package proyecto;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Estudiante extends Usuario {

    // Atributos
    private List<LearningPath> learningPathsInscritos;
    private Map<Actividad, ProgresoActividad> progresosAct;
    private Map<LearningPath, ProgresoPath> progresoPaths;
    private List<Actividad> realizadas;
    // Lista de actividades realizadas
    private boolean actividadEnProgreso;
    


    // Constructor
    public Estudiante(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena);
        this.learningPathsInscritos = new ArrayList<>();
        this.progresosAct = new HashMap<>();
        this.progresoPaths = new HashMap<>();
        this.realizadas = new ArrayList<>();
        this.actividadEnProgreso = false;
    }

    // Getters y Setters
    public List<LearningPath> getLearningPathsInscritos() {
        return learningPathsInscritos;
    }

    public List<LearningPath> getLearningPathsDisponibles(List<LearningPath> allLearningPaths) {
        List<LearningPath> disponibles = new ArrayList<>();
        for (LearningPath lp : allLearningPaths) {
            if (!learningPathsInscritos.contains(lp)) {
                disponibles.add(lp);
            }
        }
        return disponibles;
    }

    public Map<Actividad, ProgresoActividad> getProgresosAct() {
        return progresosAct;
    }

    public void setProgresosAct(Map<Actividad, ProgresoActividad> progresos) {
        this.progresosAct = progresos;
    }

    public Map<LearningPath, ProgresoPath> getProgresoPaths() {
        return progresoPaths;
    }

    public void setProgresoPaths(Map<LearningPath, ProgresoPath> progresoPaths) {
        this.progresoPaths = progresoPaths;
    }

    public List<Actividad> getRealizadas() {
        return realizadas;
    }

    public void agregarActividadRealizada(Actividad actividad) {
        if (!realizadas.contains(actividad)) {
            realizadas.add(actividad);
        }
    }

    // Métodos
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

    @Override
    public void darReseñaActividad(Actividad actividad, String texto, float rating) {
        ProgresoActividad prog = progresosAct.get(actividad);

        if (prog != null && prog.isCompletada()) {
            Reseña reseña = new Reseña(texto, rating);
            reseña.hacerReseña(actividad);
            System.out.println("Reseña añadida correctamente: " + texto + " con calificación de " + rating);
        } else {
            System.out.println("Debes completar la actividad antes de poder dar una reseña.");
        }
    }

    public LearningPath inscribirLearningPath(LearningPath learningPath) {
        if (!learningPathsInscritos.contains(learningPath)) {
            learningPathsInscritos.add(learningPath);
            System.out.println("Te has inscrito exitosamente en el Learning Path: " + learningPath.getTitulo());

            ProgresoPath avance = new ProgresoPath(learningPath, new Date(), this);
            progresoPaths.put(learningPath, avance);

            for (Actividad actividad : learningPath.getActividades()) {
                ProgresoActividad progreso = new ProgresoActividad(actividad, this);
                progresosAct.put(actividad, progreso);
            }
            return learningPath;
        } else {
            System.out.println("Ya estás inscrito en este Learning Path.");
            return null;
        }
    }

    public Actividad seleccionarActividad(Scanner scanner, LearningPath learningPath) {
        if (!learningPathsInscritos.contains(learningPath)) {
            System.out.println("No estás inscrito en este Learning Path.");
            return null;
        }

        List<Actividad> pendientes = new ArrayList<>();
        for (Actividad actividad : learningPath.getActividades()) {
            ProgresoActividad progreso = progresosAct.get(actividad);
            if (progreso != null && !progreso.isCompletada()) {
                pendientes.add(actividad);
            }
        }

        if (pendientes.isEmpty()) {
            System.out.println("No hay actividades pendientes en este Learning Path.");
            return null;
        }

        System.out.println("Actividades pendientes en el Learning Path: " + learningPath.getTitulo());
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).getDescripcion());
        }

        while (true) {
            try {
                System.out.print("Seleccione el número de la actividad para iniciar: ");
                int seleccion = Integer.parseInt(scanner.nextLine());
                if (seleccion < 1 || seleccion > pendientes.size()) {
                    System.out.println("Selección inválida. Intente de nuevo.");
                } else {
                    Actividad seleccionada = pendientes.get(seleccion - 1);
                    iniciarActividad(seleccionada);
                    return seleccionada;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }

    public void iniciarActividad(Actividad actividad) {
        if (actividadEnProgreso) {
            System.out.println("Ya tienes una actividad en progreso. Completa la actual antes de iniciar otra.");
            return;
        }

        ProgresoActividad progreso = progresosAct.get(actividad);
        if (progreso == null || progreso.isCompletada()) {
            System.out.println("Esta actividad ya ha sido completada o no está disponible.");
            return;
        }

        progreso.setFechaInicio(new Date());
        actividadEnProgreso = true;
        System.out.println("Has iniciado la actividad: " + actividad.getDescripcion());
    }

    public void realizarActividad(Actividad actividad) {
        if (actividad != null) {
            ProgresoActividad progreso = progresosAct.get(actividad);
            if (progreso != null && !progreso.isCompletada()) {
                progreso.setFechaFin(new Date());
                progreso.isCompletada();
                agregarActividadRealizada(actividad);

                ProgresoPath path = progresoPaths.get(actividad.getLearningPath());
                if (path != null) {
                    path.agregarActividadRealizada(actividad);
                }

                actividadEnProgreso = false;
                System.out.println("Actividad completada: " + actividad.getDescripcion());
            } else {
                System.out.println("Esta actividad ya ha sido completada o no está disponible.");
            }
        } else {
            System.out.println("La actividad seleccionada no es válida.");
        }
    }
    
    public void pedirRecomendacionActividad(LearningPath lpSeleccionado) {
        if (!learningPathsInscritos.contains(lpSeleccionado)) {
            System.out.println("No estás inscrito en este Learning Path.");
            return;
        }

        List<Actividad> actividadesPendientes = new ArrayList<>();
        for (Actividad actividad : lpSeleccionado.getActividades()) {
            ProgresoActividad progreso = progresosAct.get(actividad);
            if (progreso != null && !progreso.isCompletada()) {
                actividadesPendientes.add(actividad);
            }
        }

        if (actividadesPendientes.isEmpty()) {
            System.out.println("No hay actividades pendientes para recomendar en este Learning Path.");
        } else {
            Actividad recomendada = actividadesPendientes.get(0); // Simplemente se toma la primera pendiente como recomendación
            System.out.println("Actividad recomendada: " + recomendada.getDescripcion());
        }
    }

    public void pedirProgresoPath(LearningPath lpSeleccionado) {
        if (!learningPathsInscritos.contains(lpSeleccionado)) {
            System.out.println("No estás inscrito en este Learning Path.");
            return;
        }

        ProgresoPath progresoPath = progresoPaths.get(lpSeleccionado);
        if (progresoPath == null) {
            System.out.println("No hay progreso registrado para este Learning Path.");
            return;
        }

        progresoPath.calcularProgreso();
        System.out.println("Progreso en el Learning Path: " + lpSeleccionado.getTitulo());
        System.out.println("  - Porcentaje completado: " + progresoPath.getPorcentajePath() + "%");
        System.out.println("  - Estado: " + (progresoPath.isCompletado() ? "COMPLETADO" : "EN PROGRESO"));
    }
    public void setActividadesRealizadas(List<Actividad> actividadesRealizadas) {
        this.realizadas = new ArrayList<>(actividadesRealizadas);
    }



    public void mostrarProgreso() {
        System.out.println("Progreso de tus Learning Paths:");
        for (LearningPath lp : learningPathsInscritos) {
            System.out.println("Learning Path: " + lp.getTitulo());

            ProgresoPath progresoPath = progresoPaths.get(lp);
            if (progresoPath != null) {
                progresoPath.calcularProgreso();
                System.out.println("  - Porcentaje completado: " + progresoPath.getPorcentajePath() + "%");
            } else {
                System.out.println("  - Sin progreso registrado.");
            }
        }
    }
}


