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

public class PersistenciaLearningPaths {

    private static final String TITULO = "titulo";
    private static final String DESCRIPCION = "descripcion";
    private static final String OBJETIVOS = "objetivos";
    private static final String NIVEL_DIFICULTAD = "nivelDificultad";
    private static final String DURACION_ESTIMADA = "duracionEstimada";
    private static final String ACTIVIDADES = "actividades";
    private static final String CREADOR = "creador";

    public List<LearningPath> cargarLearningPaths(String archivo, List<Actividad> actividades) throws IOException {
        List<LearningPath> learningPaths = new ArrayList<>();
        File file = new File(archivo);
        
        // Verifica si el archivo existe, si no, lo crea con una estructura JSON básica
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(archivo)) {
                pw.write("{\"learningPaths\": []}");
            }
            System.out.println("Archivo de learning paths no encontrado. Se ha creado un archivo vacío.");
        }

        String jsonCompleto = new String(Files.readAllBytes(file.toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);

        cargarLearningPaths(learningPaths, raiz.getJSONArray("learningPaths"), actividades);
        return learningPaths;
    }

    private void cargarLearningPaths(List<LearningPath> learningPaths, JSONArray jPaths, List<Actividad> actividades) {
        for (int i = 0; i < jPaths.length(); i++) {
            JSONObject jPath = jPaths.getJSONObject(i);
            String titulo = jPath.getString(TITULO);
            String descripcion = jPath.getString(DESCRIPCION);
            String objetivos = jPath.getString(OBJETIVOS);
            String nivelDificultad = jPath.getString(NIVEL_DIFICULTAD);
            int duracionEstimada = jPath.getInt(DURACION_ESTIMADA);

            // Cargar el creador del Learning Path (Profesor)
            JSONObject jCreador = jPath.getJSONObject(CREADOR);
            Profesor creador = new Profesor(jCreador.getString("nombre"), jCreador.getString("correo"), "");

            // Crear el Learning Path
            LearningPath nuevoPath = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, creador, duracionEstimada);

            // Asociar actividades al Learning Path usando índices del JSON
            JSONArray jActividades = jPath.getJSONArray(ACTIVIDADES);
            for (int j = 0; j < jActividades.length(); j++) {
                int idActividad = jActividades.getInt(j); // Índice de la actividad
                nuevoPath.getActividades().add(actividades.get(idActividad));
            }

            learningPaths.add(nuevoPath);
        }
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
                jActividades.put(path.getActividades().indexOf(actividad)); // Usamos el índice de la actividad
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

