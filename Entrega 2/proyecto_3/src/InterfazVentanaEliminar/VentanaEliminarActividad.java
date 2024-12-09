package InterfazVentanaEliminar;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;

public class VentanaEliminarActividad extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaEliminarActividad(Profesor profesor, LearningPath learningPath) {
        // Configuración básica de la ventana
        setTitle("Eliminar Actividad");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título de la ventana
        JLabel lblTitulo = new JLabel("Actividades de: " + learningPath.getTitulo(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        lblTitulo.setForeground(Color.BLACK);
        add(lblTitulo, BorderLayout.NORTH);

        // Lista de actividades
        List<Actividad> actividades = learningPath.getActividades();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Actividad actividad : actividades) {
            model.addElement(actividad.getDescripcion());
        }

        JList<String> listaActividades = new JList<>(model);
        listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaActividades);
        add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        // Acción del botón "Eliminar"
        btnEliminar.addActionListener(e -> {
            int seleccion = listaActividades.getSelectedIndex();
            if (seleccion != -1) {
                String actividadSeleccionada = listaActividades.getSelectedValue();
                int confirmacion = JOptionPane.showConfirmDialog(
                    VentanaEliminarActividad.this,
                    "¿Estás seguro de que deseas eliminar la actividad: \"" + actividadSeleccionada + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Eliminar la actividad del Learning Path
                    Actividad actividadAEliminar = actividades.get(seleccion);
                    profesor.eliminarActividadDeLearningPath(learningPath, actividadAEliminar);
                    model.remove(seleccion); // Remover de la lista visual
                    JOptionPane.showMessageDialog(
                        VentanaEliminarActividad.this,
                        "La actividad ha sido eliminada exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    VentanaEliminarActividad.this,
                    "Por favor, selecciona una actividad para eliminar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Acción del botón "Cancelar"
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
