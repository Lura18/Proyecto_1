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
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.RecursoEducativo;

public class RecursoEducativoTest {
	Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
	Profesor profJaime = new Profesor("Jaime", "jaime@gmail.com", "jaime123");
	LearningPath lp = new LearningPath("Pruebas Junit", "Aprende a hacer pruebas con Junit", "Pruebas integradas", "medio", profMario, 20);
	RecursoEducativo recurso = new RecursoEducativo(lp, "recurso de Junit", "conocer Junit", "medio", 10, false, "Video", "https://example.com/python", profMario);
	Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
	
	@Test
	public void testCambiosTipoRecurso() {
		String simulatedInput = "recurso\nLibro\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        recurso.cambios(scanner);
        
        assertEquals("Libro", recurso.getTipoRecurso());
        assertTrue(outputStream.toString().contains("Ingrese el nuevo tipo de recurso:"));
	}
	
	@Test
	public void testCambiosEnlaceRecurso() {
		String simulatedInput = "enlace\nhttps://example.com/new-python\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        recurso.cambios(scanner);
        
        assertEquals("https://example.com/new-python", recurso.getEnlaceRecurso());
        assertTrue(outputStream.toString().contains("Ingrese el nuevo enlace de la actividad:"));
	}
	
	@Test
	public void testCambioInvalido() {
		String simulatedInput = "otro\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        recurso.cambios(scanner);
        assertTrue(outputStream.toString().contains("Opción no válida"));
	}
	
	@Test
	public void testRealizar() {
		ProgresoActividad progreso = new ProgresoActividad(recurso, estudianteJose);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        recurso.realizar(progreso);
        
        assertTrue(progreso.isCompletada());
        assertTrue(outputStream.toString().contains("Has completado el recurso educativo: recurso de Junit"));
	}
	
	
	@Test
	public void testEditarCreador() {
		String simulatedInput = "recurso\nCurso Online\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        recurso.editar(profMario);
        
        assertEquals("Curso Online", recurso.getTipoRecurso());
        assertTrue(outputStream.toString().contains("Que desea editar"));
	}
	
	@Test
	public void testEditarNoCreador() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        recurso.editar(profJaime);
        
        assertTrue(outputStream.toString().contains("No tiene los permisos para editar la actividad"));
	}


}
