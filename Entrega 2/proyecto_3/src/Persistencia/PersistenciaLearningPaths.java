package Persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.LearningPath;
import proyecto.Actividad;
import proyecto.Profesor;
import proyecto.Usuario;

public class PersistenciaLearningPaths {

    private static final String TITULO = "titulo";
    private static final String DESCRIPCION = "descripcion";
    private static final String OBJETIVOS = "objetivos";
    private static final String NIVEL_DIFICULTAD = "nivelDificultad";
    private static final String DURACION_ESTIMADA = "duracionEstimada";
    private static final String ACTIVIDADES = "actividades";
    private static final String CREADOR = "creador";

    public List<LearningPath> cargarLearningPaths(String archivo, List<Actividad> actividades, List<Usuario> usuarios) throws IOException {
        List<LearningPath> learningPaths = new ArrayList<>();
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);

        JSONArray jPaths = raiz.getJSONArray("learningPaths");
        for (int i = 0; i < jPaths.length(); i++) {
            JSONObject jPath = jPaths.getJSONObject(i);
            String titulo = jPath.getString("titulo");
            String descripcion = jPath.getString("descripcion");
            String objetivos = jPath.getString("objetivos");
            String nivelDificultad = jPath.getString("nivelDificultad");
            int duracionEstimada = jPath.getInt("duracionEstimada");

            // Identificar al creador (Profesor) usando la lista de usuarios
            JSONObject jCreador = jPath.getJSONObject("creador");
            String correoCreador = jCreador.getString("correo");
            Profesor creador = null;

            for (Usuario usuario : usuarios) {
                if (usuario instanceof Profesor && usuario.getCorreo().equals(correoCreador)) {
                    creador = (Profesor) usuario;
                    break;
                }
            }

            if (creador == null) {
                System.out.println("Profesor con correo " + correoCreador + " no encontrado. Saltando este Learning Path.");
                continue;
            }

            // Crear el Learning Path
            LearningPath nuevoPath = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, creador, duracionEstimada);

            // Asociar actividades al Learning Path
            JSONArray jActividades = jPath.getJSONArray("actividades");
            for (int j = 0; j < jActividades.length(); j++) {
                int idActividad = jActividades.getInt(j);
                if (idActividad >= 0 && idActividad < actividades.size()) {
                    nuevoPath.getActividades().add(actividades.get(idActividad));
                }
            }

            learningPaths.add(nuevoPath);
            creador.getLearningPathsCreados().add(nuevoPath);
        }
        return learningPaths;
    }

    public void salvarLearningPaths(String archivo, List<LearningPath> learningPaths) throws IOException {
        JSONObject jobject = new JSONObject();
        JSONArray jPaths = new JSONArray();

        for (LearningPath path : learningPaths) {
            JSONObject jPath = new JSONObject();
            jPath.put(TITULO, path.getTitulo());
            jPath.put(DESCRIPCION, path.getDescripcion());
            jPath.put(OBJETIVOS, path.getObjetivos());
            jPath.put(NIVEL_DIFICULTAD, path.getNivelDificultad());
            jPath.put(DURACION_ESTIMADA, path.getDuracionEstimada());

            // Guardar el creador
            JSONObject jCreador = new JSONObject();
            jCreador.put("nombre", path.getCreador().getNombre());
            jCreador.put("correo", path.getCreador().getCorreo());
            jPath.put(CREADOR, jCreador);

            // Guardar las actividades del Learning Path como índices o IDs
            JSONArray jActividades = new JSONArray();
            for (Actividad actividad : path.getActividades()) {
                int index = path.getActividades().indexOf(actividad);
                jActividades.put(index); // Guardamos el índice de la actividad en la lista
            }
            jPath.put(ACTIVIDADES, jActividades);

            jPaths.put(jPath);
        }

        jobject.put("learningPaths", jPaths);
        try (PrintWriter pw = new PrintWriter(archivo)) {
            jobject.write(pw, 2, 0);
        }
    }
}


