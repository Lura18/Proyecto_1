package proyecto;

import java.util.Scanner;


public class Main {
	
	public void correrAplicacion(Scanner scanner, Registro sistema)
	{
	    try
	    {
	        String archivoUsuarios = "usuarios.json"; 
	        sistema.cargarUsuarios( "./datos/" + archivoUsuarios);
	        //Usuarios ya registrados
	        //sistema.registrarProfesor(new Profesor("Gomez", "gomez@example.com", "password123"));
	        //sistema.registrarEstudiante(new Estudiante("Luis", "luis@example.com", "pass456"));
	        
	        //sistema.salvarUsuarios("./datos/" + archivoUsuarios);
	        
	     // Inicio de sesión
            System.out.print("¿Ya tiene una cuenta? (si/no)\n");
            String cuenta = scanner.nextLine().toLowerCase();
            if (cuenta.equals("si")) {
                System.out.print("Ingrese su correo: \n");
                String correo = scanner.nextLine();
                System.out.print("Ingrese su contraseña: \n");
                String contrasena = scanner.nextLine();
                System.out.print("¿Cómo desea entrar?\n1.Estudiante\n2.Profesor\n");
                String tipoUsuario = scanner.nextLine();
                
                if (tipoUsuario.equals("1")) {
                	Estudiante a = sistema.loginEstudiante(correo, contrasena);
                	System.out.println("¡Bienvenido " + a.getNombre() + "!");
                	//logicaEstudiante(a, scanner, sistema);
                } 
                else {
                	Profesor p = sistema.loginProfesor(correo, contrasena);
                	System.out.println("Bienvenido " + p.getNombre() + "!");
                	//logicaProfesor(p, scanner, sistema);
                }
            
            } else if (cuenta.equals("no")) {
                // Registro de usuario
                System.out.print("¿Cómo desea registrarse?\n1. Estudiante\n2. Profesor\n");
                String tipoRegistro = scanner.nextLine();
                System.out.print("Ingrese su nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Ingrese su correo: ");
                String correo = scanner.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String contrasena = scanner.nextLine();

                if (tipoRegistro.equals("1")) {
                    Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena);
                    sistema.registrarEstudiante(nuevoEstudiante);
                    System.out.println("Estudiante registrado exitosamente.");
                } else if (tipoRegistro.equals("2")) {
                    Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                    sistema.registrarProfesor(nuevoProfesor);
                    System.out.println("Profesor registrado exitosamente.");
                } else {
                    System.out.println("Opción no válida.");
                }
                sistema.salvarUsuarios("./datos/" + archivoUsuarios);
            } else {
                System.out.println("Opción no válida.");
            }
	    }
	    catch(Exception e )
	    {
	        e.printStackTrace( );
	    }
	}
	
    public static void main(String[] args) {
        // Funciones de la aplicacion
    	
    	System.out.println("Prueba de Registro/Inicio sesión\n");
    	Registro sistema = new Registro();
    	Main main = new Main();
    	Scanner scanner = new Scanner(System.in);
    	main.correrAplicacion(scanner, sistema);
    	
    	System.out.println("\nFuncionalidades profesores\n");
    	
    	// Profesores para el ejemplo
    	Profesor p = new Profesor("Profe. Bocanegra", "bocanegra@example.com", "password123");
        Profesor p2 = new Profesor("Profe. Castillo", "castillo@example.com", "pass456");
    	
    	// Crear un Learning Path
    	LearningPath lp = p.crearLearningPath("Java Programming", "Aprende los fundamentos de Java","Dominar los conceptos de java y POO\n Aprender un nuevo lenguaje" ,"Intermedio", 8, sistema);
        LearningPath lp2 = p2.crearLearningPath("Python Programming", "Aprende los fundamentos de Python","Dominar los conceptos de python\n Aprender un nuevo lenguaje" ,"Principiante", 5, sistema);
        
        
    	// Crear Actividades

        // Crear un quiz y agregar preguntas
        Quiz quiz = new Quiz(lp, "Quiz Geografía Básica", "Reforzar las capitales y océanos", "Bajo", 1, true, 50, p); //El quiz se crea sin preguntas.
        p.editarActividad(quiz); //Agrega una pregunta al quiz
        
        //Crea un examen
        Examen e1 = new Examen(lp, "Exámen Geografía Básica", "Evaluar los conocimientos en capitales y océanos", "Bajo", 1, true, p); //El examen se crea sin preguntas.
        p.editarActividad(e1); //agrega pregunta a un examen
        
        //Crea una encuesta
        Encuesta en1 = new Encuesta(lp, "Encuesta curso", "Queremos saber porqué escogiste este learningPath", "Bajo", 1, false, p); //La encuesta se crea sin preguntas.
        p.editarActividad(en1); //agrega pregunta a una encuesta
        
        //Crea un recurso educativo
        RecursoEducativo r1 = new RecursoEducativo(lp, "Lectura Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, "Video", "Link", p);
        p.editarActividad(r1); //edita la informacion recurso/enclace
        RecursoEducativo r2 = new RecursoEducativo(lp2, "Lectura Geografía Básica", "Aprender sobre capitales y océanos", "Bajo", 1, true, "Video", "Link", p);
        //p.editarActividad(r2); 
        
        //Crea una tarea
        Tarea t1 = new Tarea(lp, "Ensayo geografía", "Entender capitales y océanos", "Bajo", 1, true, p);
        //p.editarActividad(t1); //modifica informacion descripcion/objetivo
        
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
    	
    	//LearningPath lp3 = p3.crearLearningPath("Python Programming", "Aprende los fundamentos de Python","Dominar los conceptos de python\n Aprender un nuevo lenguaje" ,"Principiante", sis);
    	
    	System.out.println("\nOtras funcionalidades del programa\n");
    	
    	//Funcionalidad editar actividad de otro profesor y clonar
    	//Tarea t1 = new Tarea(lp3, "Ensayo geografía", "Entender capitales y océanos", "Bajo", 1, true, p3);
        //p2.editarActividad(t1);
        //Actividad tareaClonada = p2.clonarActividad(t1);
        //p2.editarActividad(tareaClonada);
        //System.out.print(tareaClonada.descripcion +"\n");
        //System.out.print(tareaClonada.objetivo);
       
        //Funcionalidades estudiante

        
    }
    
    public void logicaProfesor(Profesor p, Scanner scanner, Registro sistema) {
    	//Para los profesores 
    	
    	// Crear un Learning Path
    	LearningPath lp = p.crearLearningPath("Java Programming", "Aprende los fundamentos de Java","Dominar los conceptos de java y POO\n Aprender un nuevo lenguaje" ,"Intermedio", 8, sistema);
        LearningPath lp2 = p.crearLearningPath("Python Programming", "Aprende los fundamentos de Python","Dominar los conceptos de python\n Aprender un nuevo lenguaje" ,"Principiante", 5, sistema);
        
        
        if (lp != null && lp2 != null) {
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
    	}
    }
        

    public void logicaEstudiante(Estudiante a, Scanner scanner, Registro sistema) {
        //Inscribirse a un learning path
        LearningPath lp = a.inscribirseEnLearningPath(scanner, sistema); //Muestra la estructura del elarning path
        
        if (lp != null) {
        	//Pedir recomendación
            a.pedirRecomendacionActividad(lp);
            
            //Puede iniciar cualquier actividad
            Actividad act1 = a.seleccionarActividad(scanner, lp);
            //Realizar la actividad y completarla segun el caso
            a.realizarActividad(act1);
            
            //Pedir nueva recomendación
            a.pedirRecomendacionActividad(lp);
            
            //Iniciar otra actividad
            Actividad act2 = a.seleccionarActividad(scanner, lp);
            
            //No se puede iniciar 2 actividades a la vez
            a.seleccionarActividad(scanner, lp);
            
            //Terminar actividad para poder empezar otra
            a.realizarActividad(act2);
            
            //Iniciar actividad
            Actividad act3 = a.seleccionarActividad(scanner, lp);
            a.realizarActividad(act3);
            
            //dar reseñas
            a.darReseñaActividad(act3, "Muy buena tarea, me gustó mucho", (float) 9.5);
            
            System.out.println("Promedio de rating de la tarea: " + act3.calcularPromedioRating());
            System.out.println("Promedio de rating del Learning Path: " + lp.calcularPromedioRating());
        }
    }
}
