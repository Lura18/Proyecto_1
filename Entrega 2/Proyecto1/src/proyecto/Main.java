package proyecto;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Crear un profesor
        Profesor profesor = new Profesor("Prof. García", "garcia@example.com", "password123");

        // Crear un Learning Path
        LearningPath lp = profesor.crearLearningPath("Java Programming", "Aprende los fundamentos de Java","Dominar los conceptos de java y POO\n Aprender un nuevo lenguaje" ,"Intermedio");
        
        // Crear preguntas
        PreguntaMultiple pregunta1 = new PreguntaMultiple(
                "¿Cuál es la capital de Francia?",
                Arrays.asList("Berlín", "Madrid", "París", "Roma"),
                2,  // Índice de "París"
                Arrays.asList("No, Berlín es la capital de Alemania.", 
                              "No, Madrid es la capital de España.", 
                              "Correcto, París es la capital de Francia.", 
                              "No, Roma es la capital de Italia.")
        );

        PreguntaMultiple pregunta2 = new PreguntaMultiple(
                "¿Cuál es el océano más grande?",
                Arrays.asList("Atlántico", "Índico", "Ártico", "Pacífico"),
                3,  // Índice de "Pacífico"
                Arrays.asList("No, el Atlántico no es el más grande.", 
                              "No, el Índico no es el más grande.", 
                              "No, el Ártico no es el más grande.", 
                              "Correcto, el Pacífico es el océano más grande.")
        );

        // Crear un quiz y agregar preguntas
        Quiz quiz = new Quiz(lp, "Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, 50);
        quiz.agregarPregunta(pregunta1);
        quiz.agregarPregunta(pregunta2);

        // Realizar el quiz
        quiz.realizarQuiz();
        
        Examen e1 = new Examen(lp, "Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true);
        e1.agregarPregunta("¿Cuál es tu color favorito?");
        
        e1.realizarExamen();
        
        Encuesta en1 = new Encuesta(lp, "Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true);
        en1.agregarPregunta("¿Cuál es tu animal favorito?");
        
        en1.verPreguntas();
    }

}
