package Persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.Actividad;
import proyecto.Tarea;
import proyecto.Quiz;
import proyecto.RecursoEducativo;
import proyecto.Examen;
import proyecto.Encuesta;
import proyecto.Profesor;

public class PersistenciaActividades {

    private static final String TIPO_ACTIVIDAD = "tipoActividad";
    private static final String DESCRIPCION = "descripcion";
    private static final String OBJETIVO = "objetivo";
    private static final String NIVEL_DIFICULTAD = "nivelDificultad";
    private static final String DURACION = "duracionEsperada";
    private static final String FECHA_LIMITE = "fechaLimite";
    private static final String OBLIGATORIO = "obligatorio";
    private static final String CREADOR = "creador";
    private static final String PREGUNTAS = "preguntas";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Actividad> cargarActividades(String archivo) throws IOException {
        List<Actividad> actividades = new ArrayList<>();
        File file = new File(archivo);
        
        // Verifica si el archivo existe, si no, lo crea con una estructura JSON básica
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(archivo)) {
                pw.write("{\"actividades\": []}");
            }
            System.out.println("Archivo de actividades no encontrado. Se ha creado un archivo vacío.");
        }

        String jsonCompleto = new String(Files.readAllBytes(file.toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);

        cargarActividades(actividades, raiz.getJSONArray("actividades"));
        return actividades;
    }

    private void cargarActividades(List<Actividad> actividades, JSONArray jActividades) {
        for (int i = 0; i < jActividades.length(); i++) {
            JSONObject jActividad = jActividades.getJSONObject(i);
            String tipoActividad = jActividad.getString(TIPO_ACTIVIDAD);
            String descripcion = jActividad.getString(DESCRIPCION);
            String objetivo = jActividad.getString(OBJETIVO);
            String nivelDificultad = jActividad.getString(NIVEL_DIFICULTAD);
            int duracion = jActividad.getInt(DURACION);
            boolean obligatorio = jActividad.getBoolean(OBLIGATORIO);

            // Cargar el creador (Profesor)
            JSONObject jCreador = jActividad.getJSONObject(CREADOR);
            String nombreCreador = jCreador.getString("nombre");
            String correoCreador = jCreador.getString("correo");
            Profesor creador = new Profesor(nombreCreador, correoCreador, "");

            // Crear una instancia de la actividad correspondiente
            Actividad nuevaActividad = null;
            switch (tipoActividad) {
                case "Tarea":
                    nuevaActividad = new Tarea(null, descripcion, objetivo, nivelDificultad, duracion, obligatorio, creador);
                    break;
                case "Quiz":
                    nuevaActividad = new Quiz(null, descripcion, objetivo, nivelDificultad, duracion, obligatorio, 60.0, creador);
                    break;
                case "Examen":
                    nuevaActividad = new Examen(null, descripcion, objetivo, nivelDificultad, duracion, obligatorio, creador);
                    JSONArray jPreguntasAbiertas = jActividad.getJSONArray(PREGUNTAS);
                    for (int j = 0; j < jPreguntasAbiertas.length(); j++) {
                        ((Examen) nuevaActividad).getPreguntasAbiertas().add(jPreguntasAbiertas.getString(j));
                    }
                    break;
                case "Encuesta":
                    nuevaActividad = new Encuesta(null, descripcion, objetivo, nivelDificultad, duracion, obligatorio, creador);
                    JSONArray jPreguntasEncuesta = jActividad.getJSONArray(PREGUNTAS);
                    for (int j = 0; j < jPreguntasEncuesta.length(); j++) {
                        ((Encuesta) nuevaActividad).getPreguntasAbiertas().add(jPreguntasEncuesta.getString(j));
                    }
                    break;
                case "RecursoEducativo":
                    nuevaActividad = new RecursoEducativo(null, descripcion, objetivo, nivelDificultad, duracion, obligatorio, "Tipo de recurso", "Enlace", creador);
                    break;
            }

            if (jActividad.has(FECHA_LIMITE)) {
                try {
                    nuevaActividad.establecerFechaLimite(dateFormat.parse(jActividad.getString(FECHA_LIMITE)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Validar duplicados antes de agregar
            if (nuevaActividad != null) {
                // Crear una variable auxiliar final
                final Actividad actividadAux = nuevaActividad;

                // Validar duplicados
                if (actividades.stream().noneMatch(a -> a.getDescripcion().equals(actividadAux.getDescripcion()))) {
                    actividades.add(actividadAux);
                } else {
                    System.out.println("Advertencia: Actividad duplicada o inválida: " + actividadAux.getDescripcion());
                }
            }

        }
    }

    public void salvarActividades(String archivo, List<Actividad> actividades) throws IOException {
        JSONObject jobject = new JSONObject();
        JSONArray jActividades = new JSONArray();

        for (Actividad actividad : actividades) {
            JSONObject jActividad = new JSONObject();
            jActividad.put(TIPO_ACTIVIDAD, actividad.getClass().getSimpleName());
            jActividad.put(DESCRIPCION, actividad.getDescripcion());
            jActividad.put(OBJETIVO, actividad.getObjetivo());
            jActividad.put(NIVEL_DIFICULTAD, actividad.getNivelDificultad());
            jActividad.put(DURACION, actividad.getDuracionEsperada());
            jActividad.put(OBLIGATORIO, actividad.isObligatorio());

            if (actividad.getFechaLimite() != null) {
                jActividad.put(FECHA_LIMITE, dateFormat.format(actividad.getFechaLimite()));
            }

            JSONObject jCreador = new JSONObject();
            jCreador.put("nombre", actividad.getCreador().getNombre());
            jCreador.put("correo", actividad.getCreador().getCorreo());
            jActividad.put(CREADOR, jCreador);

            if (actividad instanceof Encuesta) {
                JSONArray jPreguntas = new JSONArray();
                for (String pregunta : ((Encuesta) actividad).getPreguntasAbiertas()) {
                    jPreguntas.put(pregunta);
                }
                jActividad.put(PREGUNTAS, jPreguntas);
            } else if (actividad instanceof Examen) {
                JSONArray jPreguntasAbiertas = new JSONArray();
                for (String pregunta : ((Examen) actividad).getPreguntasAbiertas()) {
                    jPreguntasAbiertas.put(pregunta);
                }
                jActividad.put(PREGUNTAS, jPreguntasAbiertas);
            }

            jActividades.put(jActividad);
        }

        jobject.put("actividades", jActividades);
        try (PrintWriter pw = new PrintWriter(archivo)) {
            jobject.write(pw, 2, 0);
        }
    }
}



