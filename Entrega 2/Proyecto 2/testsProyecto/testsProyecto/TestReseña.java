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
import java.util.Scanner;

public class TestReseña {

	@Test
    void testConstructorValido() {
        Reseña reseña = new Reseña("Excelente actividad", 8.5f);

        assertEquals("Excelente actividad", reseña.getTexto());
        assertEquals(8.5f, reseña.getRating(), 0.01);
    }

    @Test
    void testConstructorInvalidoRatingNegativo() {
        // Verifica que un rating negativo sea manejado adecuadamente
        Reseña reseña = new Reseña("Mala actividad", -5f);

        assertEquals("Mala actividad", reseña.getTexto());
        assertEquals(-5f, reseña.getRating(), 0.01); // Aceptamos el valor negativo pero validamos su uso en otros métodos.
    }

    @Test
    void testHacerReseñaRatingValido() {
    	Registro sistema = new Registro();
    	Profesor p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
    	LearningPath lp = p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
        Actividad actividad = new Tarea(lp, "a", "a", "a", 5, false, p1); // Simula una actividad
       
        Reseña reseña = new Reseña("Actividad interesante", 9f);

        reseña.hacerReseña(actividad);

        // Verifica que la reseña se haya agregado a la actividad
        assertTrue(actividad.getReseñas().contains(reseña));
    }

    @Test
    void testHacerReseñaSinTexto() {
    	Registro sistema = new Registro();
    	Profesor p1 = new Profesor("jaime", "jaime@gmail.com", "jaime123");
    	LearningPath lp = p1.crearLearningPath("a", "a", "a", "a", 5, sistema);
        Actividad actividad = new Tarea(lp, "a", "a", "a", 5, false, p1); // Simula una actividad
        Reseña reseña = new Reseña("", 12f); // Texto vacío

        reseña.hacerReseña(actividad);

        // Verifica que la reseña se haya agregado (a pesar de tener texto vacío)
        assertTrue(actividad.getReseñas().contains(reseña));
    }
    
}
