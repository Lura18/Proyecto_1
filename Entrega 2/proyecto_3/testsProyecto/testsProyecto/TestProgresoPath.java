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
import proyecto.ProgresoPath;
import proyecto.Quiz;
import proyecto.RecursoEducativo;
import proyecto.Registro;
import proyecto.Reseña;
import proyecto.Tarea;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestProgresoPath {
	
	//Atributos
	private LearningPath lp;
	private Registro sistema;
	private Profesor p1;
	private Estudiante e1;
	private Actividad act;
	private ProgresoPath pp;
	
    @BeforeEach
    public void setUp() {
    	sistema = new Registro();
    	p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
    	e1 = new Estudiante("jaime", "jaime@gmail.com", "jaime123");
    	lp = p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
        act = new Tarea(lp, "a", "a", "a", 5, true, p1); // Simula una actividad
        p1.añadirActividadALearningPath(lp, act);
        e1.inscribirLearningPath(lp);
        pp = e1.getProgresoPaths().get(lp);
        
       
    }
	
	@Test
	public void testSetPorcentajePath() {
		
		pp.setPorcentajePath(75.5f);
	    assertEquals(75.5f, pp.getPorcentajePath(), 0.001); // Tolerancia en float
	}

	@Test
	public void testSetCompletado() {
	    pp.setCompletado(true);
	    assertTrue(pp.isCompletado());
	    pp.setCompletado(false);
	    assertFalse(pp.isCompletado());
	}


	@Test
	public void testSetFechaFinPath() {
	    Date fechaFin = new Date(); // Fecha actual
	    pp.setFechaFinPath(fechaFin);
	    assertEquals(fechaFin, pp.getFechaFinPath());
	}
	
	@Test
	public void testAgregarActividadRealizada() {
		pp.agregarActividadRealizada(act);
	    assertTrue(pp.getActividadesRealizadas().contains(act));
	}
	
	@Test
	public void testMarcarCompletado() {
	    Actividad act2 = new Tarea(lp, "b", "b", "b", 5, false, p1);
	    
	    p1.añadirActividadALearningPath(lp, act2);
	    
	    pp.agregarActividadRealizada(act);
	    pp.agregarActividadRealizada(act2);
	    
	    pp.marcarCompletado();
	    
	    assertTrue(pp.isCompletado());
	    assertNotNull(pp.getFechaFinPath());
	}
	
	@Test
	public void testCalcularProgreso() {
		Actividad act2 = new Tarea(lp, "b", "b", "b", 5,true, p1);
	    
	    lp.getActividades().add(act2);
	    
	    // Simula que el estudiante aprobó una actividad
	    Map<Actividad, ProgresoActividad> progresos = new HashMap<>();
	    ProgresoActividad prog1 = new ProgresoActividad(act, e1);
	    ProgresoActividad prog2 = new ProgresoActividad(act2, e1);
	    
	    prog1.setResultado("Aprobada");
	    prog2.setResultado("Por completar");
	    
	    progresos.put(act, prog1);
	    progresos.put(act2, prog2);
	    
	    e1.setProgresosAct(progresos);
	    
	    pp.agregarActividadRealizada(act);
	    pp.calcularProgreso();
	    
	    assertEquals(50.0f, pp.getPorcentajePath(), 0.001); // 50% de progreso
	}
	
	@Test
	public void testActualizarTasas() {
		Actividad act2 = new Tarea(lp, "b", "b", "b", 5,true, p1);
	    
		lp.getActividades().add(act2);
	    
	    // Simula los resultados de las actividades
	    Map<Actividad, ProgresoActividad> progresos = new HashMap<>();
	    ProgresoActividad prog1 = new ProgresoActividad(act, e1);
	    ProgresoActividad prog2 = new ProgresoActividad(act2, e1);
	    
	    prog1.setResultado("Aprobada");
	    prog2.setResultado("Reprobada");
	    
	    progresos.put(act, prog1);
	    progresos.put(act2, prog2);
	    
	    e1.setProgresosAct(progresos);
	    
	    pp.agregarActividadRealizada(act);
	    pp.agregarActividadRealizada(act2);
	    pp.actualizarTasas();
	    
	    assertEquals(50.0f, pp.getTasaExito(), 0.001); // 1 de 2 aprobada
	    assertEquals(50.0f, pp.getTasaFracaso(), 0.001); // 1 de 2 reprobada
	}
}
