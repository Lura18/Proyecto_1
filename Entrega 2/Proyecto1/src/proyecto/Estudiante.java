package proyecto;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estudiante extends Usuario {
	

	//Atributos
    private List<LearningPath> learningPathsInscritos;
	private List<ProgresoActividad> progresosAct;
	private List<ProgresoPath> progresoPaths;
	private boolean actividadEnProgreso;
	//Constructor
	public Estudiante(String nombre, String correo, String contrasena) {
		super(nombre, correo, contrasena);
		this.learningPathsInscritos = new ArrayList<>();
		this.progresosAct = new ArrayList<>();
		this.progresoPaths = new ArrayList<>();
		this.actividadEnProgreso = false;
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
	
	@Override
	public String getTipoUsuario() {
		return "Estudiante";
	}
	
	@Override
    public void verLearningPaths() {
        System.out.println("Learning Paths en los que estás inscrito:");
        for (LearningPath lp : learningPathsInscritos) {
            System.out.println("- " + lp.getTitulo());
        }
    }
	
	public void darReseñaActividad(Actividad actividad, String texto, float rating) {
		if (rating < 0 || rating > 10) {
	        System.out.println("El rating debe estar entre 0 y 10.");
	    }
	    Reseña reseña = new Reseña(texto, rating);
	    actividad.agregarReseña(reseña);
	    System.out.println("Reseña agregada con éxito. Gracias por ayudarnos a mejorar!");
		
	}

	
    public void inscribirseEnLearningPath(LearningPath learningPath) {
        if (!learningPathsInscritos.contains(learningPath)) {
            learningPathsInscritos.add(learningPath);
            System.out.println("Te has inscrito exitosamente en el Learning Path: " + learningPath.getTitulo());
            System.out.println("Esta es la estructura del Learning Path");
			learningPath.mostrarEstructura();
			ProgresoPath avance = new ProgresoPath(learningPath, new Date());
			progresoPaths.add(avance);
            for (Actividad actividad : learningPath.getActividades()) {
                ProgresoActividad progreso = new ProgresoActividad(actividad);
                progresosAct.add(progreso);
            }
            
        } else {
            System.out.println("Ya estás inscrito en este Learning Path.");
        }
    }
    
    public void iniciarActividad(Actividad actividad) {
    	LearningPath lp = actividad.getLearningPath();
    	if (learningPathsInscritos.contains(lp)) {
    		if (!actividadEnProgreso) {
    			boolean faltan = faltanPrerrequisitos(actividad);
            	
            	boolean esta = false;
            	Actividad previa = null;
            	Date fecha = null;
            	
            	
            	//Para obtener la anterior actividad realizada
            	for(ProgresoPath path: progresoPaths) {
            		if (path.getLp().equals(lp)) {
            			List<Actividad> lst = path.getActividadesRealizadas();
            			if (lst.size()>0) {
                			previa = path.getActividadesRealizadas().get(lst.size() - 1);
            			}
            		}
            	}
            	
                for (ProgresoActividad progreso : progresosAct) {
                	//Obtener la facha en la que se completó la ultima actividad
                	if (previa != null) {
                		if (progreso.getActividad().equals(previa) && !progreso.isCompletada()) {
                        	fecha = progreso.getFechaFin();
                		}
                    }
                    if (progreso.getActividad().equals(actividad) && !progreso.isCompletada()) {
                    	if (faltan) {
                    		List<Actividad> pre = actividad.getPrerrequisitos();
                    		System.out.println("Advertencia: Te recomendamos completar los prerrequisitos para esta actividad: " );
                    		for (Actividad act : pre) {
                    			System.out.println(act.descripcion);
                    		}
                    	}
                        System.out.println("\nIniciando actividad: " + actividad.getDescripcion());
                        progreso.setFechaInicio(new Date());
                        esta =  true;
                        actividad.establecerFechaLimite(fecha);
                    }
                }
                if (!esta) {
                    System.out.println("Ya has completado esta actividad o no está disponible.");
                }
                this.actividadEnProgreso = true;
    		} else {
    			System.out.println("No puedes iniciar otra actividad sin terminar la anterior.");
    		}
    		
    		
            
    	} else {
    		System.out.println("No puedes realizar esta actividad porque no estás inscrito en su learning path.");
    	}
    	
    }
    
    public boolean faltanPrerrequisitos(Actividad actividad) {
    	if (!actividad.getPrerrequisitos().isEmpty()) {
            for (Actividad prerrequisito : actividad.getPrerrequisitos()) {
            	for (ProgresoActividad progreso: progresosAct) {
            		if (progreso.getActividad().equals(prerrequisito) && !progreso.isCompletada()) {
            			return true;
            		}
                }
            }
        }
    	return false;
    }
    
    public void realizarActividad(Actividad actividad) {
        for (ProgresoActividad progreso : progresosAct) {
        	if (progreso.getActividad().equals(actividad) && !progreso.isCompletada()) {
                if (progreso.getFechaInicio() !=  null) {
                	actividad.realizar(progreso);
                	
                	for(ProgresoPath path: progresoPaths) {
                		if (path.getLp().equals(actividad.getLearningPath())) {
                			path.agregarActividadRealizada(actividad);
                		}
                	}
                	
                	this.actividadEnProgreso = false;
                	return ;
                } else {
                	System.out.println("No puedes realizar una actividad sin antes comenzarla.");
                	return ;
                }
        	} 
        } 
        System.out.println("No se encontró la actividad o ya ha sido completada.");
    } 
    
    public void pedirRecomendacionActividad(LearningPath lp) {
    	if (learningPathsInscritos.contains(lp)) {
    		List<Actividad> lst = null;
        	ProgresoActividad p1 = null;
    		Actividad ultima = null;
    		Actividad anterior = null;
        	
    		//Obtener actividades realizadas
        	for (ProgresoPath p : progresoPaths) {
                if (p.getLp().equals(lp)) {
                	lst = p.getActividadesRealizadas();
                }
        	}
        	
        	//Obtener ultima actividad y su progreso
        	if (lst != null && lst.size()>0) {
        		ultima = lst.get(lst.size() -1);
        		for (ProgresoActividad q : progresosAct) {
        			if (q.getActividad().equals(ultima)) {
        				p1 = q;
        			}
    			}
        	} 
        	
        	//Obtener actividad anterior 
        	if (lst.size()>1) {
        		anterior = lst.get(lst.size() -2); //revisar index
    		}
        	
        	//Casos
        	if (p1 != null && anterior != null) {
        		ultima.recomendarActividad(p1, anterior);
        	} else if (p1 != null && anterior == null) {
        		ultima.recomendarActividad(p1, ultima);
        	} else if (p1 == null && anterior == null) {
        		System.out.println("Te recomendamos empezar por la actividad: " + lp.getActividades().get(0).descripcion);
        	}
        	
    	} else {
    		System.out.println("No te encuentras inscrito en este learning path.");
    	}
    }

    

}
