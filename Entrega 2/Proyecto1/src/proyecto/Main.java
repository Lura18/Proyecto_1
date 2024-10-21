package proyecto;

public class Main {
    public static void main(String[] args) {
        // Crear un profesor
        Profesor profesor = new Profesor("Prof. García", "garcia@example.com", "password123");
        Profesor profesor2 = new Profesor("Prof. Bocanegra", "bocanegra@example.com", "password123");

        // Crear un Learning Path
        LearningPath lp = profesor.crearLearningPath("Java Programming", "Aprende los fundamentos de Java","Dominar los conceptos de java y POO\n Aprender un nuevo lenguaje" ,"Intermedio");
        LearningPath lp2 = profesor.crearLearningPath("Python Programming", "Aprende los fundamentos de Python","Dominar los conceptos de python\n Aprender un nuevo lenguaje" ,"Principiante");
        
        //Crear estudiante
        Estudiante a = new Estudiante("Sarmiento", "l.sarmientog@example.com", "password123");
        
        // Crear Actividades

        // Crear un quiz y agregar preguntas
        Quiz quiz = new Quiz(lp, "Quiz Geografía Básica", "Reforzar las capitales y océanos", "Bajo", 1, true, 50, profesor);
        Quiz quiz2 = new Quiz(lp2, "Quiz Geografía Básica", "Reforzar las capitales y océanos", "Bajo", 1, true, 50, profesor);
        //profesor.editarActividad(quiz); //Agrega una pregunta al quiz
        //profesor.editarActividad(quiz2); //Agrega una pregunta al quiz

        //Crea un examen
        Examen e1 = new Examen(lp, "Exámen Geografía Básica", "Evaluar los conocimientos en capitales y océanos", "Bajo", 1, true, profesor);
        profesor.editarActividad(e1); //agrega pregunta a un examen
        
        Encuesta en1 = new Encuesta(lp, "Encuesta curso", "Queremos saber porqué escogiste este learningPath", "Bajo", 1, false, profesor);
        profesor.editarActividad(en1); //agrega pregunta a una encuesta
        
        RecursoEducativo r1 = new RecursoEducativo(lp, "Lectura Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, "Video", "Link", profesor);
        //profesor.editarActividad(r1); //edita la informacion recurso/enclace
        
        Tarea t1 = new Tarea(lp, "Ensayo geografía", "Entender capitales y océanos", "Bajo", 1, true, profesor);
        //profesor.editarActividad(t1); //modifica informacion descripcion/objetivo
        
        //Prerrequisitos
        profesor.agregarPrerrequisitoActividad(quiz, en1);
        profesor.agregarPrerrequisitoActividad(quiz, t1);
        profesor.agregarPrerrequisitoActividad(quiz, r1);
        profesor.agregarPrerrequisitoActividad(e1, quiz);
        profesor.agregarPrerrequisitoActividad(e1, r1);
        profesor.agregarPrerrequisitoActividad(e1, t1);
        
        
        //Actividades Recomendadas
        profesor.agregarActividadSeguimiento(en1, r1);
        profesor.agregarActividadSeguimiento(r1, t1);
        profesor.agregarActividadSeguimiento(t1,quiz);
        profesor.agregarActividadSeguimiento(quiz,e1);
        
        //Añadir actividad al learningPath
        profesor.añadirActividadALearningPath(lp, en1);
        profesor.añadirActividadALearningPath(lp, r1);
        profesor.añadirActividadALearningPath(lp, t1);
        profesor.añadirActividadALearningPath(lp, quiz);
        profesor.añadirActividadALearningPath(lp, e1);
        profesor.añadirActividadALearningPath(lp, quiz2);
        
        
        
        a.inscribirseEnLearningPath(lp);
        a.pedirRecomendacionActividad(lp);
        a.iniciarActividad(r1);
        a.realizarActividad(r1);
        a.pedirRecomendacionActividad(lp);
        a.iniciarActividad(t1);
        a.realizarActividad(t1);
        a.pedirRecomendacionActividad(lp);
        //a.iniciarActividad(e1);
        //a.realizarActividad(e1);
        //a.iniciarActividad(quiz);
        //a.realizarActividad(quiz);
        //a.iniciarActividad(quiz2);
        //a.realizarActividad(quiz2);
        
        profesor2.editarActividad(t1);
        Actividad tareaClonada = profesor2.clonarActividad(t1);
        profesor2.editarActividad(tareaClonada);
        Actividad tareaClon2 = profesor.clonarActividad(t1);
        
        
    }

}
