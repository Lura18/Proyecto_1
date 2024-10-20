package proyecto;

import java.util.Date;
import java.util.Scanner;

public class Tarea extends Actividad {
	
	//Atributos
    private String medioEntrega;

    //Constructor
	public Tarea(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio);
		// TODO Auto-generated constructor stub
		this.medioEntrega = "";
	}
	
	//Get and set
	public String getMedioEntrega() {
		return medioEntrega;
	}
	

	//Metodos

    public void enviarTarea(Scanner scanner) {
	    System.out.print("Ingrese su respuesta: ");
    	this.medioEntrega = scanner.nextLine();
        System.out.println("Has enviado la tarea: " + descripcion);
    }
    
    public void calificarTarea(boolean aprobada, ProgresoActividad progreso) {
    	String rta = "";
    	if (aprobada) {
            rta = "Aprobada";
        } else {
        	rta = "Reprobada";

        }
        progreso.completarActividad(rta);
		System.out.println("La calificación de la tarea " + descripcion + " ha sido completada. Su resultado es: " + rta +"\n");
    }

	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
		enviarTarea(scanner);
		progreso.marcarRealizada("Enviada", new Date());
	}

	
}

