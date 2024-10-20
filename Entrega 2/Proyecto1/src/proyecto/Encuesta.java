package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad {


	//Atributos
	private List<String> preguntas;
	
	//Constructor
	public Encuesta(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio);
		// TODO Auto-generated constructor stub
		this.preguntas = new ArrayList<String>();
	}
	
	//Get and set
	public List<String> getPreguntasAbiertas() {
		return preguntas;
	}


	//Metodos
	
    public void agregarPregunta(String pregunta) {
        preguntas.add(pregunta);
    }
    
	public void verPreguntas() {
		for (int i = 0; i < preguntas.size(); i++) {
		    System.out.println((i + 1) + ". " + preguntas.get(i));
		}

	}


	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
        progreso.marcarRealizada("Aprobada", new Date());
        progreso.completarActividad("Aprobada");
        System.out.println("Has completado la encuesta: " + descripcion + "\n");
	}
}
