package testsProyecto;
import org.junit.jupiter.api.*;

import proyecto.Actividad;
import proyecto.Encuesta;
import proyecto.Estudiante;
import proyecto.Examen;
import proyecto.LearningPath;
import proyecto.Main2;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.Quiz;
import proyecto.RecursoEducativo;
import proyecto.Registro;
import proyecto.Tarea;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class TestProfesor {

	//Atributos 
	private Scanner scanner;
	private Registro sistema;
    private Estudiante e1;
    private Profesor p1;
    private LearningPath lp;
    private LearningPath lp2;
    private Actividad act1;
    private Actividad act2;
	
	@BeforeEach
	void setUp() {
        sistema = new Registro();
        p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
		lp = p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
        act1 = new Tarea(lp, "a", "a", "a", 5, false, p1);
	}
	
	
	
	@Test
	public void testGetLearningPaths(){
		assertEquals(p1.getLearningPathsCreados().get(0), lp);
	
	}
	
	@Test
	public void testGetTipoUsuario(){
		
		assertEquals(p1.getTipoUsuario(), "Profesor");
	
	}
	
	@Test
	public void testDarReseña(){
		p1.darReseñaActividad(act1, "ta bien", 4);
		assertEquals(act1.getReseñas().get(0).getTexto(), "ta bien");
	
	}
	
	@Test
	public void crearLearningPathTest(){
		
		p1.crearLearningPath("b", "a", "a", "a", 5, sistema);
		int cantPaths = sistema.getPaths().size();
		p1.verLearningPaths();
		assertTrue(cantPaths == 2);
	}
	
	@Test
	public void crearActividadSinPath(){
		Profesor otroProfesor = new Profesor("Jane Doe", "a", "a");
		String simulatedInput = 
				"1\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "45\n" + // Duración esperada
	            "si\n" + // Obligatorio
	            "Video\n" + // Tipo de recurso
	            "http://example.com/recurso\n"; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	        Actividad recurso = otroProfesor.crearActividad(new Scanner(System.in));
	        assertNull(recurso);
	        }
	
	@Test
	public void crearRecurso(){
		String simulatedInput = 
				"0\n" +
				"a\n" +
	            "1\n" + 
				"0\n" +
				"a\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "a\n" +
	            "45\n" + // Duración esperada
	            "si\n" + // Obligatorio
	            "Video\n" + // Tipo de recurso
	            "http://example.com/recurso\n"; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	        RecursoEducativo recurso = (RecursoEducativo) p1.crearActividad(new Scanner(System.in));
	        assertNotNull(recurso);
	        assertEquals("Descripción del recurso", recurso.getDescripcion());
	        assertEquals("Objetivo del recurso", recurso.getObjetivo());
	        assertEquals("Medio", recurso.getNivelDificultad());
	        assertEquals(45, recurso.getDuracionEsperada());
	        assertTrue(recurso.isObligatorio());
	        assertEquals("Video", recurso.getTipoRecurso());
	        assertEquals(p1.getLearningPathsCreados().get(0), recurso.getLearningPath());
	}
	
	@Test
	public void crearEncuesta(){
		p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
		String simulatedInput = 
	            "2\n" + 
				"0\n" +
				"a\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "a\n" +
	            "45\n" + // Duración esperada
	            "si\n" +// Obligatorio
	            "Hola"
	            ; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	       Encuesta encuesta = (Encuesta) p1.crearActividad(new Scanner(System.in));
	        assertNotNull(encuesta);
	        assertEquals("Descripción del recurso", encuesta.getDescripcion());
	        assertEquals("Objetivo del recurso", encuesta.getObjetivo());
	        assertEquals("Medio", encuesta.getNivelDificultad());
	        assertEquals(45, encuesta.getDuracionEsperada());
	        assertTrue(encuesta.isObligatorio());
	        assertEquals(p1.getLearningPathsCreados().get(0), encuesta.getLearningPath());
	}
	
	@Test
	public void crearTarea(){
		p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
		String simulatedInput = 
	            "3\n" + 
				"0\n" +
				"a\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "a\n" +
	            "45\n" + // Duración esperada
	            "si\n" // Obligatorio
	            ; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	       Tarea encuesta = (Tarea) p1.crearActividad(new Scanner(System.in));
	        assertNotNull(encuesta);
	        assertEquals("Descripción del recurso", encuesta.getDescripcion());
	        assertEquals("Objetivo del recurso", encuesta.getObjetivo());
	        assertEquals("Medio", encuesta.getNivelDificultad());
	        assertEquals(45, encuesta.getDuracionEsperada());
	        assertTrue(encuesta.isObligatorio());
	        assertEquals(p1.getLearningPathsCreados().get(0), encuesta.getLearningPath());
	}
	
	@Test
	public void crearQuiz(){
		p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
		String simulatedInput = 
	            "4\n" + 
				"0\n" +
				"a\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "a\n" +
	            "45\n" + // Duración esperada
	            "a\n" +
	            "650\n" +
	            "65\n" +
	            "si\n" +
	            "1\n" + // Obligatorio
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "a\n" +
	            "-1\n" +
	            "1\n" +
	            "no\n" 
	            ; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	       Quiz encuesta = (Quiz) p1.crearActividad(new Scanner(System.in));
	        assertNotNull(encuesta);
	        assertEquals("Descripción del recurso", encuesta.getDescripcion());
	        assertEquals("Objetivo del recurso", encuesta.getObjetivo());
	        assertEquals("Medio", encuesta.getNivelDificultad());
	        assertEquals(45, encuesta.getDuracionEsperada());
	        assertTrue(encuesta.isObligatorio());
	        assertEquals(p1.getLearningPathsCreados().get(0), encuesta.getLearningPath());
	}
	
	@Test
	public void crearExamen(){
		p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
		String simulatedInput = 
	            "5\n" + 
				"0\n" +
				"a\n" +
	            "1\n" + // Selección del Learning Path
	            "Descripción del recurso\n" + // Descripción
	            "Objetivo del recurso\n" + // Objetivo
	            "Medio\n" + // Nivel de dificultad
	            "a\n" +
	            "45\n" + // Duración esperada
	            "si\n" + // Obligatorio
	            "pregunta\n" + 
	            "no\n" 
	            ; // Enlace del recurso
	        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
	        
	        // Llama al método y verifica el resultado
	       Examen encuesta = (Examen) p1.crearActividad(new Scanner(System.in));
	        assertNotNull(encuesta);
	        assertEquals("Descripción del recurso", encuesta.getDescripcion());
	        assertEquals("Objetivo del recurso", encuesta.getObjetivo());
	        assertEquals("Medio", encuesta.getNivelDificultad());
	        assertEquals(45, encuesta.getDuracionEsperada());
	        assertTrue(encuesta.isObligatorio());
	        assertEquals(p1.getLearningPathsCreados().get(0), encuesta.getLearningPath());
	}
	
	@Test
	public void testEliminarActividadDeLearningPath() {

		p1.añadirActividadALearningPath(lp, act1);
	    
	    // Caso exitoso
	    p1.eliminarActividadDeLearningPath(act1);
	    assertFalse(lp.getActividades().contains(act1));

	    // Caso: actividad no pertenece al Learning Path
	    Actividad actividadNoPertenece = new Tarea(lp, "b", "b", "b", 5, false, p1);
	    p1.eliminarActividadDeLearningPath(actividadNoPertenece);

	    // Caso: Learning Path no pertenece al profesor
	    Profesor otroProfesor = new Profesor("Jane Doe", "a", "a");
	    otroProfesor.eliminarActividadDeLearningPath(act1);
	    

	}
	
	@Test
	public void testAgregarPrerrequisitoActividad() {

	    Actividad prerrequisito = new Tarea(lp, "b", "b", "a", 5, false, p1);

	    p1.agregarPrerrequisitoActividad(act1, prerrequisito);
	    assertTrue(act1.getPrerrequisitos().contains(prerrequisito));

	    // Caso: permiso denegado
	    Profesor otroProfesor = new Profesor("Jane Doe", "a", "a");
	    otroProfesor.agregarPrerrequisitoActividad(act1, prerrequisito);
	}
	
	@Test
	public void testAgregarSeguimientoActividad() {

	    Actividad prerrequisito = new Tarea(lp, "b", "b", "a", 5, false, p1);

	    p1.agregarActividadSeguimiento(act1, prerrequisito);
	    assertTrue(act1.getActividadSeguimiento().contains(prerrequisito));

	    // Caso: permiso denegado
	    Profesor otroProfesor = new Profesor("Jane Doe", "a", "a");
	    otroProfesor.agregarActividadSeguimiento(act1, prerrequisito);
	}
	
	
}
