package InterfazVentanaEnviosYProgreso;

import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.ProgresoPath;

import javax.swing.*;
import java.util.List;

public class PanelTablaProgreso extends JScrollPane {
    public PanelTablaProgreso(Estudiante estudiante) {
        String[] columnas = {"Learning Path", "Progreso (%)", "Tasa de Éxito (%)", "Tasa de Fracaso (%)", "Estado"};
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

        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        setViewportView(tabla);
    }
}
