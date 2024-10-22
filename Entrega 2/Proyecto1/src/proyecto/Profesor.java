package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profesor extends Usuario {
	


	//Atributos
    private List<LearningPath> learningPathsCreados;
    
    //Constructor
	public Profesor(String nombre, String correo, String contrasena) {
		super(nombre, correo, contrasena);
        this.learningPathsCreados = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}
	
	//Get and set
	public List<LearningPath> getLearningPathsCreados() {
		return learningPathsCreados;
	}

	
	//Metodos
	
	@Override
	public void verLearningPaths() {
        System.out.println("Learning Paths creados:");
        for (LearningPath lp : learningPathsCreados) {
            System.out.println("- " + lp.getTitulo());
        }
	}
	
	@Override
	public String getTipoUsuario() {
		return "Profesor";
	}
	
	
    public LearningPath crearLearningPath(String titulo, String descripcion, String objetivos, String nivelDificultad, int duracionEstimada, Registro sistema) {
    	
    	LearningPath nuevoLP = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, this, duracionEstimada);
        learningPathsCreados.add(nuevoLP);
        System.out.println("Learning Path creado exitosamente.");
        sistema.agregarPaths(nuevoLP);
        return nuevoLP;
    }
    
    public void añadirActividadALearningPath(LearningPath lp, Actividad actividad) {
        if (learningPathsCreados.contains(lp)) {
        	actividad.setLearningPath(lp);
        	lp.getActividades().add(actividad);
        	lp.añadirTiempoLp(actividad);
            System.out.println("Actividad añadida exitosamente al Learning Path.");
            } else {
            System.out.println("Este Learning Path no fue creado por este profesor.");
        }
	}

        
    public void eliminarActividadDeLearningPath(LearningPath lp, Actividad actividad) {
        if (learningPathsCreados.contains(lp)) {
            if (lp.getActividades().remove(actividad)) {
                lp.reducirTiempoLp(actividad);
                System.out.println("Actividad eliminada exitosamente del Learning Path.");
            } else {
                System.out.println("La actividad no pertenece a este Learning Path.");
            }
        } else {
            System.out.println("Este Learning Path no fue creado por este profesor.");
        }
    }
	
	//El profesor edita el learning path editando sus actividades.
	public void editarActividad(Actividad actividad) {
		actividad.editar(this);
		actividad.getLearningPath().setFechaModificacion(new Date());
		actividad.getLearningPath().setVersion(actividad.getLearningPath().getVersion()+1);
	}
	
	public void agregarPrerrequisitoActividad(Actividad actividad, Actividad prerrequisito) {
		if (this.equals(actividad.getCreador())) {
			actividad.agregarPrerrequisito(prerrequisito);
		} else {
			System.out.println("No tiene los permisos para agregar el prerrequisito");
		}
	}
	
	public void agregarActividadSeguimiento(Actividad actividad, Actividad seguimiento) {
		if (this.equals(actividad.getCreador())) {
			actividad.agregarActividadSeguimiento(seguimiento);
		} else {
			System.out.println("No tiene los permisos para agregar la actividad de seguimiento");
		}
	}
	
	//Clonar actividad
	public Actividad clonarActividad(Actividad original) {
	    if (!original.getCreador().equals(this)) {
	        Actividad clon = original.clonarActividad(this);
	        if (clon != null) {
	        	System.out.println("Actividad clonada y guardada exitosamente.");
	        	return clon;
	        } else {
	            System.out.println("Error al clonar la actividad.");
	            return null;
	        }
	    } else {
	        System.out.println("No puedes clonar una actividad que tú mismo has creado.");
	        return null;
	    }
	}


}
