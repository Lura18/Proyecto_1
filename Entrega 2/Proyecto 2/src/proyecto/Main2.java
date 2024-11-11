package proyecto;

import Persistencia.PersistenciaActividades;
import Persistencia.PersistenciaLearningPaths;
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
            sistema.cargarUsuarios("./datos/" + archivoUsuarios);
            actividades = persistenciaActividades.cargarActividades("./datos/actividades.json");
            learningPaths = persistenciaLearningPaths.cargarLearningPaths("./datos/learning_paths.json", actividades);

            // Verificación de carga de datos
            System.out.println("Actividades cargadas:");
            for (Actividad actividad : actividades) {
                if (actividad != null) {
                    System.out.println("- " + actividad.getDescripcion());
                } else {
                    System.out.println("Actividad nula encontrada en la lista de actividades.");
                }
            }
            
            System.out.println("Learning Paths cargados:");
            for (LearningPath lp : learningPaths) {
                System.out.println("- " + lp.getTitulo());
                System.out.println("  Actividades en el Learning Path:");
                for (Actividad act : lp.getActividades()) {
                    System.out.println("    * " + act.getDescripcion());
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar actividades o Learning Paths, se inicializarán listas vacías.");
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
                Estudiante estudiante = sistema.loginEstudiante(correo, contrasena);
                if (estudiante != null) {
                    System.out.println("¡Bienvenido " + estudiante.getNombre() + "!");
                    ejecutarOpcionesEstudiante(scanner, estudiante);
                } else {
                    System.out.println("Error en la autenticación del estudiante.");
                }
            } else if (tipoUsuario.equals("2")) {
                Profesor profesor = sistema.loginProfesor(correo, contrasena);
                if (profesor != null) {
                    System.out.println("Bienvenido " + profesor.getNombre() + "!");
                    ejecutarOpcionesProfesor(scanner, sistema, profesor);
                } else {
                    System.out.println("Error en la autenticación del profesor.");
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
                ejecutarOpcionesEstudiante(scanner, nuevoEstudiante);
            } else if (tipoRegistro.equals("2")) {
                Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                sistema.registrarProfesor(nuevoProfesor);
                System.out.println("Profesor registrado exitosamente.");
                ejecutarOpcionesProfesor(scanner, sistema, nuevoProfesor);
            } else {
                System.out.println("Opción no válida.");
            }
            sistema.salvarUsuarios("./datos/" + archivoUsuarios);
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
            System.out.println("6. Salir");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
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
                case "2":
                    profesor.verLearningPaths();
                    break;
                case "3":
                    Actividad nuevaActividad = profesor.crearActividad(scanner);
                    if (nuevaActividad != null) {
                        actividades.add(nuevaActividad);
                        System.out.println("Actividad creada y guardada exitosamente.");
                        guardarCambios();
                    } else {
                        System.out.println("Error al crear la actividad.");
                    }
                    break;
                case "4":
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
                case "5":
                    profesor.verProgresoEstudiantes(sistema);
                    break;
                case "6":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void ejecutarOpcionesEstudiante(Scanner scanner, Estudiante estudiante) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nOpciones de Estudiante:");
            System.out.println("1. Inscribirse en un Learning Path");
            System.out.println("2. Ver Learning Paths disponibles");
            System.out.println("3. Ver mi progreso");
            System.out.println("4. Salir");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    if (!learningPaths.isEmpty()) {
                        System.out.println("Seleccione el Learning Path al que desea inscribirse:");
                        for (int i = 0; i < learningPaths.size(); i++) {
                            System.out.println((i + 1) + ". " + learningPaths.get(i).getTitulo());
                        }
                        int lpIndex = Integer.parseInt(scanner.nextLine()) - 1;

                        if (lpIndex >= 0 && lpIndex < learningPaths.size()) {
                            LearningPath lpSeleccionado = learningPaths.get(lpIndex);
                            estudiante.inscripcion(lpSeleccionado);
                            System.out.println("Inscripción exitosa al Learning Path: " + lpSeleccionado.getTitulo());
                        } else {
                            System.out.println("Selección de Learning Path inválida.");
                        }
                    } else {
                        System.out.println("No hay Learning Paths disponibles.");
                    }
                    break;
                case "2":
                    for (LearningPath lp : learningPaths) {
                        System.out.println("Título: " + lp.getTitulo());
                        System.out.println("Descripción: " + lp.getDescripcion());
                        System.out.println("Duración: " + lp.getDuracionEstimada() + " horas");
                        System.out.println("Objetivos: " + lp.getObjetivos());
                        System.out.println("-----------------------------");
                    }
                    break;
                case "3":
                    estudiante.mostrarProgreso();
                    break;
                case "4":
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
            System.out.println("Error al guardar actividades o Learning Paths.");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Registro sistema = new Registro();
        Main2 main = new Main2();
        main.correrAplicacion(scanner, sistema);
    }
}





