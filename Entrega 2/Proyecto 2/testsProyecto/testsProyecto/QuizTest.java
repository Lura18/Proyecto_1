package testsProyecto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import proyecto.Encuesta;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.PreguntaMultiple;
import proyecto.PreguntaQuiz;
import proyecto.PreguntaTrueFalse;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Quiz;

public class QuizTest {

	Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
	Profesor profJaime = new Profesor("Jaime", "jaime@gmail.com", "jaime123");
	LearningPath lp = new LearningPath("Pruebas Junit", "Aprende a hacer pruebas con Junit", "Pruebas integradas", "medio", profMario, 20);
	Quiz quiz = new Quiz(lp, "encuesta de Junit", "conocer Junit", "medio", 10, false, 3, profMario, "Texto");
	Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
	List<String> explicaciones;
	PreguntaTrueFalse pregTF1 = new PreguntaTrueFalse("Las pruebas se hacen sin junit", 0, explicaciones);
	

	
	@Test
	public void testRealizar() {
		ProgresoActividad progreso = new ProgresoActividad(quiz, estudianteJose);
		List<PreguntaQuiz> preguntas = quiz.getPreguntas();

		
		preguntas.add(pregTF1);

		
		String simulatedInput = "0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        quiz.realizar(progreso);
        
        assertTrue(progreso.isCompletada());

	}
	
	@Test
	public void testRealizarSinPreguntas() {
		ProgresoActividad progreso = new ProgresoActividad(quiz, estudianteJose);

		String simulatedInput = "";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        quiz.realizar(progreso);
        
        assertFalse(quiz.getRespuesta().containsKey(estudianteJose));
	}
	
	@Test
	public void testEditarCreador() {
		List<PreguntaQuiz> preguntas = quiz.getPreguntas();

		
		String simulatedInput = "Otra pregunta sobre pruebas integradas";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
        
        quiz.editar(profMario);
        
        assertEquals(1, preguntas.size());
        assertEquals("Otra pregunta sobre pruebas integradas", preguntas.get(0));
	}
	
	@Test
	public void testEditarNoCreador() {
		List<PreguntaQuiz> preguntas = quiz.getPreguntas();
		
		String simulatedInput = "Otra pregunta sobre pruebas integradas";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        System.setIn(inputStream);
                
        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        quiz.editar(profJaime);
        
		String output = outputStream.toString().trim();
		String expectedMessage = "No tiene los permisos para editar la actividad";
		assertEquals(expectedMessage, output);
		assertEquals(0, preguntas.size());

	}
}
