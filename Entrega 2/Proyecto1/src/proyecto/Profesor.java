package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
    
    public Actividad crearActividad(Scanner scanner) {
    	System.out.println("¿Qué tipo de actividad quiere crear? Seleccione el número\n1. Recurso educativo\n2. Encuesta\n3. Tarea\n4. Quiz\n5. Examen");
    	boolean opcionValida = false;
    	while (opcionValida) {
	    	try {
	        	int tipo = Integer.parseInt(scanner.nextLine());
	            if (tipo < 1 || tipo > 5) {
	            	System.out.println("Selección no válida. Por favor, intente nuevamente.");
	            } else if (tipo == 1) {
	            	opcionValida = true;
	            	return crearRecurso(scanner);
	            } else if (tipo == 2) {
	            	opcionValida = true;
	            	return crearEncuesta(scanner);
	            } else if (tipo == 3) {
	            	opcionValida = true;
	            	return crearTarea(scanner);
	            } else if (tipo == 4) {
	            	opcionValida = true;
	            	return crearQuiz(scanner);
	            } else {
	            	opcionValida = true;
	            	return crearExamen(scanner);
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Por favor, ingrese un número.");
	        }
    	}
    	return null;
    }
    
    public RecursoEducativo crearRecurso(Scanner scanner) {
    	return null;
    }
    
    public Encuesta crearEncuesta(Scanner scanner) {
    	return null;
    }
    
    public Tarea crearTarea(Scanner scanner) {
    	return null;
    }
    
    public Quiz crearQuiz(Scanner scanner) {
    	return null;
    }
    
    public Examen crearExamen(Scanner scanner) {
    	return null;
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

	//Calificar
	public void calificarActividad(Actividad actividad, Scanner scanner) {
		if (actividad.getCreador().equals(this)){
			Set<Estudiante> estudiantes = actividad.getRespuesta().keySet();
			if (estudiantes != null) {
				for (Estudiante estudiante: estudiantes) {
					HashMap<Actividad, ProgresoActividad> mapa = estudiante.getProgresosAct();
					ProgresoActividad progreso = mapa.get(actividad);
					if (progreso.getResultado().equals("Enviada")) {
						String rta = actividad.getRespuesta().get(estudiante);
						if (actividad.getTipo().equals("Tarea")) {
							System.out.println("El estudiante "+ estudiante.nombre + " mando la tarea por el medio: \n" + rta);
						} else {
							System.out.println("La respuesta del estudiante "+ estudiante.nombre + " es: \n" + rta);
						}
			            System.out.print("¿Cómo desea calificarla?\n1. Aprobada\n2.Reprobada ");
			            String resultado = scanner.nextLine().toLowerCase();
			            if (resultado.equals("1")) {
							progreso.setResultado("Aprobada");
							System.out.println("Se ha calificado la actividad "+ actividad.descripcion+ " del estudiante " + estudiante.nombre + ". Su resultado es Aprobada.");
			            } else {
			            	progreso.setResultado("Reprobada");
							System.out.println("Se ha calificado la actividad "+ actividad.descripcion+ " del estudiante " + estudiante.nombre + ". Su resultado es Reprobada.");
			            }
						
					}
				}
			} else {
				System.out.println("No hay actividades por calificar.");
			}
		} else {
			System.out.println("No tiene los permisos para calificar esta actividad.");
		}
	}
	

}
