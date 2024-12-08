package InterfazVentanaDetalles;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;
import proyecto.ProgresoPath;
import proyecto.Actividad;
import proyecto.ProgresoActividad;
import proyecto.Estudiante;

public class PanelDetallesProgreso extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelDetallesProgreso(Estudiante estudiante, LearningPath learningPath) {
        setLayout(new BorderLayout());

        // Área de texto para mostrar los detalles
        JTextArea areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Obtener el progreso del Learning Path
        ProgresoPath progreso = estudiante.getProgresoPaths().get(learningPath);
        if (progreso == null) {
            areaDetalles.setText("No se ha iniciado ningún progreso para este Learning Path.");
        } else {
            // Construir el texto del progreso
            StringBuilder detalles = new StringBuilder();
            detalles.append("- Porcentaje completado: ").append(progreso.getPorcentajePath()).append("%\n");
            detalles.append("- Tasa de éxito: ").append(progreso.getTasaExito()).append("%\n");
            detalles.append("- Tasa de fracaso: ").append(progreso.getTasaFracaso()).append("%\n");
            detalles.append(progreso.isCompletado() ? "- Estado: COMPLETADO\n" : "- Estado: EN PROGRESO\n");

            if (progreso.isCompletado()) {
                detalles.append("- Fecha de finalización: ").append(progreso.getFechaFinPath()).append("\n");
            }

            detalles.append("\nActividades realizadas:\n");
            for (Actividad actividad : progreso.getActividadesRealizadas()) {
                ProgresoActividad progresoActividad = estudiante.getProgresosAct().get(actividad);
                detalles.append("* ").append(actividad.getDescripcion()).append("\n");
                detalles.append("  - Resultado: ").append(progresoActividad.getResultado()).append("\n");
                detalles.append("  - Tiempo dedicado: ").append(progresoActividad.getTiempoDedicado()).append(" horas\n");
            }

            detalles.append("\nActividades restantes:\n");
            for (Actividad actividad : learningPath.getActividades()) {
                if (!progreso.getActividadesRealizadas().contains(actividad)) {
                    detalles.append("* ").append(actividad.getDescripcion()).append("\n");
                }
            }

            areaDetalles.setText(detalles.toString());
        }

        // Agregar el área de texto dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(areaDetalles);
        add(scrollPane, BorderLayout.CENTER);
    }
}
