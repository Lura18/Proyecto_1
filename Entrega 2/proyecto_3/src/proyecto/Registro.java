package proyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Persistencia.PersistenciaLearningPaths;
import Persistencia.PersistenciaProgreso;
import Persistencia.PersistenciaUsuarios;

public class Registro {

    // Atributos
    private List<Profesor> profesores;
    private List<Estudiante> estudiantes;
    private List<Usuario> usuarios;
    private List<LearningPath> paths;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaLearningPaths persistenciaLearningPaths;
    private PersistenciaProgreso persistenciaProgreso;

    // Constructor
    public Registro() {
        profesores = new ArrayList<>();
        estudiantes = new ArrayList<>();
        usuarios = new ArrayList<>();
        paths = new ArrayList<>();
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaLearningPaths = new PersistenciaLearningPaths();
        persistenciaProgreso = new PersistenciaProgreso();
    }

    // Métodos para Usuarios
    public void registrarProfesor(Profesor profesor) {
        for (Usuario u : usuarios) {
            if (u instanceof Profesor && u.getCorreo().equals(profesor.getCorreo())) {
                System.out.println("El profesor ya está registrado.");
                return; // Evitar duplicados
            }
        }
        profesores.add(profesor);
        usuarios.add(profesor);
    }

    public void registrarEstudiante(Estudiante estudiante) {
        for (Usuario u : usuarios) {
            if (u instanceof Estudiante && u.getCorreo().equals(estudiante.getCorreo())) {
                System.out.println("El estudiante ya está registrado.");
                return; // Evitar duplicados
            }
        }
        estudiantes.add(estudiante);
        usuarios.add(estudiante);
    }

    public Profesor loginProfesor(String correo, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u instanceof Profesor) {
                Profesor p = (Profesor) u;
                if (p.getCorreo().equals(correo) && p.getContrasena().equals(contrasena)) {
                    return p;
                }
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }

    public Estudiante loginEstudiante(String correo, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u instanceof Estudiante) {
                Estudiante e = (Estudiante) u;
                if (e.getCorreo().equals(correo) && e.getContrasena().equals(contrasena)) {
                    return e;
                }
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }

    public List<Usuario> cargarUsuarios(String archivo) throws IOException {
        usuarios = persistenciaUsuarios.cargarUsuarios(archivo); // Actualiza la lista de usuarios
        // Clasificar usuarios en profesores y estudiantes
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Profesor) {
                profesores.add((Profesor) usuario);
            } else if (usuario instanceof Estudiante) {
                estudiantes.add((Estudiante) usuario);
            }
        }
        return usuarios;
    }

    public void salvarUsuarios(String archivo) throws Exception {
        persistenciaUsuarios.salvarUsuarios(archivo, usuarios);
    }

    public void agregarPaths(LearningPath lp) {
        this.paths.add(lp);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Estudiante> getUsuariosEstudiantes() {
        return this.usuarios.stream()
                .filter(usuario -> usuario instanceof Estudiante)
                .map(usuario -> (Estudiante) usuario)
                .collect(Collectors.toList());
    }

    public List<Estudiante> getEstudiantesInscritosEnLearningPaths(List<LearningPath> learningPaths) {
        List<Estudiante> estudiantesInscritos = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            for (LearningPath lp : learningPaths) {
                if (estudiante.getLearningPathsInscritos().contains(lp)) {
                    estudiantesInscritos.add(estudiante);
                    break; // Evitar duplicados
                }
            }
        }
        return estudiantesInscritos;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<LearningPath> getPaths() {
        return paths;
    }

    // Métodos para sincronización de Learning Paths y Progresos
    public void cargarLearningPathsYProgresos(String archivoLearningPaths, String archivoProgresos) throws IOException {
        // Cargar Learning Paths
        paths = persistenciaLearningPaths.cargarLearningPaths(archivoLearningPaths, new ArrayList<>(), usuarios);

        // Cargar progresos de estudiantes
        List<Estudiante> estudiantesConProgresos = persistenciaProgreso.cargarProgresos(archivoProgresos, paths);

        // Asociar progresos y actividades realizadas a estudiantes
        for (Estudiante estudianteRegistrado : estudiantes) {
            for (Estudiante estudianteConProgreso : estudiantesConProgresos) {
                if (estudianteRegistrado.getCorreo().equals(estudianteConProgreso.getCorreo())) {
                    // Sincronizar progreso de LearningPaths
                    estudianteRegistrado.setProgresoPaths(estudianteConProgreso.getProgresoPaths());
                    estudianteRegistrado.setProgresosAct(estudianteConProgreso.getProgresosAct());
                    estudianteRegistrado.getLearningPathsInscritos().addAll(estudianteConProgreso.getLearningPathsInscritos());

                    // Actualizar actividades realizadas
                    for (LearningPath lp : estudianteConProgreso.getLearningPathsInscritos()) {
                        ProgresoPath progresoPath = estudianteConProgreso.getProgresoPaths().get(lp);
                        if (progresoPath != null) {
                            estudianteRegistrado.getRealizadas().addAll(progresoPath.getActividadesRealizadas());
                        }
                    }
                }
            }
        }
    }

    public void salvarProgresos(String archivoProgresos) throws IOException {
        // Sincronizar actividades realizadas con ProgresoPath antes de guardar
        for (Estudiante estudiante : estudiantes) {
            for (LearningPath lp : estudiante.getLearningPathsInscritos()) {
                ProgresoPath progresoPath = estudiante.getProgresoPaths().get(lp);
                if (progresoPath != null) {
                    progresoPath.setActividadesRealizadas(estudiante.getRealizadas().stream()
                            .filter(a -> a.getLearningPath().equals(lp))
                            .collect(Collectors.toList()));
                }
            }
        }

        // Guardar progresos en archivo
        persistenciaProgreso.guardarProgresos(archivoProgresos, estudiantes);
    }
}

