package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Quiz extends Actividad{
	

	//Atributos
	private double notaAprobacion;
	private double notaObtenida;
	private List<PreguntaMultiple> preguntas;

	//Constructor
	public Quiz(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, double notaAprobacion) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio);
		// TODO Auto-generated constructor stub
		this.notaAprobacion = notaAprobacion;
		this.notaObtenida = 0.0;
		this.preguntas = new ArrayList<PreguntaMultiple>();
	}
	
	//get and set
	public double getNotaAprobacion() {
		return notaAprobacion;
	}
	public double getNotaObtenida() {
		return notaObtenida;
	}

	
	//Metodos
    public void agregarPregunta(PreguntaMultiple pregunta) {
        preguntas.add(pregunta);
    }
  
	
	public String realizarQuiz() {
		double nota = 0;
        Scanner scanner = new Scanner(System.in);
        String resultado = "";
        
		for (PreguntaMultiple p: preguntas) {
			boolean rta = p.mostrarYResolver(scanner);
			if (rta) {
				nota += 1;
			}
		}
		double numPreguntas = preguntas.size();
		this.notaObtenida = (nota/numPreguntas)*100;
		
		System.out.println("Tu nota es: " + notaObtenida);
		if (notaObtenida >= notaAprobacion) {
			resultado = "Aprobada";
			System.out.println("Tu resultado es: " + resultado +"\n");
		} else {
			resultado = "Reprobada";
			System.out.println("Tu resultado es: " + resultado+"\n");
		}
		return resultado;
	}
	
	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
		String rta = realizarQuiz();
		progreso.marcarRealizada("Enviada", new Date());
		progreso.completarActividad(rta);
	}

}
