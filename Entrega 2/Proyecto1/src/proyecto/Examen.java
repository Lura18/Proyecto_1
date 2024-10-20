package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Examen extends Actividad {
	

	//Atributos
	private List<String> preguntasAbiertas;
	
	//Constructor
	public Examen(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio);
		this.preguntasAbiertas = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	
	//Get and set
	public List<String> getPreguntasAbiertas() {
		return preguntasAbiertas;
	}
	
	
	//Metodos
	
    public void agregarPregunta(String pregunta) {
        preguntasAbiertas.add(pregunta);
    }
    
	public void realizarExamen() {
        try (Scanner scanner = new Scanner(System.in)) {
			for (int i = 0; i < preguntasAbiertas.size(); i++) {
			    System.out.println((i + 1) + ". " + preguntasAbiertas.get(i));
			    System.out.print("Ingrese su respuesta: ");
			    scanner.nextLine();
			}
		    System.out.print("Has terminado el examen. Tu resultado se mostrará una vez el profesor califique tus respuestas.\n");
		}
	}

    public void calificarExamen(boolean aprobada, ProgresoActividad progresoEstudiante) {
    	String rta = "";
        if (aprobada) {
            rta = "Aprobada";
        } else {
        	rta = "Reprobada";
        }
        progresoEstudiante.completarActividad(rta);
    }

	@Override
	public void realizar(ProgresoActividad progresoEstudiante) {
		// TODO Auto-generated method stub
		realizarExamen();
		progresoEstudiante.marcarRealizada("Enviada", new Date());
		
	}

}
