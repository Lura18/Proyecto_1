package InterfazVentanaEnviosYProgreso;

import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.ProgresoPath;
import proyecto.Actividad;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Map;

public class PanelGraficoActividades extends JPanel {

    public PanelGraficoActividades(Estudiante estudiante) {
        setLayout(new GridLayout(7, 53, 2, 2)); // 52 semanas + 1 fila de días
        Map<LearningPath, ProgresoPath> progresoPaths = estudiante.getProgresoPaths();
        int[][] actividadPorDia = calcularActividadesPorDia(progresoPaths);

        // Etiquetas de días
        String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : dias) {
            JLabel labelDia = new JLabel(dia, SwingConstants.CENTER);
            add(labelDia);
        }

        // Crear celdas para cada día del año
        for (int semana = 0; semana < 52; semana++) {
            for (int dia = 0; dia < 7; dia++) {
                int actividades = actividadPorDia[semana][dia];
                JPanel celda = new JPanel();
                celda.setBackground(colorActividad(actividades));
                add(celda);
            }
        }
    }

    private int[][] calcularActividadesPorDia(Map<LearningPath, ProgresoPath> progresoPaths) {
        int[][] actividadPorDia = new int[52][7]; // 52 semanas, 7 días

        for (ProgresoPath progreso : progresoPaths.values()) {
            for (Actividad actividad : progreso.getActividadesRealizadas()) {
                Date fechaFin = progreso.getEstudiante().getProgresosAct().get(actividad).getFechaFin();
                if (fechaFin != null) {
                    LocalDate fecha = fechaFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    int semana = fecha.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1; // Semanas 0-51
                    int dia = fecha.getDayOfWeek().getValue() - 1; // Días 0 (lunes) a 6 (domingo)
                    actividadPorDia[semana][dia]++;
                }
            }
        }

        return actividadPorDia;
    }

    private Color colorActividad(int actividades) {
        if (actividades == 0) return Color.LIGHT_GRAY;
        else if (actividades <= 2) return new Color(173, 216, 230); // Azul claro
        else if (actividades <= 5) return new Color(100, 149, 237); // Azul medio
        else return new Color(0, 0, 139); // Azul oscuro
    }
}
