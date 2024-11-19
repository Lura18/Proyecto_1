package proyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Persistencia.PersistenciaUsuarios;

public class Registro {

    // Atributos
    private List<Profesor> profesores;
    private List<Estudiante> estudiantes;
    private List<Usuario> usuarios;
    private List<LearningPath> paths;
    private PersistenciaUsuarios persistenciaUsuarios;

    // Constructor
    public Registro() {
        profesores = new ArrayList<>();
        estudiantes = new ArrayList<>();
        usuarios = new ArrayList<>();
        paths = new ArrayList<>();
        persistenciaUsuarios = new PersistenciaUsuarios(); // Inicialización de persistencia
    }

    public List<LearningPath> getPaths() {
        return paths;
    }

    // Métodos
    public void registrarProfesor(Profesor profesor) {
        for (Usuario u : usuarios) {
            if (u instanceof Profesor && u.getCorreo().equals(profesor.getCorreo())) {
                System.out.println("El profesor ya está registrado.");
                return; // Evitar agregar duplicados
            }
        }
        profesores.add(profesor);
        usuarios.add(profesor);
    }

    public void registrarEstudiante(Estudiante estudiante) {
        for (Usuario u : usuarios) {
            if (u instanceof Estudiante && u.getCorreo().equals(estudiante.getCorreo())) {
                System.out.println("El estudiante ya está registrado.");
                return; 
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

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Estudiante> getEstudiantesInscritosEnLearningPaths(List<LearningPath> learningPaths) {
        List<Estudiante> estudiantesInscritos = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            for (LearningPath lp : learningPaths) {
                if (estudiante.getLearningPathsInscritos().contains(lp)) {
                    estudiantesInscritos.add(estudiante);
                    break; // Para evitar añadir el mismo estudiante varias veces
                }
            }
        }
        return estudiantesInscritos;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
}
