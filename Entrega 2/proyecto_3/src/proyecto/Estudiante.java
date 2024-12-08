package proyecto;

import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collection;
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
    private boolean actividadEnProgreso;

    // Constructor
    public Estudiante(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena);
        this.learningPathsInscritos = new ArrayList<>();
        this.progresosAct = new HashMap<>();
        this.progresoPaths = new HashMap<>();
        this.actividadEnProgreso = false;
        this.realizadas = new ArrayList<>();
    }

    // Getters
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

            // Crear y asociar ProgresoPath
            ProgresoPath avance = new ProgresoPath(learningPath, new Date(), this);
            progresoPaths.put(learningPath, avance);

            // Crear progreso para cada actividad del Learning Path
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

    public LearningPath inscribirseEnLearningPath(Scanner scanner, List<LearningPath> allLearningPaths) {
        List<LearningPath> disponibles = getLearningPathsDisponibles(allLearningPaths);

        if (disponibles.isEmpty()) {
            System.out.println("No hay Learning Paths disponibles para inscripción en este momento.");
            return null;
        }

        System.out.println("Learning Paths disponibles para inscripción:");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i).getTitulo());
        }

        while (true) {
            try {
                System.out.print("Seleccione el número del Learning Path para inscribirse: ");
                int seleccion = Integer.parseInt(scanner.nextLine());

                if (seleccion < 1 || seleccion > disponibles.size()) {
                    System.out.println("Selección inválida. Por favor, intente de nuevo.");
                } else {
                    LearningPath seleccionado = disponibles.get(seleccion - 1);
                    inscribirLearningPath(seleccionado);
                    return seleccionado;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }
        
    public Actividad seleccionarActividad(Scanner scanner, LearningPath learningPath) {
        if (!learningPathsInscritos.contains(learningPath)) {
            System.out.println("No estás inscrito en este Learning Path.");
            return null;
        }

        List<Actividad> actividades = learningPath.getActividades();
        List<Actividad> pendientes = new ArrayList<>();
        for (Actividad actividad : actividades) {
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

        if (faltanPrerrequisitos(actividad)) {
            System.out.println("Faltan prerrequisitos para esta actividad. Completa las actividades necesarias primero.");
            return;
        }

        progreso.setFechaInicio(new Date());
        actividadEnProgreso = true;
        System.out.println("Has iniciado la actividad: " + actividad.getDescripcion());
    }

    public boolean faltanPrerrequisitos(Actividad actividad) {
        for (Actividad prerequisito : actividad.getPrerrequisitos()) {
            ProgresoActividad progreso = progresosAct.get(prerequisito);
            if (progreso == null || !progreso.isCompletada()) {
                return true;
            }
        }
        return false;
    }

    public void realizarActividad(Actividad actividad) {
        if (actividad != null) {
            ProgresoActividad progreso = progresosAct.get(actividad);
            if (!progreso.isCompletada()) {
                actividad.realizar(progreso);
                realizadas.add(actividad);

                ProgresoPath path = progresoPaths.get(actividad.getLearningPath());
                path.agregarActividadRealizada(actividad);
                path.marcarCompletado();

                this.actividadEnProgreso = false;
            } else {
                System.out.println("No puedes realizar una actividad sin antes comenzarla.");
            }
        } else {
            System.out.println("No se encontró la actividad o ya ha sido completada.");
        }
    }

    public void mostrarProgreso() {
        System.out.println("Progreso de tus Learning Paths:");

        // Iterar sobre los Learning Paths en los que está inscrito el estudiante
        for (LearningPath lp : learningPathsInscritos) {
            System.out.println("Learning Path: " + lp.getTitulo());

            // Obtener el progreso asociado al Learning Path
            ProgresoPath progresoPath = progresoPaths.get(lp);
            if (progresoPath != null) {
                // Calcular y actualizar estadísticas de progreso
                progresoPath.calcularProgreso();
                progresoPath.actualizarTasas();

                // Mostrar detalles del progreso
                System.out.println("  - Porcentaje completado: " + progresoPath.getPorcentajePath() + "%");
                System.out.println("  - Tasa de éxito: " + progresoPath.getTasaExito() + "%");
                System.out.println("  - Tasa de fracaso: " + progresoPath.getTasaFracaso() + "%");
                System.out.println(progresoPath.isCompletado() ? "  - Estado: COMPLETADO" : "  - Estado: EN PROGRESO");
            } else {
                System.out.println("  - No se ha iniciado el progreso para este Learning Path.");
            }
        }
    }


    public void pedirRecomendacionActividad(LearningPath lp) {
        System.out.println("Recomendación para el Learning Path: " + lp.getTitulo());
    }

    public void pedirProgresoPath(LearningPath lpSeleccionado) {
        // Verificar si el estudiante está inscrito en el Learning Path
        if (!learningPathsInscritos.contains(lpSeleccionado)) {
            System.out.println("No estás inscrito en este Learning Path.");
            return;
        }

        // Obtener el progreso del Learning Path
        ProgresoPath progreso = progresoPaths.get(lpSeleccionado);
        if (progreso == null) {
            System.out.println("No se ha iniciado ningún progreso para este Learning Path.");
            return;
        }

        // Calcular y actualizar estadísticas del progreso
        progreso.calcularProgreso();
        progreso.actualizarTasas();

        // Mostrar información del progreso
        System.out.println("Progreso en el Learning Path: " + lpSeleccionado.getTitulo());
        System.out.println("- Porcentaje completado: " + progreso.getPorcentajePath() + "%");
        System.out.println("- Tasa de éxito: " + progreso.getTasaExito() + "%");
        System.out.println("- Tasa de fracaso: " + progreso.getTasaFracaso() + "%");

        if (progreso.isCompletado()) {
            System.out.println("- Estado: COMPLETADO");
            System.out.println("- Fecha de finalización: " + progreso.getFechaFinPath());
        } else {
            System.out.println("- Estado: EN PROGRESO");
        }

        // Mostrar actividades realizadas y su estado
        System.out.println("\nActividades realizadas:");
        for (Actividad actividad : progreso.getActividadesRealizadas()) {
            ProgresoActividad progresoActividad = progresosAct.get(actividad);
            System.out.println("* " + actividad.getDescripcion());
            System.out.println("  - Resultado: " + progresoActividad.getResultado());
            System.out.println("  - Tiempo dedicado: " + progresoActividad.getTiempoDedicado() + " horas");
        }

        System.out.println("\nActividades restantes:");
        for (Actividad actividad : lpSeleccionado.getActividades()) {
            if (!progreso.getActividadesRealizadas().contains(actividad)) {
                System.out.println("* " + actividad.getDescripcion());
            }
        }
    }

}


