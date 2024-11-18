package testsProyecto;
import org.junit.jupiter.api.Test;

import Persistencia.PersistenciaUsuarios;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import proyecto.Actividad;
import proyecto.Encuesta;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Main2;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Quiz;
import proyecto.Registro;
import proyecto.Reseña;
import proyecto.Tarea;
import proyecto.Usuario;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;

public class ActividadesTest {
	Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
	Profesor profJaime = new Profesor("Jaime", "jaime@gmail.com", "jaime123");
	Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
	LearningPath lp = new LearningPath("Pruebas Junit", "Aprende a hacer pruebas con Junit", "Pruebas integradas", "medio", profMario, 20);
	LearningPath lp2 = new LearningPath("Interfaz Grafica", "Aprende a disenar una interfaz grafica", "Crear interfaz grafica en java", "medio", profMario, 10);
	Encuesta encuesta = new Encuesta(lp, "encuesta de Junit", "conocer Junit", "medio", 10, false, profMario);
	Tarea tarea = new Tarea(lp, "diferentes tipos de asserts", "conocer los tipos de asserts", "medio", 40, true, profMario);
	Quiz quiz = new Quiz(lp2, "asserts", "escoger assert correcto", "medio", 20, true, 3.0, profMario);
	Reseña resena = new Reseña("Muy buena encuesta", 9);
	Reseña resena2 = new Reseña("No me gusto", 3);
	
	@Test
	public void testEstablecerFechaLimite() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		Date fechaAnterior = calendar.getTime();
		
		encuesta.establecerFechaLimite(fechaAnterior);
		
		calendar.add(Calendar.HOUR_OF_DAY, 3); //fecha esperada
		Date expectedFechaLimite = calendar.getTime();

		assertEquals(expectedFechaLimite, encuesta.getFechaLimite());
		
	}
	
	@Test
	public void testEstablecerFechaLimiteNullFechaAnterior() {
		Date prev = new Date();
		encuesta.establecerFechaLimite(null);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(prev);
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		Date expectedFechaLimite = calendar.getTime();
		
		assertEquals(expectedFechaLimite, encuesta.getFechaLimite());
	}
	
	@Test
	public void testRecordatorioActividadDentroDeHora() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		calendar.add(Calendar.MINUTE, -150);
		
		Date fechaAnterior = calendar.getTime();
		encuesta.establecerFechaLimite(fechaAnterior);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
		
        encuesta.RecordatorioActividad(progreso);
        
		String expectedMessage = "Recordatorio: La actividad 'encuesta de Junit' tiene una hora para su fecha límite recomendada.";
		String output = outputStream.toString().trim();
		assertDoesNotThrow(() -> { encuesta.RecordatorioActividad(progreso);});
		assertTrue(output.contains(expectedMessage));
		
	}
	
	@Test
	public void testRecordatorioActividadFueraDeTiempo() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		calendar.add(Calendar.MINUTE, -60);
		
		Date fechaAnterior = calendar.getTime();
		encuesta.establecerFechaLimite(fechaAnterior);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        encuesta.RecordatorioActividad(progreso);
        
        String output = outputStream.toString().trim();

        assertTrue(output.isEmpty());
	}
	
	@Test
	public void testRecordatorioActividadFechaLimiteNull() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        encuesta.RecordatorioActividad(progreso);
        
        String actualOutput = outputStream.toString().trim();
        assertTrue(actualOutput.isEmpty());

	}
	
	@Test
	public void testRecordatorioActividadCompletada() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		calendar.add(Calendar.HOUR, 2);
		Date fechaFin = calendar.getTime();
		
		progreso.setFechaInicio(fechaInicio);
		
		progreso.marcarRealizada("Completada", fechaFin);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
		
		encuesta.RecordatorioActividad(progreso);
		
		String output = outputStream.toString().trim();
        assertTrue(output.isEmpty());
	}
	
	@Test
	public void testAgregarActividadSeguimiento() {
		encuesta.agregarActividadSeguimiento(tarea);
		
		assertTrue(encuesta.getActividadSeguimiento().contains(tarea));
	}
	
	@Test
	public void testAgregarActividadSeguimientoLPDif() {
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        encuesta.agregarActividadSeguimiento(quiz);
        assertFalse(encuesta.getActividadSeguimiento().contains(quiz));
        
        String output = outputStream.toString().trim();
        assertEquals("La actividad de seguimiento no pertenece al mismo learning path", output);
	}
	
	@Test
	public void testAgregarPrerrequisito() {
		encuesta.agregarPrerrequisito(tarea);
		
		assertTrue(encuesta.getPrerrequisitos().contains(tarea));
	}
	
	@Test
	public void testAgregarPrerrequisitoLPDif() {
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        encuesta.agregarPrerrequisito(quiz);
        assertFalse(encuesta.getPrerrequisitos().contains(quiz));
        
        String output = outputStream.toString().trim();
        assertEquals("La actividad prerrequisito no pertenece al mismo learning path", output);
	}
	
	@Test
	public void testRecomendarActividadNoHayProxima() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		calendar.add(Calendar.HOUR, 2);
		Date fechaFin = calendar.getTime();
		
		progreso.setFechaInicio(fechaInicio);
		
		progreso.marcarRealizada("Aprobada", fechaFin);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
		encuesta.recomendarActividad(progreso, encuesta);
		
		String output = outputStream.toString().trim();
		String expectedMessage = "No hay actvidades de seguimiento para recomendar";
		assertEquals(expectedMessage, output);
	}
	
	@Test
	public void testRecomendarActividadSinCompletarUltima() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		encuesta.agregarActividadSeguimiento(tarea);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
		encuesta.recomendarActividad(progreso, encuesta);
		
		String output = outputStream.toString().trim();
		String expectedMessage = "Aún no has completado la última actividad.";
		assertEquals(expectedMessage, output);
	}
	
	@Test 
	public void testRecomendarActividad() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		encuesta.agregarActividadSeguimiento(tarea);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		calendar.add(Calendar.HOUR, 2);
		Date fechaFin = calendar.getTime();
		
		progreso.setFechaInicio(fechaInicio);
		
		progreso.marcarRealizada("Aprobada", fechaFin);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        encuesta.recomendarActividad(progreso, encuesta);
		
		String output = outputStream.toString().trim();
		String expectedMessage = "Recomendación: Realiza la siguiente actividad -> diferentes tipos de asserts";
		assertEquals(expectedMessage, output);
	}
	
	@Test
	public void testRecomendarActividadSinNotaUltima() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		encuesta.agregarActividadSeguimiento(tarea);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		calendar.add(Calendar.HOUR, 2);
		Date fechaFin = calendar.getTime();
		
		progreso.setFechaInicio(fechaInicio);
		
		progreso.marcarRealizada("Completada", fechaFin);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
		
        encuesta.recomendarActividad(progreso, encuesta);
        
		String output = outputStream.toString().trim();
		String expectedMessage = "Aún no te podemos recomendar una actividad porque estás a la espera de la calificación de encuesta de Junit.\nInténtalo de nuevo cuando te den el resultado.";
		assertEquals(expectedMessage, output);
	}

	@Test
	public void testRecomendarActividadReprobada() {
		ProgresoActividad progreso = new ProgresoActividad(encuesta, estudianteJose);
		encuesta.agregarActividadSeguimiento(tarea);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		calendar.add(Calendar.HOUR, 2);
		Date fechaFin = calendar.getTime();
		
		progreso.setFechaInicio(fechaInicio);
		
		progreso.marcarRealizada("Reprobada", fechaFin);
		
		var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        encuesta.recomendarActividad(progreso, encuesta);
		
		String output = outputStream.toString().trim();
		String expectedMessage = "Recomendación: Vuelve a realizar la actividad: encuesta de Junit";
		assertEquals(expectedMessage, output);
	}
	
	@Test
	public void testClonarActividad() {
		Actividad clon = encuesta.clonarActividad(profJaime);
		
		assertEquals(encuesta.getDescripcion(), clon.getDescripcion());
		assertEquals(encuesta.getObjetivo(), clon.getObjetivo());
		assertEquals(encuesta.getNivelDificultad(), clon.getNivelDificultad());
		assertEquals(encuesta.getDuracionEsperada(), clon.getDuracionEsperada());
		assertEquals(encuesta.isObligatorio(), clon.isObligatorio());
		assertTrue(clon.getCreador().equals(profJaime));
	}
	
	@Test
	public void testAgregarResenaNoHay() {
		
		assertTrue(encuesta.getReseñas() == null);
		
		encuesta.agregarReseña(resena);
		assertTrue(encuesta.getReseñas().contains(resena));
		assertTrue(encuesta.getReseñas().size() == 1);
		
		encuesta.agregarReseña(resena2);
		assertTrue(encuesta.getReseñas().contains(resena2));
	}
	
	@Test
	public void testCalcularPromedioRating() {
		
		assertEquals(0.0, encuesta.calcularPromedioRating());
		
		encuesta.agregarReseña(resena);
		encuesta.agregarReseña(resena2);
		
		assertEquals(6.0, encuesta.calcularPromedioRating());
	}

	
}


