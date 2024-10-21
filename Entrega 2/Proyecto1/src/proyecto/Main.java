package proyecto;

import java.io.IOException;
import java.util.Scanner;


public class Main {
	
	public void correrAplicacion(Scanner scanner)
	{
	    try
	    {
	    	Registro sistema = new Registro();
	        String archivo = "usuarios.json"; 
	        sistema.cargarUsuarios( "./datos/" + archivo);
	        
	        //Registrar unos usuarios
	        sistema.registrarProfesor(new Profesor("Gomez", "gomez@example.com", "password123"));
	        sistema.registrarEstudiante(new Estudiante("Luis", "luis@example.com", "pass456"));
	        
	        sistema.salvarUsuarios("./datos/" + archivo);
	        System.out.println("Usuarios guardados exitosamente.");
	        
	     // Inicio de sesión
            System.out.print("¿Ya tiene una cuenta? (si/no)\n");
            String cuenta = scanner.nextLine().toLowerCase();
            if (cuenta.equals("si")) {
                System.out.print("Ingrese su correo: \n");
                String correo = scanner.nextLine();
                System.out.print("Ingrese su contraseña: \n");
                String contrasena = scanner.nextLine();
                System.out.print("¿Cómo desea registrarse?\n1.Estudiante\n2.Profesor\n");
                String tipoUsuario = scanner.nextLine().toLowerCase();
                
                if (tipoUsuario.equals("1")) {
                	Estudiante a = sistema.loginEstudiante(correo, contrasena);
                	System.out.println("¡Bienvenido " + a.getNombre() + "!");
                
                	
                } 
                
                else {
                	Profesor p = sistema.loginProfesor(correo, contrasena);
                	System.out.println("Bienvenido " + p.getNombre() + "!");
                    
                }
            }
            
	    }
	    catch(Exception e )
	    {
	        e.printStackTrace( );
	    }
	}
	
    public static void main(String[] args) {
        // Crear un profesor
    	
    	Main main = new Main();
    	Scanner scanner = new Scanner(System.in);
    	main.correrAplicacion(scanner);

        //Funcionamiento del sistema
    	
    	//Para los profesores 
    	Profesor p = new Profesor("Gomez", "gomez@example.com", "password123");
    	Profesor p2 = new Profesor("Ramirez", "ramirez@example.com", "password123");
    	
    	// Crear un Learning Path
    	LearningPath lp = p.crearLearningPath("Java Programming", "Aprende los fundamentos de Java","Dominar los conceptos de java y POO\n Aprender un nuevo lenguaje" ,"Intermedio");
        LearningPath lp2 = p.crearLearningPath("Python Programming", "Aprende los fundamentos de Python","Dominar los conceptos de python\n Aprender un nuevo lenguaje" ,"Principiante");
        
        // Crear Actividades

        // Crear un quiz y agregar preguntas
        Quiz quiz = new Quiz(lp, "Quiz Geografía Básica", "Reforzar las capitales y océanos", "Bajo", 1, true, 50, p);
        p.editarActividad(quiz); //Agrega una pregunta al quiz
        
        //Crea un examen
        Examen e1 = new Examen(lp, "Exámen Geografía Básica", "Evaluar los conocimientos en capitales y océanos", "Bajo", 1, true, p);
        p.editarActividad(e1); //agrega pregunta a un examen
        
        //Crea una encuesta
        Encuesta en1 = new Encuesta(lp, "Encuesta curso", "Queremos saber porqué escogiste este learningPath", "Bajo", 1, false, p);
        p.editarActividad(en1); //agrega pregunta a una encuesta
        
        //Crea un recurso educativo
        RecursoEducativo r1 = new RecursoEducativo(lp, "Lectura Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, "Video", "Link", p);
        p.editarActividad(r1); //edita la informacion recurso/enclace
        RecursoEducativo r2 = new RecursoEducativo(lp2, "Lectura Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, "Video", "Link", p);
        p.editarActividad(r2); //edita la informacion recurso/enclace
        
        //Crea una tarea
        Tarea t1 = new Tarea(lp, "Ensayo geografía", "Entender capitales y océanos", "Bajo", 1, true, p);
        p.editarActividad(t1); //modifica informacion descripcion/objetivo
        
        //Agrega los prerrequisitos
        p.agregarPrerrequisitoActividad(quiz, en1);
        p.agregarPrerrequisitoActividad(quiz, t1);
        p.agregarPrerrequisitoActividad(quiz, r1);
        p.agregarPrerrequisitoActividad(e1, quiz);
        p.agregarPrerrequisitoActividad(e1, r1);
        p.agregarPrerrequisitoActividad(e1, t1);
        
        //Agrega las actividades Recomendadas
        p.agregarActividadSeguimiento(en1, r1);
        p.agregarActividadSeguimiento(r1, t1);
        p.agregarActividadSeguimiento(t1,quiz);
        p.agregarActividadSeguimiento(quiz,e1);
        
        //Añade las actividades al learningPath correspondiente
        p.añadirActividadALearningPath(lp, en1);
        p.añadirActividadALearningPath(lp, r1);
        p.añadirActividadALearningPath(lp, t1);
        p.añadirActividadALearningPath(lp, quiz);
        p.añadirActividadALearningPath(lp, e1);
        p.añadirActividadALearningPath(lp2, r2);
    	
    	//Funcionalidad editar actividad de otro profesor y clonar
    	
        p2.editarActividad(t1);
        Actividad tareaClonada = p2.clonarActividad(t1);
        p2.editarActividad(tareaClonada);
       
        //Funcionalidades estudiante
        
        Estudiante a = new Estudiante("Luis", "luis@example.com", "pass456");
        
        //Inscribirse a un learning path
        a.inscribirseEnLearningPath(lp); //Muestra la estructura del elarning path
        
        //Pedir recomendación
        a.pedirRecomendacionActividad(lp);
        
        //Puede iniciar cualquier actividad
        a.iniciarActividad(r1);
        
        //Realizar la actividad y completarla segun el caso
        a.realizarActividad(r1);
        
        //Pedir nueva recomendación
        a.pedirRecomendacionActividad(lp);
        
        //Iniciar otra actividad
        a.iniciarActividad(quiz);
        
        //No se puede iniciar 2 actividades a la vez
        a.iniciarActividad(t1);
        
        //Terminar actividad (quiz)
        a.realizarActividad(quiz);
        
        //Iniciar actividad
        a.iniciarActividad(t1);
        a.realizarActividad(t1);
        
        //dar reseñas
        a.darReseñaActividad(t1, "Muy buena tarea, me gustó mucho", (float) 9.5);
        //p.darReseñaActividad(tareaClonada, null, 0);
        
        
        
        //Calcular promedio del rating de la actividad y el learning path
        System.out.println("Promedio de rating de la tarea: " + t1.calcularPromedioRating());
        System.out.println("Promedio de rating del Learning Path: " + lp.calcularPromedioRating());
        
    }

}
