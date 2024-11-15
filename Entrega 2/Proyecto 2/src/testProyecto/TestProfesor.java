package testProyecto;
import org.junit.jupiter.api.*;

import proyecto.Main2;
import proyecto.Profesor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class TestProfesor {

	Profesor prof1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
	Main2 main = new Main2();
	
	
	@Test
	public void verLearningPathTest(){
		//assertTrue(prof1.verLearningPaths().size() == 0);
	
	}
	
	public void crearLearningPathTest(){
		int cantPaths = prof1.getLearningPathsCreados().size();
		//prof1.crearLearningPath("pruebas integradas con junit", "crear pruebas usando junit", "aprende a crear pruebas con junit", "medio", 20, Registro sistema);
		assertTrue(cantPaths == cantPaths + 1);
		//assert equals para verificar que el formato del learning path quedo creado correctamente
	}
	
	
}
