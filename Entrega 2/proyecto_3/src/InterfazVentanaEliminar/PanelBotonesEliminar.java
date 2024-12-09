package InterfazVentanaEliminar;

import javax.swing.*;
import java.awt.*;
import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;

public class PanelBotonesEliminar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelBotonesEliminar(JFrame ventana, Profesor profesor, LearningPath learningPath, PanelListaActividades panelLista) {
        setLayout(new FlowLayout());

        // Botón "Eliminar"
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> {
            int seleccion = panelLista.getActividadSeleccionadaIndex();
            if (seleccion != -1) {
                String actividadSeleccionada = panelLista.getActividadSeleccionada();
                int confirmacion = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Estás seguro de que deseas eliminar la actividad: \"" + actividadSeleccionada + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Eliminar la actividad del Learning Path
                    Actividad actividadAEliminar = learningPath.getActividades().get(seleccion);
                    profesor.eliminarActividadDeLearningPath(learningPath, actividadAEliminar);
                    panelLista.eliminarActividadDeLista(seleccion); // Remover de la lista visual
                    JOptionPane.showMessageDialog(
                        ventana,
                        "La actividad ha sido eliminada exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    ventana,
                    "Por favor, selecciona una actividad para eliminar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botón "Cancelar"
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> ventana.dispose());

        add(btnEliminar);
        add(btnCancelar);
    }
}
