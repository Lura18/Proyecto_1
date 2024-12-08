package testsProyecto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import proyecto.Encuesta;
import proyecto.Estudiante;
import proyecto.Examen;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;

public class ExamenTest {
	Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
	Profesor profJaime = new Profesor("Jaime", "jaime@gmail.com", "jaime123");
	LearningPath lp = new LearningPath("Pruebas Junit", "Aprende a hacer pruebas con Junit", "Pruebas integradas", "medio", profMario, 20);
	Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
	Examen examen = new Examen(lp, "pruebas unitarias", "probar conocimiento de pruebas", "medio", 20, true, profMario);

	
	@Test
	public void testAgregarPregunta() {
		List<String> preguntas = examen.getPreguntasAbiertas();
		
		String simulatedInput = "Que es una prueba integrada";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        examen.agregarPregunta(scanner);
        
        assertEquals(1, preguntas.size());
        assertEquals("Que es una prueba integrada", preguntas.get(0));
        
        simulatedInput = "Para que sirven las pruebas integradas";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(inputStream);
        
        examen.agregarPregunta(scanner);
        
        assertEquals(2, preguntas.size());
        assertEquals("Para que sirven las pruebas integradas", preguntas.get(1));
	}
	
	@Test
	public void testRealizarExamen() {
		ProgresoActividad progreso = new ProgresoActividad(examen, estudianteJose);
		List<String> preguntas = examen.getPreguntasAbiertas();
		preguntas.add("Que es una prueba integrada");

		
		String simulatedInput = "Prueban mas de una parte del codigo";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        examen.realizarExamen(progreso);
        
        assertEquals(1, examen.getRespuesta().size());
        assertTrue(examen.getRespuesta().containsKey(estudianteJose));
        assertEquals("Prueban mas de una parte del codigo", examen.getRespuesta().get(estudianteJose));
        
	}
	
	@Test
	public void testRealizar() {
		ProgresoActividad progreso = new ProgresoActividad(examen, estudianteJose);
		List<String> preguntas = examen.getPreguntasAbiertas();

		preguntas.add("Que es una prueba integrada");
		preguntas.add("Para que sirven las pruebas integradas");
		
		String simulatedInput = "Prueban mas de una parte del codigo\nAseguran que el codigo funcione\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        examen.realizar(progreso);
        
        assertTrue(progreso.isCompletada());

	}
	
	@Test
	public void testRealizarSinPreguntas() {
		ProgresoActividad progreso = new ProgresoActividad(examen, estudianteJose);

		String simulatedInput = "";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        examen.realizar(progreso);
        
        assertFalse(examen.getRespuesta().containsKey(estudianteJose));
	}
	
	@Test
	public void testEditarCreador() {
		List<String> preguntas = examen.getPreguntasAbiertas();

		String simulatedInput = "Otra pregunta sobre pruebas integradas";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        examen.editar(profMario);
        
        assertEquals(1, preguntas.size());
        assertEquals("Otra pregunta sobre pruebas integradas", preguntas.get(0));
	}
	
	@Test
	public void testEditarNoCreador() {
		List<String> preguntas = examen.getPreguntasAbiertas();
		
		String simulatedInput = "Otra pregunta sobre pruebas integradas";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
                
        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        examen.editar(profJaime);
        
		String output = outputStream.toString().trim();
		String expectedMessage = "No tiene los permisos para editar la actividad";
		assertEquals(expectedMessage, output);
		assertEquals(0, preguntas.size());

	}
}
