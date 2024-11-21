package testsProyecto;
import org.junit.jupiter.api.*;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Main2;
import proyecto.Profesor;
import proyecto.Registro;

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
	}
	
	
	
	@Test
	public void testGetLearningPaths(){
		p1.getLearningPathsCreados();
		//assertTrue(prof1.verLearningPaths().size() == 0);
	
	}
	
	public void crearLearningPathTest(){
		int cantPaths = prof1.getLearningPathsCreados().size();
		//prof1.crearLearningPath("pruebas integradas con junit", "crear pruebas usando junit", "aprende a crear pruebas con junit", "medio", 20, Registro sistema);
		assertTrue(cantPaths == cantPaths + 1);
		//assert equals para verificar que el formato del learning path quedo creado correctamente
	}
	
	
}
