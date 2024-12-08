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
import proyecto.Reseña;
import proyecto.Tarea;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Scanner;

public class TestPath {


	//Atributos
	private LearningPath lp;
	private Registro sistema;
	private Profesor p1;
	private Actividad act;
	
    @BeforeEach
    public void setUp() {
    	sistema = new Registro();
    	p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
    	lp = p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
        act = new Tarea(lp, "a", "a", "a", 5, false, p1); // Simula una actividad
        lp.setFechaModificacion(new Date());
       
    }
	
	@Test
	public void testSetTituloActualizaFechaModificacion() {
	    Date before = lp.getFechaCreacion();
	    lp.setTitulo("Nuevo Título");
	    assertEquals("Nuevo Título", lp.getTitulo());
	    assertTrue(lp.getFechaModificacion().after(before));
	}
	
	@Test
	public void testSetDescripcionActualizaFechaModificacion() {
	    Date before = lp.getFechaCreacion();
	    lp.setDescripcion("Nueva Descripción");
	    assertEquals("Nueva Descripción", lp.getDescripcion());
	    assertTrue(lp.getFechaModificacion().after(before));
	}
	
	@Test
	public void testSetObjetivosActualizaFechaModificacion() {
	    Date before = lp.getFechaCreacion();
	    lp.setObjetivos("Nuevos Objetivos");
	    assertEquals("Nuevos Objetivos", lp.getObjetivos());
	    assertTrue(lp.getFechaModificacion().after(before));
	}
	
	@Test
	public void testSetNivelDificultad() {
	    lp.setNivelDificultad("Intermedio");
	    assertEquals("Intermedio", lp.getNivelDificultad());
	}
	
	@Test
	public void testSetFechaCreacion() {
	    Date date = new Date();
	    lp.setFechaCreacion(date);
	    assertEquals(date, lp.getFechaCreacion());
	}
	
	@Test
	public void testSetFechaModificacion() {
	    Date date = new Date();
	    lp.setFechaModificacion(date);
	    assertEquals(date, lp.getFechaModificacion());
	}
	
	@Test
	public void testSetVersion() {
		lp.setVersion(2);
	    assertEquals(2, lp.getVersion());
	}
	
	@Test
	public void testAñadirTiempoLp() {
	    lp.añadirTiempoLp(act);
	    assertEquals(10, lp.getDuracionEstimada());
	}
	
	@Test
	public void testReducirTiempoLp() {
	    lp.añadirTiempoLp(act); // Añadimos primero para luego reducir
	    lp.reducirTiempoLp(act);
	    assertEquals(5, lp.getDuracionEstimada());
	}
	
	@Test
	public void testGetCreador() {

	    assertEquals(p1, lp.getCreador());
	}
	
	@Test
	public void testCalcularPromedioRating() {

        Actividad act2 = new Tarea(lp, "b", "b", "b", 5, false, p1); // Simula una actividad
	    // Crear actividades con ratings y agregarlas al LearningPath
	    act.agregarReseña(new Reseña("Muy útil", 8));
	    act.agregarReseña(new Reseña("Buena", 9));

	    p1.añadirActividadALearningPath(lp, act);
	    p1.añadirActividadALearningPath(lp, act2);
	    
	    act2.agregarReseña(new Reseña("Excelente", 10));


	    // Calcular el promedio de rating
	    double promedioRating = lp.calcularPromedioRating();

	    // Verificar que el promedio sea correcto
	    assertEquals(9.25, promedioRating, 0.01); // Delta para manejar decimales

	}
	
	@Test
	public void testCalcularPromedioRatingCount0() {

    	LearningPath lp2 = p1.crearLearningPath("b", "b", "b", "a", 5, sistema);
	    // Calcular el promedio de rating
	    double promedioRating = lp2.calcularPromedioRating();

	    // Verificar que el promedio sea correcto
	    assertEquals(0, promedioRating, 0.01); // Delta para manejar decimales

	}
}