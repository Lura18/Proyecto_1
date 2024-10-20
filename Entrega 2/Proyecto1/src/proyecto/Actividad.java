package proyecto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Actividad {
	
	//Atributos
	protected LearningPath learningPath;
	protected String descripcion;
	protected String objetivo;
	protected String nivelDificultad;
	protected int duracionEsperada; //En minutos
	protected Date fechaLimite;
	protected boolean obligatorio;
    protected List<Actividad> actividadesSeguimiento;
    protected List<Actividad> prerrequisitos;
	
	//Constructor
	public Actividad(LearningPath lp, String descripcion, String objetivo, String nivelDificultad,
			int duracionEsperada, boolean obligatorio) {
		super();
		this.learningPath = lp;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracionEsperada = duracionEsperada;
		this.fechaLimite = null;
		this.obligatorio = obligatorio;
		this.actividadesSeguimiento = new ArrayList<Actividad>();
		this.prerrequisitos = new ArrayList<Actividad>();
	}
	
	//Get and set
	public LearningPath getLearningPath() {
		return learningPath;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public String getNivelDificultad() {
		return nivelDificultad;
	}
	public Date getFechaLimite() {
		return fechaLimite;
	}
	public int getDuracionEsperada() {
		return duracionEsperada;
	}
	public boolean isObligatorio() {
		return obligatorio;
	}

	//Metodos
    public abstract void realizar(ProgresoActividad progresoEstudiante);
    
    public void establecerFechaLimite(Date fecha) {
    	if (fecha!= null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            this.fechaLimite = calendar.getTime();
    	} else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            this.fechaLimite = calendar.getTime();
    	}
        
    }
    
    public void RecordatorioActividad(ProgresoActividad progreso) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaLimite);
        calendar.add(Calendar.HOUR_OF_DAY, -1); // Restar 1 hora a la fecha limite

        Date fechaRecordatorio = calendar.getTime();
        
        if (new Date().after(fechaRecordatorio) && !progreso.isCompletada()) {
            System.out.println("Recordatorio: La actividad '" + descripcion + "' tiene un límite recomendado de 1 hora!");
        }
    }
    
    public void agregarActividadSeguimiento(Actividad actividad) {
        actividadesSeguimiento.add(actividad);
    }

    public void agregarPrerrequisito(Actividad actividad) {
        prerrequisitos.add(actividad);
    }
    
    public void recomendarActividad(ProgresoActividad p_ultima, Actividad anterior) {
        if (p_ultima.isCompletada()) {
            if ("Aprobada".equals(p_ultima.getResultado())) {
                if (!actividadesSeguimiento.isEmpty()) {
                    System.out.println("Recomendación: Realiza la siguiente actividad: " + actividadesSeguimiento.get(0).getDescripcion());
                }
            } else {
            	String act = anterior.getDescripcion(); //La ultima actividad que completó.
                System.out.println("Recomendación: Vuelve a realizar la actividad: " + act);
            }
        } else {
        	if ("Enviada".equals(p_ultima.getResultado())){
        		System.out.println("No te podemos recomendar una actividad en este momento. Por favor espera a que te den tu resultado e inténtalo nuevamente.");
        	} else {
        	System.out.println("Aún no has realizado ninguna actividad.");
        	}
        }
    }
    
    
}
