package proyecto;

import Persistencia.PersistenciaActividades;
import Persistencia.PersistenciaLearningPaths;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {

    private List<LearningPath> learningPaths;
    private List<Actividad> actividades;
    private PersistenciaActividades persistenciaActividades;
    private PersistenciaLearningPaths persistenciaLearningPaths;
    private String archivoUsuarios = "usuarios.json";

    public Main2() {
        learningPaths = new ArrayList<>();
        actividades = new ArrayList<>();
        persistenciaActividades = new PersistenciaActividades();
        persistenciaLearningPaths = new PersistenciaLearningPaths();
    }

    public void correrAplicacion(Scanner scanner, Registro sistema) throws Exception {
        try {
            // Verificar si el archivo de usuarios existe
            File archivo = new File("./datos/" + archivoUsuarios);
            if (!archivo.exists()) {
                System.out.println("Archivo de usuarios no encontrado: " + archivo.getAbsolutePath());
            } else {
                System.out.println("Archivo de usuarios encontrado: " + archivo.getAbsolutePath());
            }

            // Cargar usuarios
            sistema.cargarUsuarios("./datos/" + archivoUsuarios);
            System.out.println("Usuarios cargados correctamente:");

            for (Usuario usuario : sistema.getUsuarios()) {
                System.out.println("- " + usuario.getNombre() + " (" + usuario.getTipoUsuario() + ")");
            }

            // Cargar actividades
            actividades = persistenciaActividades.cargarActividades("./datos/actividades.json");
            System.out.println("Actividades cargadas:");
            for (Actividad actividad : actividades) {
                if (actividad != null) {
                    System.out.println("- " + actividad.getDescripcion());
                } else {
                    System.out.println("Actividad nula encontrada en la lista de actividades.");
                }
            }

            // Cargar learning paths con la lista de usuarios
            learningPaths = persistenciaLearningPaths.cargarLearningPaths("./datos/learning_paths.json", actividades, sistema.getUsuarios());
            System.out.println("Learning Paths cargados:");
            for (LearningPath lp : learningPaths) {
                System.out.println("- " + lp.getTitulo());
                System.out.println("  Actividades en el Learning Path:");
                for (Actividad act : lp.getActividades()) {
                    System.out.println("    * " + act.getDescripcion());
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            learningPaths = new ArrayList<>();
            actividades = new ArrayList<>();
        }

        System.out.print("¿Ya tiene una cuenta? (si/no)\n");
        String cuenta = scanner.nextLine().toLowerCase();
        if (cuenta.equals("si")) {
            System.out.print("Ingrese su correo: \n");
            String correo = scanner.nextLine();
            System.out.print("Ingrese su contraseña: \n");
            String contrasena = scanner.nextLine();
            System.out.print("¿Cómo desea entrar?\n1. Estudiante\n2. Profesor\n");
            String tipoUsuario = scanner.nextLine();

            if (tipoUsuario.equals("1")) {
                try {
                    Estudiante estudiante = sistema.loginEstudiante(correo, contrasena);
                    System.out.println("¡Bienvenido " + estudiante.getNombre() + "!");
                    ejecutarOpcionesEstudiante(scanner, estudiante, sistema);
                } catch (Exception e) {
                    System.out.println("Error en la autenticación del estudiante: " + e.getMessage());
                }
            } else if (tipoUsuario.equals("2")) {
                try {
                    Profesor profesor = sistema.loginProfesor(correo, contrasena);
                    System.out.println("Bienvenido " + profesor.getNombre() + "!");
                    ejecutarOpcionesProfesor(scanner, sistema, profesor);
                } catch (Exception e) {
                    System.out.println("Error en la autenticación del profesor: " + e.getMessage());
                }
            }
        } else if (cuenta.equals("no")) {
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
                sistema.salvarUsuarios("./datos/" + archivoUsuarios); // Guardar cambios
                ejecutarOpcionesEstudiante(scanner, nuevoEstudiante, sistema);
            } else if (tipoRegistro.equals("2")) {
                Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                sistema.registrarProfesor(nuevoProfesor);
                System.out.println("Profesor registrado exitosamente.");
                sistema.salvarUsuarios("./datos/" + archivoUsuarios); // Guardar cambios
                ejecutarOpcionesProfesor(scanner, sistema, nuevoProfesor);
            } else {
                System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Opción no válida.");
        }

        guardarCambios();
    }

    private void ejecutarOpcionesProfesor(Scanner scanner, Registro sistema, Profesor profesor) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nOpciones de Profesor:");
            System.out.println("1. Crear Learning Path");
            System.out.println("2. Ver Learning Paths");
            System.out.println("3. Crear Actividad");
            System.out.println("4. Agregar Actividad a Learning Path");
            System.out.println("5. Ver Progreso de Estudiantes");
            System.out.println("6. Eliminar Actividad de Learning Path");
            System.out.println("7. Agregar Prerrequisito a Actividad");
            System.out.println("8. Agregar Actividad de Seguimiento");
            System.out.println("9. Clonar Actividad");
            System.out.println("10. Calificar Actividades");
            System.out.println("11. Salir");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1": // Crear Learning Path
                    System.out.print("Ingrese el título del Learning Path: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese la descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingrese los objetivos: ");
                    String objetivos = scanner.nextLine();
                    System.out.print("Ingrese el nivel de dificultad: ");
                    String nivelDificultad = scanner.nextLine();
                    System.out.print("Ingrese la duración estimada en horas: ");
                    int duracionEstimada = Integer.parseInt(scanner.nextLine());

                    LearningPath nuevoLp = profesor.crearLearningPath(titulo, descripcion, objetivos, nivelDificultad, duracionEstimada, sistema);
                    if (nuevoLp != null) {
                        learningPaths.add(nuevoLp);
                        System.out.println("Learning Path creado y guardado exitosamente.");
                        guardarCambios();
                    } else {
                        System.out.println("Error al crear el Learning Path.");
                    }
                    break;

                case "2": // Ver Learning Paths
                    profesor.verLearningPaths();
                    //System.out.println(profesor.getLearningPathsCreados());
                    break;

                
                case "3":
                    System.out.println("Creando una nueva actividad...");
                    System.out.println("¿Qué tipo de actividad quiere crear? Seleccione el número:");
                    System.out.println("1. Recurso Educativo");
                    System.out.println("2. Encuesta");
                    System.out.println("3. Tarea");
                    System.out.println("4. Quiz");
                    System.out.println("5. Examen");

                    int tipoActividad = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea

                    System.out.print("Ingrese la descripción: ");
                    String descripcionActividad = scanner.nextLine();

                    System.out.print("Ingrese el objetivo: ");
                    String objetivoActividad = scanner.nextLine();

                    System.out.print("Ingrese el nivel de dificultad (bajo, medio, alto): ");
                    String nivelDificultadActividad = scanner.nextLine();

                    System.out.print("Ingrese la duración esperada (en minutos): ");
                    int duracionEsperadaActividad = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea

                    System.out.print("¿Es obligatorio? (true/false): ");
                    boolean obligatorioActividad = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir salto de línea

                    // Utilizar el profesor autenticado como creador
                    switch (tipoActividad) {
                        case 1: // Recurso Educativo
                            System.out.println("Creando un Recurso Educativo...");
                            System.out.print("Ingrese el tipo de recurso: ");
                            String tipoRecurso = scanner.nextLine();
                            System.out.print("Ingrese el enlace del recurso: ");
                            String enlaceRecurso = scanner.nextLine();

                            RecursoEducativo recurso = new RecursoEducativo(null, descripcionActividad, objetivoActividad,
                                    nivelDificultadActividad, duracionEsperadaActividad, obligatorioActividad, tipoRecurso,
                                    enlaceRecurso, profesor); // profesor autenticado
                            actividades.add(recurso);
                            System.out.println("Recurso Educativo creado: " + recurso.getDescripcion());
                            break;

                        case 2: // Encuesta
                            Encuesta encuesta = new Encuesta(null, descripcionActividad, objetivoActividad,
                                    nivelDificultadActividad, duracionEsperadaActividad, obligatorioActividad, profesor); // profesor autenticado
                            System.out.println("Ingrese las preguntas de la encuesta (escriba 'fin' para terminar):");
                            while (true) {
                                System.out.print("Pregunta: ");
                                String pregunta = scanner.nextLine();
                                if (pregunta.equalsIgnoreCase("fin"))
                                    break;
                                encuesta.getPreguntasAbiertas().add(pregunta);
                            }
                            actividades.add(encuesta);
                            System.out.println("Encuesta creada: " + encuesta.getDescripcion());
                            break;

                        case 3: // Tarea
                            Tarea tarea = new Tarea(null, descripcionActividad, objetivoActividad,
                                    nivelDificultadActividad, duracionEsperadaActividad, obligatorioActividad, profesor); // profesor autenticado
                            actividades.add(tarea);
                            System.out.println("Tarea creada: " + tarea.getDescripcion());
                            break;

                        case 4: // Quiz
                            System.out.print("Ingrese la nota de aprobación para el Quiz: ");
                            double notaAprobacion = scanner.nextDouble();
                            scanner.nextLine(); // Consumir salto de línea

                            System.out.print("Seleccione el tipo de pregunta (1. Múltiple opción, 2. Verdadero/Falso): ");
                            int tipoPregunta = scanner.nextInt();
                            scanner.nextLine(); // Consumir salto de línea

                            String tipoPreguntaTexto = tipoPregunta == 1 ? "Múltiple" : "VoF";
                            Quiz quiz = new Quiz(null, descripcionActividad, objetivoActividad, nivelDificultadActividad,
                                    duracionEsperadaActividad, obligatorioActividad, notaAprobacion, profesor, tipoPreguntaTexto); // profesor autenticado
                            actividades.add(quiz);
                            System.out.println("Quiz creado: " + quiz.getDescripcion());
                            break;

                        case 5: // Examen
                            Examen examen = new Examen(null, descripcionActividad, objetivoActividad, nivelDificultadActividad,
                                    duracionEsperadaActividad, obligatorioActividad, profesor); // profesor autenticado
                            System.out.println("Ingrese las preguntas del examen (escriba 'fin' para terminar):");
                            while (true) {
                                System.out.print("Pregunta: ");
                                String pregunta = scanner.nextLine();
                                if (pregunta.equalsIgnoreCase("fin"))
                                    break;
                                examen.getPreguntasAbiertas().add(pregunta);
                            }
                            actividades.add(examen);
                            System.out.println("Examen creado: " + examen.getDescripcion());
                            break;

                        default:
                            System.out.println("Opción inválida.");
                    }
                    break;





                case "4": // Agregar Actividad a Learning Path
                    if (!learningPaths.isEmpty()) {
                        System.out.println("Seleccione el Learning Path al que desea agregar la actividad:");
                        for (int i = 0; i < learningPaths.size(); i++) {
                            System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                        }
                        int lpIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (lpIndex >= 0 && lpIndex < learningPaths.size()) {
                            LearningPath lpSeleccionado = learningPaths.get(lpIndex);

                            if (!actividades.isEmpty()) {
                                System.out.println("Seleccione la actividad que desea agregar:");
                                for (int i = 0; i < actividades.size(); i++) {
                                    System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                                }
                                int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                                if (actividadIndex >= 0 && actividadIndex < actividades.size()) {
                                    Actividad actividadSeleccionada = actividades.get(actividadIndex);
                                    profesor.añadirActividadALearningPath(lpSeleccionado, actividadSeleccionada);
                                    System.out.println("Actividad añadida al Learning Path exitosamente.");
                                    guardarCambios();
                                } else {
                                    System.out.println("Selección de actividad inválida.");
                                }
                            } else {
                                System.out.println("No hay actividades disponibles para agregar.");
                            }
                        } else {
                            System.out.println("Selección de Learning Path inválida.");
                        }
                    } else {
                        System.out.println("No hay Learning Paths disponibles.");
                    }
                    break;

                case "5": // Ver Progreso de Estudiantes
                    profesor.verProgresoEstudiantes(sistema);
                    break;

                case "6": // Eliminar Actividad de Learning Path
                    if (!learningPaths.isEmpty()) {
                        System.out.println("Seleccione el Learning Path del que desea eliminar una actividad:");
                        for (int i = 0; i < learningPaths.size(); i++) {
                            System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                        }
                        int lpIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (lpIndex >= 0 && lpIndex < learningPaths.size()) {
                            LearningPath lpSeleccionado = learningPaths.get(lpIndex);
                            System.out.println("Seleccione la actividad a eliminar:");
                            List<Actividad> actividadesLP = lpSeleccionado.getActividades();
                            for (int i = 0; i < actividadesLP.size(); i++) {
                                System.out.println((i + 1) + ". " + actividadesLP.get(i).getDescripcion());
                            }
                            int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                            if (actividadIndex >= 0 && actividadIndex < actividadesLP.size()) {
                                Actividad actividadSeleccionada = actividadesLP.get(actividadIndex);
                                profesor.eliminarActividadDeLearningPath(actividadSeleccionada);
                                guardarCambios();
                            } else {
                                System.out.println("Selección de actividad inválida.");
                            }
                        } else {
                            System.out.println("Selección de Learning Path inválida.");
                        }
                    } else {
                        System.out.println("No hay Learning Paths disponibles.");
                    }
                    break;

                case "7": // Agregar Prerrequisito a Actividad
                    if (!actividades.isEmpty()) {
                        System.out.println("Seleccione la actividad a la que desea agregar un prerrequisito:");
                        for (int i = 0; i < actividades.size(); i++) {
                            System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                        }
                        int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (actividadIndex >= 0 && actividadIndex < actividades.size()) {
                            Actividad actividadPrincipal = actividades.get(actividadIndex);

                            System.out.println("Seleccione la actividad que desea establecer como prerrequisito:");
                            for (int i = 0; i < actividades.size(); i++) {
                                System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                            }
                            int prerrequisitoIndex = Integer.parseInt(scanner.nextLine()) - 1;

                            if (prerrequisitoIndex >= 0 && prerrequisitoIndex < actividades.size()) {
                                Actividad prerrequisito = actividades.get(prerrequisitoIndex);
                                profesor.agregarPrerrequisitoActividad(actividadPrincipal, prerrequisito);
                                System.out.println("Prerrequisito agregado exitosamente.");
                                guardarCambios();
                            } else {
                                System.out.println("Selección de prerrequisito inválida.");
                            }
                        } else {
                            System.out.println("Selección de actividad inválida.");
                        }
                    } else {
                        System.out.println("No hay actividades disponibles.");
                    }
                    break;

                case "8": // Agregar Actividad de Seguimiento
                    if (!actividades.isEmpty()) {
                        System.out.println("Seleccione la actividad a la que desea agregar una actividad de seguimiento:");
                        for (int i = 0; i < actividades.size(); i++) {
                            System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                        }
                        int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (actividadIndex >= 0 && actividadIndex < actividades.size()) {
                            Actividad actividadPrincipal = actividades.get(actividadIndex);

                            System.out.println("Seleccione la actividad que desea establecer como seguimiento:");
                            for (int i = 0; i < actividades.size(); i++) {
                                System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                            }
                            int seguimientoIndex = Integer.parseInt(scanner.nextLine()) - 1;

                            if (seguimientoIndex >= 0 && seguimientoIndex < actividades.size()) {
                                Actividad seguimiento = actividades.get(seguimientoIndex);
                                profesor.agregarActividadSeguimiento(actividadPrincipal, seguimiento);
                                System.out.println("Actividad de seguimiento agregada exitosamente.");
                                guardarCambios();
                            } else {
                                System.out.println("Selección de actividad de seguimiento inválida.");
                            }
                        } else {
                            System.out.println("Selección de actividad inválida.");
                        }
                    } else {
                        System.out.println("No hay actividades disponibles.");
                    }
                    break;

                case "9": // Clonar Actividad
                    if (!actividades.isEmpty()) {
                        System.out.println("Seleccione la actividad que desea clonar:");
                        for (int i = 0; i < actividades.size(); i++) {
                            System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                        }
                        int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (actividadIndex >= 0 && actividadIndex < actividades.size()) {
                            Actividad original = actividades.get(actividadIndex);
                            Actividad clon = profesor.clonarActividad(original);
                            if (clon != null) {
                                actividades.add(clon);
                                System.out.println("Actividad clonada exitosamente.");
                                guardarCambios();
                            }
                        } else {
                            System.out.println("Selección de actividad inválida.");
                        }
                    } else {
                        System.out.println("No hay actividades disponibles para clonar.");
                    }
                    break;

                case "10": // Calificar Actividades
                    if (!actividades.isEmpty()) {
                        System.out.println("Seleccione la actividad que desea calificar:");
                        for (int i = 0; i < actividades.size(); i++) {
                            System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                        }
                        int actividadIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (actividadIndex >= 0 && actividadIndex < actividades.size()) {
                            Actividad actividad = actividades.get(actividadIndex);
                            profesor.calificarActividad(actividad, scanner);
                            guardarCambios();
                        } else {
                            System.out.println("Selección de actividad inválida.");
                        }
                    } else {
                        System.out.println("No hay actividades disponibles para calificar.");
                    }
                    break;

                case "11": // Salir
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void ejecutarOpcionesEstudiante(Scanner scanner, Estudiante estudiante, Registro sistema) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nOpciones de Estudiante:");
            System.out.println("1. Inscribirse en un Learning Path");
            System.out.println("2. Ver Learning Paths inscritos");
            System.out.println("3. Iniciar Actividad");
            System.out.println("4. Completar Actividad");
            System.out.println("5. Ver mi progreso");
            System.out.println("6. Pedir recomendación de actividad");
            System.out.println("7. Pedir progreso de Learning Path");
            System.out.println("8. Dar reseña de actividad");
            System.out.println("9. Salir");

            String opcion = scanner.nextLine();
            switch (opcion) {
            case "1": // Inscribirse en un Learning Path
                List<LearningPath> disponibles = estudiante.getLearningPathsDisponibles(learningPaths);
                if (disponibles.isEmpty()) {
                    System.out.println("No hay Learning Paths disponibles para inscripción en este momento.");
                } else {
                    System.out.println("Seleccione un Learning Path para inscribirse:");
                    for (int i = 0; i < disponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + disponibles.get(i).getTitulo());
                    }
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index >= 0 && index < disponibles.size()) {
                        LearningPath lpSeleccionado = disponibles.get(index);
                        estudiante.inscribirLearningPath(lpSeleccionado);
                        System.out.println("Inscripción exitosa al Learning Path: " + lpSeleccionado.getTitulo());
                    } else {
                        System.out.println("Selección inválida.");
                    }
                }
                break;
            case "2": // Ver Learning Paths inscritos
                List<LearningPath> inscritos = estudiante.getLearningPathsInscritos();
                if (inscritos.isEmpty()) {
                    System.out.println("No tienes Learning Paths inscritos.");
                } else {
                    System.out.println("Learning Paths en los que estás inscrito:");
                    for (LearningPath lp : inscritos) {
                        System.out.println("- " + lp.getTitulo());
                    }
                }
                break;
            case "3": // Iniciar Actividad
                List<LearningPath> lpInscritos = estudiante.getLearningPathsInscritos();
                if (lpInscritos.isEmpty()) {
                    System.out.println("No tienes Learning Paths inscritos para iniciar actividades.");
                } else {
                    System.out.println("Seleccione el Learning Path:");
                    for (int i = 0; i < lpInscritos.size(); i++) {
                        System.out.println((i + 1) + ". " + lpInscritos.get(i).getTitulo());
                    }
                    int lpIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (lpIndex >= 0 && lpIndex < lpInscritos.size()) {
                        LearningPath lpSeleccionado = lpInscritos.get(lpIndex);
                        Actividad actividad = estudiante.seleccionarActividad(scanner, lpSeleccionado);
                        if (actividad != null) {
                            estudiante.iniciarActividad(actividad);
                            System.out.println("Actividad " + actividad.getDescripcion() + " iniciada.");
                        }
                    } else {
                        System.out.println("Selección inválida.");
                    }
                }
                case "4": // Completar Actividad
                    System.out.println("Seleccione el Learning Path:");
                    for (int i = 0; i < learningPaths.size(); i++) {
                        System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                    }
                    int lpIndexCompletar = Integer.parseInt(scanner.nextLine()) - 1;
                    if (lpIndexCompletar >= 0 && lpIndexCompletar < learningPaths.size()) {
                        LearningPath lpSeleccionado = learningPaths.get(lpIndexCompletar);
                        Actividad actividad = estudiante.seleccionarActividad(scanner, lpSeleccionado);
                        if (actividad != null) {
                            estudiante.realizarActividad(actividad);
                            System.out.println("Actividad " + actividad.getDescripcion() + " completada.");
                        }
                    } else {
                        System.out.println("Selección de Learning Path inválida.");
                    }
                    break;
                case "5": // Ver mi progreso
                    estudiante.mostrarProgreso();
                    break;
                case "6": // Pedir recomendación de actividad
                    System.out.println("Seleccione el Learning Path:");
                    for (int i = 0; i < learningPaths.size(); i++) {
                        System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                    }
                    int lpIndexRecomendacion = Integer.parseInt(scanner.nextLine()) - 1;
                    if (lpIndexRecomendacion >= 0 && lpIndexRecomendacion < learningPaths.size()) {
                        LearningPath lpSeleccionado = learningPaths.get(lpIndexRecomendacion);
                        estudiante.pedirRecomendacionActividad(lpSeleccionado);
                    } else {
                        System.out.println("Selección de Learning Path inválida.");
                    }
                    break;
                case "7": // Pedir progreso de Learning Path
                    System.out.println("Seleccione el Learning Path:");
                    for (int i = 0; i < learningPaths.size(); i++) {
                        System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                    }
                    int lpIndexProgreso = Integer.parseInt(scanner.nextLine()) - 1;
                    if (lpIndexProgreso >= 0 && lpIndexProgreso < learningPaths.size()) {
                        LearningPath lpSeleccionado = learningPaths.get(lpIndexProgreso);
                        estudiante.pedirProgresoPath(lpSeleccionado);
                    } else {
                        System.out.println("Selección de Learning Path inválida.");
                    }
                    break;
                case "8": // Dar reseña de actividad
                    System.out.println("Seleccione la actividad para dar una reseña:");
                    for (int i = 0; i < actividades.size(); i++) {
                        System.out.println((i + 1) + ". " + actividades.get(i).getDescripcion());
                    }
                    int actividadIndexReseña = Integer.parseInt(scanner.nextLine()) - 1;
                    if (actividadIndexReseña >= 0 && actividadIndexReseña < actividades.size()) {
                        Actividad actividad = actividades.get(actividadIndexReseña);
                        System.out.print("Ingrese su reseña: ");
                        String texto = scanner.nextLine();
                        System.out.print("Ingrese la calificación (0-5): ");
                        float rating = Float.parseFloat(scanner.nextLine());
                        estudiante.darReseñaActividad(actividad, texto, rating);
                    } else {
                        System.out.println("Selección de actividad inválida.");
                    }
                    break;
                case "9": // Salir
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }


    private void guardarCambios() {
        try {
            persistenciaLearningPaths.salvarLearningPaths("./datos/learning_paths.json", learningPaths);
            persistenciaActividades.salvarActividades("./datos/actividades.json", actividades);
            System.out.println("Actividades y Learning Paths guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar actividades o Learning Paths: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Registro sistema = new Registro();
        Main2 main = new Main2();
        main.correrAplicacion(scanner, sistema);
    }
}






