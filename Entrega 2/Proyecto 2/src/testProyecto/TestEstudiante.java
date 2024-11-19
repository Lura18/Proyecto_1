package testProyecto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.ProgresoActividad;
import proyecto.ProgresoPath;
import proyecto.RecursoEducativo;
import proyecto.Registro;

public class TestEstudiante {

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
        e1 = new Estudiante("Juan", "juan@mail.com", "1234");
        p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
        lp = p1.crearLearningPath("pruebas integradas con junit", "crear pruebas usando junit", "aprende a crear pruebas con junit", "medio",20, sistema);
        lp2 = p1.crearLearningPath("pruebas integradas con junit 2", "crear pruebas usando junit 2", "aprende a crear pruebas con junit 2", "medio",20, sistema);
        String entradaUsuario = "1\n1\na\na\na\n5\nsi\na\na";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
        Scanner scanner = new Scanner(entrada);
        act1 = p1.crearActividad(scanner);
        String entradaUsuario2 = "1\n1\na2\na2\na2\n5\nsi\na\na";
        ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
        Scanner scanner2 = new Scanner(entrada2);
        act2 = p1.crearActividad(scanner2);
        p1.agregarPrerrequisitoActividad(act1, act2);
    }
    
    @Test
    public void testTipoUsuario() {
    	assertEquals("Estudiante", e1.getTipoUsuario());
    }
    
    @Test
    public void testInscribirseEnLearningPathVacio() {
    	Registro sistema2 = new Registro();
    	assertNull(e1.inscribirseEnLearningPath(scanner, sistema2), "Debería estar vacío.");
    }
    
    @Test
    public void testInscribirseEnLearningPath() {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Crear objetos necesarios para la prueba
        
        // Ejecutar el método
        LearningPath resultado = e1.inscribirseEnLearningPath(new Scanner(System.in), sistema);
        e1.verLearningPaths();
        // Verificar resultados
        assertNotNull(resultado, "El resultado no debe ser null cuando hay un Learning Path disponible");
        assertEquals(lp, resultado, "El Learning Path seleccionado debe ser el primero en la lista");
}
    @Test
    public void testInscribirseEnLearningPathEntradaNoValida() {
    	
        String entradaUsuario = "texto\n2\n";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
        Scanner scanner = new Scanner(entrada);
        
        // Ejecutar el método
        LearningPath resultado = e1.inscribirseEnLearningPath(scanner, sistema);
        
        // Verificar resultados
        assertNotNull(resultado, "Debería haberse seleccionado un Learning Path válido.");
        assertEquals(lp2, resultado, "Debería haberse seleccionado el segundo Learning Path.");
}

    @Test
    public void testInscribirseEnLearningPathError() {
    	
        String entradaUsuario = "-1\n2\n";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
        Scanner scanner = new Scanner(entrada);

        
        // Ejecutar el método
        LearningPath resultado = e1.inscribirseEnLearningPath(scanner, sistema);
        
        // Verificar resultados
        assertNotNull(resultado, "Debería haberse seleccionado un Learning Path válido.");
        assertEquals(lp2, resultado, "Debería haberse seleccionado el segundo Learning Path.");
}
    
    @Test
    public void testInscribirseEnLearningPathYaInscrito() {
    	
        String entradaUsuario = "1\n";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
        Scanner scanner = new Scanner(entrada);

        
        // Ejecutar el método
        e1.inscribirseEnLearningPath(scanner, sistema);
        
        String entradaUsuario2 = "1\n";
        ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
        Scanner scanner2 = new Scanner(entrada2);
        
        e1.inscribirseEnLearningPath(scanner2, sistema);
        
        // Verificar resultados
        assertEquals(1, e1.getLearningPathsInscritos().size(), "No debería inscribirse nuevamente");
}
  
    
	@Test
	public void testSeleccionarActividad() {
        String entradaUsuario = "1\n";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
        Scanner scanner = new Scanner(entrada);
        

        e1.inscribirseEnLearningPath(scanner, sistema);
        
        String entradaUsuario2 = "1\n";
        ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
        Scanner scanner2 = new Scanner(entrada2);
        
        Actividad act = e1.seleccionarActividad(scanner2, lp);
        assertEquals(act.getObjetivo(), act1.getObjetivo());

	}

	@Test
	public void testSeleccionarActividadSinProgreso() {
	    // Creamos un Learning Path nuevo sin progreso asociado
	    LearningPath lpNuevo = new LearningPath("Learning Path Nuevo", "Descripción","a","medio",p1, 20);
	    String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);

	    Actividad actividadSeleccionada = e1.seleccionarActividad(scanner, lpNuevo);
	    
	    assertNull(actividadSeleccionada, "No debería haber actividad seleccionada si no hay progreso.");
	}
	
	@Test
	public void testSeleccionarActividadVacio() {
        
        String entradaUsuario2 = "2\n";
        ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
        Scanner scanner2 = new Scanner(entrada2);
        e1.inscribirseEnLearningPath(scanner2, sistema);
        Scanner scanner = new Scanner(System.in);
        
        assertNull(e1.seleccionarActividad(scanner, lp2));

	}
	
	@Test
	public void testSeleccionarActividadProgresoCompleto() {
	    // Configuramos el progreso al 100%
	    ProgresoPath progreso = new ProgresoPath(lp, new Date(), e1);
	    progreso.setPorcentajePath(100);
	    progreso.setCompletado(true);
	    e1.getProgresoPaths().put(lp, progreso);
	    
	    String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    
	    // Intentamos seleccionar una actividad
	    Actividad actividadSeleccionada = e1.seleccionarActividad(scanner, lp);
	    
	    // Debería imprimir que ya se completó y retornar null
	    assertNull(actividadSeleccionada, "No debería seleccionar actividad si el progreso está al 100%.");
	}
	
	@Test
	public void testSeleccionarActividadEntradaNoValida() {
		String entradaUsuario2 = "1\n";
	    ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
	    Scanner scanner2 = new Scanner(entrada2);
	    
	    e1.inscribirseEnLearningPath(scanner2, sistema);
	    
	    String entradaUsuario = "-1\ntexto\n1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    
	    Actividad actividadSeleccionada = e1.seleccionarActividad(scanner, lp);
	    
	    assertEquals(act1.getDescripcion(), actividadSeleccionada.getDescripcion(), "Debería seleccionar correctamente la actividad tras entrada inválida inicial.");
	}
	
	@Test
	public void testSeleccionarActividadDoble() {
		String entradaUsuario2 = "1\n";
	    ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
	    Scanner scanner2 = new Scanner(entrada2);
	    
	    e1.inscribirseEnLearningPath(scanner2, sistema);
	    
	    String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    e1.seleccionarActividad(scanner, lp);
	    
	    assertNull(e1.seleccionarActividad(scanner, lp), "No deberia poder otra actividad si hay una en progreso");
	}
	
	@Test
	public void testRealizarActividad() {
		String entradaUsuario2 = "1\n";
	    ByteArrayInputStream entrada2 = new ByteArrayInputStream(entradaUsuario2.getBytes());
	    Scanner scanner2 = new Scanner(entrada2);
	    
	    LearningPath lp3 = e1.inscribirseEnLearningPath(scanner2, sistema);
	    e1.pedirProgresoPath(lp3);
	    e1.pedirRecomendacionActividad(lp3);
	    e1.mostrarProgreso();
	    String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    Actividad act = e1.seleccionarActividad(scanner, lp);
	    e1.realizarActividad(act);
	    ProgresoActividad p = e1.getProgresosAct().get(act);
	    e1.realizarActividad(null);
	    assertTrue(p.isCompletada(), "Deberia estar completada");
	}
	@Test 
	public void testDarReseñaActividad() {
		String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    
		e1.inscribirseEnLearningPath(scanner, sistema);
		
		e1.iniciarActividad(act1);
		e1.realizarActividad(act1);
		
		e1.darReseñaActividad(act1, "Cool", 4);
		
		assertEquals(act1.getReseñas().get(0).getTexto(), "Cool" );
	}
	
	@Test 
	public void testDarReseñaActividadSinRealizar() {
		String entradaUsuario = "1\n";
	    ByteArrayInputStream entrada = new ByteArrayInputStream(entradaUsuario.getBytes());
	    Scanner scanner = new Scanner(entrada);
	    
		e1.inscribirseEnLearningPath(scanner, sistema);
		
		e1.iniciarActividad(act1);
		
		e1.darReseñaActividad(act1, "Cool", 4);
		
		assertNull(act1.getReseñas());
	}

}
