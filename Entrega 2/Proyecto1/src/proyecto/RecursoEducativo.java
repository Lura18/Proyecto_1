package proyecto;

import java.util.Date;

public class RecursoEducativo extends Actividad{
	

	//Atributos
	private String tipoRecurso;
	private String enlaceRecurso;

	//Cosntructor
	public RecursoEducativo(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, String tipoRecurso, String enlaceRecurso) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio);
        this.tipoRecurso = tipoRecurso;
        this.enlaceRecurso = enlaceRecurso;
		// TODO Auto-generated constructor stub
	}
	
	//get and set
	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public String getEnlaceRecurso() {
		return enlaceRecurso;
	}

	@Override
	public void realizar(ProgresoActividad progreso) {
		progreso.marcarRealizada("Aprobada", new Date());
		progreso.completarActividad("Aprobada");
        System.out.println("Has completado el recurso educativo: " + descripcion);
		// TODO Auto-generated method stub
		
	}
}
