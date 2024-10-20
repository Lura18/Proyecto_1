package proyecto;

import java.util.ArrayList;
import java.util.List;

class Profesor extends Usuario {
	


	//Atributos
    private List<LearningPath> learningPaths;
    private List<LearningPath> learningPathsCreados;
    
    //Constructor
	public Profesor(String nombre, String correo, String contrasena) {
		super(nombre, correo, contrasena);
        this.setLearningPathsCreados(new ArrayList<>());
		// TODO Auto-generated constructor stub
	}
	
	//Get and set
	public List<LearningPath> getLearningPaths() {
		return learningPaths;
	}

	public void setLearningPaths(List<LearningPath> learningPaths) {
		this.learningPaths = learningPaths;
	}
	public List<LearningPath> getLearningPathsCreados() {
		return learningPathsCreados;
	}

	public void setLearningPathsCreados(List<LearningPath> learningPathsCreados) {
		this.learningPathsCreados = learningPathsCreados;
	}
	
	//Metodos
    public LearningPath crearLearningPath(String titulo, String descripcion, String objetivos, String nivelDificultad) {
        LearningPath nuevoLP = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, this, 6);
        learningPathsCreados.add(nuevoLP);
        System.out.println("Learning Path creado exitosamente.");
        return nuevoLP;
    }
    public void editarLearningPath(LearningPath learningPath, String nuevoTitulo, String nuevaDescripcion) {
        if (learningPathsCreados.contains(learningPath)) {
            learningPath.setTitulo(nuevoTitulo);
            learningPath.setDescripcion(nuevaDescripcion);
            System.out.println("Has editado el Learning Path: " + nuevoTitulo);
        } else {
            System.out.println("No tienes permiso para editar este Learning Path.");
        }
    }
    
    public void añadirActividadALearningPath(LearningPath lp, Actividad actividad) {
        if (learningPathsCreados.contains(lp)) {
            lp.añadirActividad(actividad);
            System.out.println("Actividad añadida exitosamente al Learning Path.");
        } else {
            System.out.println("Este Learning Path no fue creado por este profesor.");
        }
    }
        
    public void eliminarActividadDeLearningPath(LearningPath lp, Actividad actividad) {
        if (learningPathsCreados.contains(lp)) {
            lp.eliminarActividad(actividad);
            System.out.println("Actividad eliminada exitosamente del Learning Path.");
        } else {
            System.out.println("Este Learning Path no fue creado por este profesor.");
        }
    	}
	@Override
	public void verLearningPaths() {
        System.out.println("Learning Paths creados:");
        for (LearningPath lp : learningPathsCreados) {
            System.out.println("- " + lp.getTitulo());
        }
	}


}
