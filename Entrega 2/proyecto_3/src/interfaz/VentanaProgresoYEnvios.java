package interfaz;

import proyecto.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;


public class VentanaProgresoYEnvios extends JFrame {

    private static final long serialVersionUID = 1L;

    // Componentes principales
    private JTable tablaProgreso;
    private JPanel panelGrafico;
    private JLabel titulo;

    private Estudiante estudiante;

    public VentanaProgresoYEnvios(Estudiante estudiante) {
        this.estudiante = estudiante;

        // Configuración básica de la ventana
        setTitle("Progreso y Envíos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear componentes
        titulo = new JLabel("Progreso General de Learning Paths", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        tablaProgreso = crearTablaProgreso();
        JScrollPane scrollTabla = new JScrollPane(tablaProgreso);

        panelGrafico = crearGraficoActividades();

        // Agregar componentes al JFrame
        add(titulo, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelGrafico, BorderLayout.SOUTH);
    }

    /**
     * Crea una tabla para mostrar el progreso general de los Learning Paths
     */
    private JTable crearTablaProgreso() {
        // Columnas de la tabla
        String[] columnas = {"Learning Path", "Progreso (%)", "Tasa de Éxito (%)", "Tasa de Fracaso (%)", "Estado"};

        // Datos de la tabla
        List<LearningPath> learningPaths = estudiante.getLearningPathsInscritos();
        Object[][] datos = new Object[learningPaths.size()][5];

        for (int i = 0; i < learningPaths.size(); i++) {
            LearningPath lp = learningPaths.get(i);
            ProgresoPath progreso = estudiante.getProgresoPaths().get(lp);

            if (progreso != null) {
                progreso.calcularProgreso();
                progreso.actualizarTasas();

                datos[i][0] = lp.getTitulo();
                datos[i][1] = progreso.getPorcentajePath();
                datos[i][2] = progreso.getTasaExito();
                datos[i][3] = progreso.getTasaFracaso();
                datos[i][4] = progreso.isCompletado() ? "Completado" : "En Progreso";
            } else {
                datos[i][0] = lp.getTitulo();
                datos[i][1] = "No iniciado";
                datos[i][2] = "-";
                datos[i][3] = "-";
                datos[i][4] = "No iniciado";
            }
        }

        // Crear tabla
        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        return tabla;
    }

    /**
     * Crea un gráfico estilo GitHub para mostrar las actividades realizadas
     */
    private JPanel crearGraficoActividades() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 53, 2, 2)); // 52 semanas + 1 fila de días

        // Etiquetas de los días
        String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : dias) {
            JLabel labelDia = new JLabel(dia, SwingConstants.CENTER);
            panel.add(labelDia);
        }

        // Crear el mapa de actividades realizadas por día
        Map<LearningPath, ProgresoPath> progresoPaths = estudiante.getProgresoPaths();
        int[][] actividadPorDia = calcularActividadesPorDia(progresoPaths);

        // Crear celdas para cada día del año
        for (int semana = 0; semana < 52; semana++) {
            for (int dia = 0; dia < 7; dia++) {
                int actividades = actividadPorDia[semana][dia];
                JPanel celda = new JPanel();
                celda.setBackground(colorActividad(actividades));
                panel.add(celda);
            }
        }

        return panel;
    }

    /**
     * Calcula el número de actividades realizadas por día
     */
    private int[][] calcularActividadesPorDia(Map<LearningPath, ProgresoPath> progresoPaths) {
        int[][] actividadPorDia = new int[52][7]; // 52 semanas, 7 días

        for (ProgresoPath progreso : progresoPaths.values()) {
            for (Actividad actividad : progreso.getActividadesRealizadas()) {
                Date fechaFin = progreso.getEstudiante().getProgresosAct().get(actividad).getFechaFin();
                if (fechaFin != null) {
                    // Convertir Date a LocalDate
                    LocalDate fecha = fechaFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    int semana = fecha.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1; // Semanas de 0 a 51
                    int dia = fecha.getDayOfWeek().getValue() - 1; // Días de 0 (lunes) a 6 (domingo)
                    
                    // Incrementar la actividad correspondiente
                    actividadPorDia[semana][dia]++;
                }
            }
        }

        return actividadPorDia;
    }
    /**
     * Asigna un color según la cantidad de actividades realizadas
     */
    private Color colorActividad(int actividades) {
        if (actividades == 0) return Color.LIGHT_GRAY;
        else if (actividades <= 2) return new Color(173, 216, 230); // Azul claro
        else if (actividades <= 5) return new Color(100, 149, 237); // Azul medio
        else return new Color(0, 0, 139); // Azul oscuro
    }

   
}
