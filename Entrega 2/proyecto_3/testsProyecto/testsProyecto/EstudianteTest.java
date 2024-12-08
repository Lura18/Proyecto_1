package testsProyecto;

import org.junit.jupiter.api.Test;
import proyecto.*;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EstudianteTest {

    Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
    Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
    LearningPath lp = new LearningPath("Aprender Java", "Curso completo de Java", "Dominio del lenguaje", "medio", profMario, 30);
    Actividad actividad1 = new Encuesta(lp, "Encuesta inicial", "Conocer experiencia previa", "bajo", 15, false, profMario);
    Actividad actividad2 = new Tarea(lp, "Tarea sobre bucles", "Dominar bucles for y while", "medio", 60, true, profMario);
    ProgresoPath progresoPath;

    @Test
    public void testInscribirLearningPath() {
        estudianteJose.inscribirLearningPath(lp);

        assertTrue(estudianteJose.getLearningPathsInscritos().contains(lp), "El Learning Path debería estar inscrito.");
        assertNotNull(estudianteJose.getProgresoPaths().get(lp), "El progreso del Learning Path debería estar creado.");
    }

    @Test
    public void testInscribirLearningPathDuplicado() {
        estudianteJose.inscribirLearningPath(lp);
        estudianteJose.inscribirLearningPath(lp);

        assertEquals(1, estudianteJose.getLearningPathsInscritos().size(), "No se debe permitir inscribir el mismo Learning Path más de una vez.");
    }

    @Test
    public void testSeleccionarActividad() {
        estudianteJose.inscribirLearningPath(lp);

        // Iniciar una actividad y verificar que se selecciona correctamente
        Actividad seleccionada = estudianteJose.seleccionarActividad(new java.util.Scanner("1\n"), lp);
        assertEquals(actividad1, seleccionada, "La actividad seleccionada debería ser la primera en el Learning Path.");
    }

    @Test
    public void testSeleccionarActividadNoInscrito() {
        Actividad seleccionada = estudianteJose.seleccionarActividad(new java.util.Scanner("1\n"), lp);
        assertNull(seleccionada, "No se debería permitir seleccionar actividades de un Learning Path no inscrito.");
    }

    @Test
    public void testMostrarProgreso() {
        estudianteJose.inscribirLearningPath(lp);
        progresoPath = estudianteJose.getProgresoPaths().get(lp);
        progresoPath.agregarActividadRealizada(actividad1);

        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        estudianteJose.mostrarProgreso();

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Porcentaje completado"), "El progreso debería incluir información de porcentaje completado.");
        assertTrue(output.contains("Encuesta inicial"), "Debería mostrar la actividad completada.");
    }

    @Test
    public void testIniciarActividadSinPrerrequisitos() {
        estudianteJose.inscribirLearningPath(lp);

        // Verificar que la actividad se puede iniciar
        estudianteJose.iniciarActividad(actividad1);

        ProgresoActividad progreso = estudianteJose.getProgresosAct().get(actividad1);
        assertNotNull(progreso.getFechaInicio(), "La actividad debería estar iniciada.");
    }

    @Test
    public void testFaltanPrerrequisitos() {
        actividad2.agregarPrerrequisito(actividad1);
        estudianteJose.inscribirLearningPath(lp);

        boolean faltanPrerrequisitos = estudianteJose.faltanPrerrequisitos(actividad2);

        assertTrue(faltanPrerrequisitos, "No se debería poder iniciar la actividad sin completar sus prerrequisitos.");
    }

    @Test
    public void testPedirProgresoPath() {
        estudianteJose.inscribirLearningPath(lp);

        progresoPath = estudianteJose.getProgresoPaths().get(lp);
        progresoPath.agregarActividadRealizada(actividad1);
        progresoPath.calcularProgreso();
        progresoPath.actualizarTasas();

        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        estudianteJose.pedirProgresoPath(lp);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Porcentaje completado"), "Debería mostrar el porcentaje completado.");
        assertTrue(output.contains("Encuesta inicial"), "Debería mostrar las actividades realizadas.");
    }
}


