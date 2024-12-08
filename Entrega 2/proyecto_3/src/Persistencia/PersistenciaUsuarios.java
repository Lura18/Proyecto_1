package Persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import proyecto.Usuario;
import proyecto.Estudiante;
import proyecto.Profesor;

public class PersistenciaUsuarios {

    private static final String NOMBRE = "nombre";
    private static final String CORREO = "correo";
    private static final String CONTRASENA = "password";
    private static final String TIPO_USUARIO = "tipoUsuario";

    /**
     * Carga usuarios desde un archivo JSON. El método soporta dos formatos:
     * - Un objeto con una clave raíz `"usuarios"` que contiene un array de usuarios.
     * - Un array JSON directo con los usuarios.
     *
     * @param archivo Ruta del archivo JSON.
     * @return Lista de usuarios cargados.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public List<Usuario> cargarUsuarios(String archivo) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));

        // Verificar si el JSON es un array o un objeto con clave "usuarios"
        if (jsonCompleto.trim().startsWith("[")) {
            // El JSON es un array directo
            JSONArray jUsuarios = new JSONArray(jsonCompleto);
            cargarUsuariosDesdeJSONArray(usuarios, jUsuarios);
        } else {
            // El JSON es un objeto con la clave "usuarios"
            JSONObject raiz = new JSONObject(jsonCompleto);
            JSONArray jUsuarios = raiz.getJSONArray("usuarios");
            cargarUsuariosDesdeJSONArray(usuarios, jUsuarios);
        }
        return usuarios;
    }

    /**
     * Carga usuarios desde un JSONArray y los agrega a la lista de usuarios.
     *
     * @param usuarios  Lista de usuarios a la que se agregarán los usuarios cargados.
     * @param jUsuarios JSONArray con los datos de los usuarios.
     */
    private void cargarUsuariosDesdeJSONArray(List<Usuario> usuarios, JSONArray jUsuarios) {
        for (int i = 0; i < jUsuarios.length(); i++) {
            JSONObject usuario = jUsuarios.getJSONObject(i);
            cargarUsuarioDesdeJSON(usuarios, usuario);
        }
    }

    /**
     * Crea un usuario desde un JSONObject y lo agrega a la lista de usuarios.
     *
     * @param usuarios Lista de usuarios.
     * @param usuario  JSONObject con los datos del usuario.
     */
    private void cargarUsuarioDesdeJSON(List<Usuario> usuarios, JSONObject usuario) {
        String tipoUsuario = usuario.getString(TIPO_USUARIO);
        String nombre = usuario.getString(NOMBRE);
        String correo = usuario.getString(CORREO);
        String password = usuario.getString(CONTRASENA);

        if ("Profesor".equalsIgnoreCase(tipoUsuario)) {
            usuarios.add(new Profesor(nombre, correo, password));
        } else if ("Estudiante".equalsIgnoreCase(tipoUsuario)) {
            usuarios.add(new Estudiante(nombre, correo, password));
        }
    }

    /**
     * Guarda una lista de usuarios en un archivo JSON.
     * El archivo generado será un array JSON directo con los usuarios.
     *
     * @param archivo  Ruta del archivo donde se guardarán los usuarios.
     * @param usuarios Lista de usuarios a guardar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void salvarUsuarios(String archivo, List<Usuario> usuarios) throws IOException {
        JSONArray jUsuarios = new JSONArray();

        for (Usuario usuario : usuarios) {
            JSONObject jUsuario = new JSONObject();
            jUsuario.put(NOMBRE, usuario.getNombre());
            jUsuario.put(CORREO, usuario.getCorreo());
            jUsuario.put(CONTRASENA, usuario.getContrasena());
            jUsuario.put(TIPO_USUARIO, usuario.getTipoUsuario());
            jUsuarios.put(jUsuario);
        }

        try (PrintWriter pw = new PrintWriter(archivo)) {
            pw.write(jUsuarios.toString(2)); // Formato con 2 espacios para legibilidad
        }
    }
}

