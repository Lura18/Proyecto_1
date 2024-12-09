package Persistencia;

import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.ProgresoPath;
import proyecto.ProgresoActividad;
import proyecto.Actividad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaProgreso {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Estudiante> cargarProgresos(String archivo, List<LearningPath> learningPaths) throws IOException {
        List<Estudiante> estudiantes = new ArrayList<>();
        File file = new File(archivo);

        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(archivo)) {
                pw.write("{\"estudiantes\": []}");
            }
            return estudiantes;
        }

        String contenido = new String(Files.readAllBytes(file.toPath()));
        JSONObject jsonRaiz = new JSONObject(contenido);
        JSONArray jsonEstudiantes = jsonRaiz.getJSONArray("estudiantes");

        for (int i = 0; i < jsonEstudiantes.length(); i++) {
            JSONObject jsonEstudiante = jsonEstudiantes.getJSONObject(i);
            String correo = jsonEstudiante.getString("correo");
            String nombre = jsonEstudiante.getString("nombre");

            Estudiante estudiante = new Estudiante(nombre, correo, "");
            JSONArray jsonLearningPaths = jsonEstudiante.getJSONArray("learningPaths");

            for (int j = 0; j < jsonLearningPaths.length(); j++) {
                JSONObject jsonLP = jsonLearningPaths.getJSONObject(j);
                String tituloLP = jsonLP.getString("titulo");

                LearningPath lp = learningPaths.stream()
                        .filter(path -> path.getTitulo().equals(tituloLP))
                        .findFirst()
                        .orElse(null);

                if (lp != null) {
                    JSONObject jsonProgreso = jsonLP.getJSONObject("progresoPath");
                    ProgresoPath progresoPath;

                    try {
                        Date fechaInicio = jsonProgreso.has("fechaInicio") && !jsonProgreso.isNull("fechaInicio")
                                ? dateFormat.parse(jsonProgreso.getString("fechaInicio"))
                                : new Date();
                        Date fechaFin = jsonProgreso.has("fechaFin") && !jsonProgreso.isNull("fechaFin")
                                ? dateFormat.parse(jsonProgreso.getString("fechaFin"))
                                : null;
                        progresoPath = new ProgresoPath(lp, fechaInicio, estudiante);
                        progresoPath.setFechaFinPath(fechaFin);
                    } catch (ParseException e) {
                        progresoPath = new ProgresoPath(lp, new Date(), estudiante);
                    }

                    JSONArray jsonActividades = jsonProgreso.getJSONArray("actividadesRealizadas");
                    for (int k = 0; k < jsonActividades.length(); k++) {
                        JSONObject jsonActividad = jsonActividades.getJSONObject(k);
                        String descripcion = jsonActividad.getString("descripcion");

                        Actividad actividad = lp.getActividades().stream()
                                .filter(a -> a.getDescripcion().equals(descripcion))
                                .findFirst()
                                .orElse(null);

                        if (actividad != null) {
                            ProgresoActividad progresoActividad = estudiante.getProgresosAct().get(actividad);
							if (progresoActividad == null) {
							    // Crear un nuevo ProgresoActividad si no existe
							    progresoActividad = new ProgresoActividad(actividad, estudiante);
							    estudiante.getProgresosAct().put(actividad, progresoActividad);
							}

							try {
							    progresoActividad.setResultado(jsonActividad.getString("resultado"));
							    progresoActividad.setFechaInicio(dateFormat.parse(jsonActividad.getString("fechaInicio")));
							    progresoActividad.setFechaFin(dateFormat.parse(jsonActividad.getString("fechaFin")));
							    progresoActividad.marcarRealizada(jsonActividad.getString("resultado"), progresoActividad.getFechaFin());
							    progresoPath.agregarActividadRealizada(actividad);
							} catch (ParseException e) {
							    System.out.println("Error al parsear las fechas de la actividad: " + e.getMessage());
							    e.printStackTrace();
							}
                        }
                    }

                    progresoPath.calcularProgreso();
                    progresoPath.actualizarTasas();
                    estudiante.getProgresoPaths().put(lp, progresoPath);
                }
            }
            estudiantes.add(estudiante);
        }

        return estudiantes;
    }

    public void guardarProgresos(String archivo, List<Estudiante> estudiantes) throws IOException {
        JSONObject jsonRaiz = new JSONObject();
        JSONArray jsonEstudiantes = new JSONArray();

        for (Estudiante estudiante : estudiantes) {
            JSONObject jsonEstudiante = new JSONObject();
            jsonEstudiante.put("correo", estudiante.getCorreo());
            jsonEstudiante.put("nombre", estudiante.getNombre());

            JSONArray jsonLearningPaths = new JSONArray();
            for (LearningPath lp : estudiante.getLearningPathsInscritos()) {
                JSONObject jsonLP = new JSONObject();
                jsonLP.put("titulo", lp.getTitulo());

                ProgresoPath progresoPath = estudiante.getProgresoPaths().get(lp);
                if (progresoPath != null) {
                    JSONObject jsonProgreso = new JSONObject();
                    jsonProgreso.put("fechaInicio", dateFormat.format(progresoPath.getFechaInicioPath()));
                    jsonProgreso.put("fechaFin", progresoPath.getFechaFinPath() != null
                            ? dateFormat.format(progresoPath.getFechaFinPath())
                            : JSONObject.NULL);
                    jsonProgreso.put("porcentaje", progresoPath.getPorcentajePath());

                    JSONArray jsonActividades = new JSONArray();
                    for (Actividad actividad : progresoPath.getActividadesRealizadas()) {
                        ProgresoActividad progresoActividad = estudiante.getProgresosAct().get(actividad);
                        if (progresoActividad != null) {
                            JSONObject jsonActividad = new JSONObject();
                            jsonActividad.put("descripcion", actividad.getDescripcion());
                            jsonActividad.put("resultado", progresoActividad.getResultado());
                            jsonActividad.put("fechaInicio", progresoActividad.getFechaInicio() != null
                                    ? dateFormat.format(progresoActividad.getFechaInicio())
                                    : JSONObject.NULL);
                            jsonActividad.put("fechaFin", progresoActividad.getFechaFin() != null
                                    ? dateFormat.format(progresoActividad.getFechaFin())
                                    : JSONObject.NULL);
                            jsonActividad.put("tiempoDedicado", progresoActividad.getTiempoDedicado());
                            jsonActividades.put(jsonActividad);
                        }
                    }

                    jsonProgreso.put("actividadesRealizadas", jsonActividades);
                    jsonLP.put("progresoPath", jsonProgreso);
                }
                jsonLearningPaths.put(jsonLP);
            }
            jsonEstudiante.put("learningPaths", jsonLearningPaths);
            jsonEstudiantes.put(jsonEstudiante);
        }

        jsonRaiz.put("estudiantes", jsonEstudiantes);

        try (PrintWriter pw = new PrintWriter(archivo)) {
            pw.write(jsonRaiz.toString(2));
        }
    }
}


