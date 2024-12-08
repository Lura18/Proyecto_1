package InterfazVentanaInscripcion;

import javax.swing.*;

import InterfazVentanaPrincipalEstudiante.VentanaPrincipalEstudiante;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.Estudiante;
import proyecto.LearningPath;

import java.util.List;

public class VentanaInscripcion extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaInscripcion(Estudiante estudiante, List<LearningPath> paths, JFrame ventanaPrincipal) {
        // Configuración básica de la ventana
        setTitle("Inscripción a un Learning Path");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título de la ventana
        JLabel titulo = new JLabel("Learning Paths Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        titulo.setForeground(Color.BLACK);
        add(titulo, BorderLayout.NORTH);

        // Panel con la lista de Learning Paths
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear modelo de lista de Learning Paths
        DefaultListModel<String> model = new DefaultListModel<>();
        for (LearningPath lp : paths) {
            model.addElement(lp.getTitulo()); // Añadir el título de cada Learning Path
        }

        // Crear la lista de Learning Paths
        JList<String> listaLearningPaths = new JList<>(model);
        listaLearningPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaLearningPaths);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);

        // Botones para inscribirse y cerrar
        JPanel panelBotones = new JPanel();
        JButton btnInscribir = new JButton("Inscribirse");
        JButton btnCerrar = new JButton("Cerrar");

        // Acción del botón "Inscribirse"
        
     // Método para inscribirse al Learning Path
        btnInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPath = listaLearningPaths.getSelectedValue();
                if (selectedPath != null) {
                    // Inscribir al estudiante en el Learning Path
                    for (LearningPath lp : paths) {
                        if (lp.getTitulo().equals(selectedPath)) {
                            estudiante.inscribirLearningPath(lp);
                            JOptionPane.showMessageDialog(VentanaInscripcion.this, "Te has inscrito exitosamente en: " + lp.getTitulo(), "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                            // Llamar a la función de actualización de la tabla en el panel
                            ((VentanaPrincipalEstudiante) ventanaPrincipal).getPanelLearningPaths().actualizarTabla(estudiante);
                            dispose(); // Cerrar la ventana
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaInscripcion.this, "Por favor, selecciona un Learning Path.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón "Cerrar"
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });

        // Añadir botones al panel
        panelBotones.add(btnInscribir);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);
    }
}