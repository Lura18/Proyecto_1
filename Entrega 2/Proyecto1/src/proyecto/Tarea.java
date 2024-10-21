package proyecto;

import java.util.Date;
import java.util.Scanner;

public class Tarea extends Actividad {
	
	//Atributos
    private String medioEntrega;

    //Constructor
	public Tarea(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, Profesor creador) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio, creador);
		// TODO Auto-generated constructor stub
		this.medioEntrega = "";
	}
	
	//Get and set
	public String getMedioEntrega() {
		return medioEntrega;
	}
	

	//Metodos

    public void enviarTarea(Scanner scanner) {
	    System.out.print("Ingrese el medio de envío de la tarea: ");
    	this.medioEntrega = scanner.nextLine();
        System.out.println("Has enviado la tarea: " + descripcion);
    }
    
    public void calificarTarea(boolean aprobada, ProgresoActividad progreso, Profesor califica) {
    	if (this.creador == califica) {
        	String rta = "";
        	if (aprobada) {
                rta = "Aprobada";
            } else {
            	rta = "Reprobada";

            }
            progreso.completarActividad(rta);
    		System.out.println("La calificación de la tarea " + descripcion + " ha sido completada. Su resultado es: " + rta +"\n");
    	} else {
    		System.out.println("No tiene los permisos para calfiifcar esta actividad");
    	}
    }
    
	public void cambios(Scanner scanner) {
	    System.out.print("Que desea editar (Descripcion, Objetivo): ");
	    String editar = scanner.nextLine();
	    if (editar.equals("Descripcion")) {
	    	System.out.print("Ingrese la nueva descripcion: ");
		    String d = scanner.nextLine();
		    this.descripcion = d;
	    } else if (editar.equals("Objetivo")) {
	    	System.out.print("Ingrese el objetivo de la actividad: ");
		    String o = scanner.nextLine();
		    this.objetivo = o;
	    } else {
	    	System.out.println("Opción no válida");
	    }
	}

	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
		enviarTarea(scanner);
		progreso.marcarRealizada("Enviada", new Date());
	}

	@Override
	public void editar(Profesor editor) {
		if (this.creador == editor) {
			Scanner scanner = new Scanner(System.in);
			cambios(scanner);
		} else {
			System.out.println("No tiene los permisos para editar la actividad");
		}
		
	}

	
}

