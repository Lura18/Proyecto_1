package testsProyecto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.junit.Test;

import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Tarea;

public class TareaTest {
	
	Profesor profMario = new Profesor("Mario", "mario@gmail.com", "mario123");
	Profesor profJaime = new Profesor("Jaime", "jaime@gmail.com", "jaime123");
	LearningPath lp = new LearningPath("Pruebas Junit", "Aprende a hacer pruebas con Junit", "Pruebas integradas", "medio", profMario, 20);
	Tarea tarea = new Tarea(lp, "tarea de Junit", "conocer Junit", "medio", 10, false, profMario);
	Estudiante estudianteJose = new Estudiante("jose", "jose@gmail.com", "jose123");
	
	@Test
	public void testEnviarTarea() {
		ProgresoActividad progreso = new ProgresoActividad(tarea, estudianteJose);
		
		String simulatedInput = "pdf";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        
        tarea.enviarTarea(scanner, progreso);
        
        assertEquals(1, tarea.getRespuesta().size());
        assertEquals(true, tarea.getRespuesta().containsKey(estudianteJose));
        assertEquals("pdf", tarea.getRespuesta().get(estudianteJose));
        
        String output = outputStream.toString().trim();
		String expectedMessage = "Has enviado la tarea: tarea de Junit";
		assertEquals(true, output.contains(expectedMessage));
		  
	}
	
	@Test
	public void testCambiosDescripcion() {
		String simulatedInput = "descripcion\ntarea de pruebas junit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        tarea.cambios(scanner);
        
        assertEquals("tarea de pruebas junit", tarea.getDescripcion());
	}
	
	@Test
	public void testCambiosObjetivos() {
		String simulatedInput = "objetivo\naprender a usar junit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        tarea.cambios(scanner);
        
        assertEquals("aprender a usar junit", tarea.getObjetivo());
	}
	
	@Test
	public void testCambioInvalido() {
		String simulatedInput = "otro\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        tarea.cambios(scanner);
        assertEquals(true, outputStream.toString().contains("Opción no válida"));

	}
	
	@Test
	public void testRealizar() {
		ProgresoActividad progreso = new ProgresoActividad(tarea, estudianteJose);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
		
		Date fechaInicio = calendar.getTime();
		progreso.setFechaInicio(fechaInicio);
        
        tarea.realizar(progreso);
        
        assertEquals(true, progreso.isCompletada());
        assertEquals(true, outputStream.toString().contains("Has completado el recurso educativo: recurso de Junit"));
	}
	
	
	@Test
	public void testEditarCreador() {
		String simulatedInput = "descripcion\ncambio descripcion profMario\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        tarea.editar(profMario);
        
        assertEquals("cambio descripcion profMario", tarea.getDescripcion());
        assertEquals(true, outputStream.toString().contains("Que desea editar"));
	}
	
	@Test
	public void testEditarNoCreador() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        tarea.editar(profJaime);
        
        assertEquals(true, outputStream.toString().contains("No tiene los permisos para editar la actividad"));
	}
	
}
	

