package proyecto;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

class Estudiante extends Usuario {
	

	//Atributos
    private List<LearningPath> learningPathsInscritos;
	private List<ProgresoActividad> progresosAct;
	private List<ProgresoPath> progresoPaths;
	
	//Constructor
	public Estudiante(String nombre, String correo, String contrasena) {
		super(nombre, correo, contrasena);
		this.learningPathsInscritos = new ArrayList<>();
		this.progresosAct = new ArrayList<>();
		this.progresoPaths = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	//Get and set
	public List<ProgresoActividad> getProgresosAct() {
		return progresosAct;
	}
	
	public List<ProgresoPath> getProgresoPaths() {
		return progresoPaths;
	}
	
	public List<LearningPath> getLearningPathsInscritos() {
		return learningPathsInscritos;
	}

	//Metodos
    public void inscribirseEnLearningPath(LearningPath learningPath) {
        if (!learningPathsInscritos.contains(learningPath)) {
            learningPathsInscritos.add(learningPath);
            System.out.println("Te has inscrito exitosamente en el Learning Path: " + learningPath.getTitulo());
            System.out.println("Esta es la estructura del Learning Path");
			learningPath.mostrarEstructura();
			
            for (Actividad actividad : learningPath.getActividades()) {
                ProgresoActividad progreso = new ProgresoActividad(actividad);
                progresosAct.add(progreso);
            }
            
        } else {
            System.out.println("Ya estás inscrito en este Learning Path.");
        }
    }
    
    public void iniciarActividad(Actividad actividad, LearningPath lp) {
    	
    	boolean esta = false;
    	Actividad previa = null;
    	Date fecha = null;
    	
    	for(ProgresoPath path: progresoPaths) {
    		if (path.getLp().equals(lp)) {
    			List<Actividad> lst = path.getActividadesRealizadas();
    			if (lst.size()>0) {
        			previa = path.getActividadesRealizadas().get(lst.size() - 1);
    			}
    		}
    	}
    	
        for (ProgresoActividad progreso : progresosAct) {
        	
        	if (previa != null) {
        		if (progreso.getActividad().equals(previa) && !progreso.isCompletada()) {
                	fecha = progreso.getFechaFin();
        	}
            }
        	
            if (progreso.getActividad().equals(actividad) && !progreso.isCompletada()) {
                System.out.println("Iniciando actividad: " + actividad.getDescripcion());
                progreso.setFechaInicio(new Date());
                esta =  true;
                actividad.establecerFechaLimite(fecha);
            }
        }
        if (!esta) {
            System.out.println("Ya has completado esta actividad o no está disponible.");
        }
    }
    
    
    public void realizarActividad(Actividad actividad) {
        for (ProgresoActividad progreso : progresosAct) {
            if (progreso.getActividad().equals(actividad)) {
            	actividad.realizar(progreso);
            	return ;
            }
        }
        System.out.println("No se encontró la actividad o ya ha sido completada.");
    } 
    public void pedirRecomendacionActividad(ProgresoPath progreso, LearningPath lp) {
    	List<Actividad> lst = null;
    	ProgresoActividad p1 = null;
    	
    	for (ProgresoPath p : progresoPaths) {
            if (progreso.getLp().equals(lp)) {
            	lst = p.getActividadesRealizadas();
            }
    	}
    	if (lst != null && lst.size()>0) {
    		Actividad ultima = lst.getLast();
    		Actividad anterior = lst.get(lst.size() -2); //revisar index
    		for (ProgresoActividad q : progresosAct) {
    			if (q.getActividad().equals(ultima)) {
    				p1 = q;
    			}
        	}
    		ultima.recomendarActividad(p1,anterior);
    	}
    	
    
    }
    
	@Override
    public void verLearningPaths() {
        System.out.println("Learning Paths en los que estás inscrito:");
        for (LearningPath lp : learningPathsInscritos) {
            System.out.println("- " + lp.getTitulo());
        }
    }

}
