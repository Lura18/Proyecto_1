package proyecto;

import java.util.ArrayList;
import java.util.List;

import Persistencia.PersistenciaUsuarios;

public class Registro {
	
	//Atributos
	private List<Profesor> profesores;
	private List<Estudiante> estudiantes;
	private List<Usuario> usuarios;
    private PersistenciaUsuarios persistencia;

    
    //Constructor
    public Registro() {
    	profesores = new ArrayList<>();
		estudiantes = new ArrayList<>();
        persistencia = new PersistenciaUsuarios();
	}

    
    //Mteodos
	public void registrarProfesor(Profesor profesor) throws Exception {
        for (Profesor p : profesores) {
            if (p.getCorreo().equals(profesor.getCorreo())) {
                throw new Exception("El profesor ya está registrado.");
            }
        }
        profesores.add(profesor);
        usuarios.add(profesor);
    }
	
	public void registrarEstudiante(Estudiante estudiante) throws Exception {
        for (Estudiante e : estudiantes) {
            if (e.getCorreo().equals(estudiante.getCorreo())) {
                throw new Exception("El profesor ya está registrado.");
            }
        }
        estudiantes.add(estudiante);
        usuarios.add(estudiante);
    }
	
    public Profesor loginProfesor(String correo, String contrasena) throws Exception {
        for (Profesor u : profesores) {
            if (u.getCorreo().equals(correo) && u.getContrasena().equals(contrasena)) {
                return  u;
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }


    public Estudiante loginEstudiante(String correo, String contrasena) throws Exception {
        for (Estudiante u : estudiantes) {
            if (u.getCorreo().equals(correo) && u.getContrasena().equals(contrasena)) {
                return  u;
            }
        }
        throw new Exception("Login fallido. Usuario o contraseña incorrectos.");
    }

    public void cargarUsuarios(String archivo) throws Exception {
        usuarios = persistencia.cargarUsuarios(archivo);
    }

    public void salvarUsuarios(String archivo) throws Exception {
        persistencia.salvarUsuarios(archivo, usuarios);
    }
}
