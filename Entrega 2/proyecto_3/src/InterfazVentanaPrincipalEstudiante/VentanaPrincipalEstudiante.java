package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;

import InterfazVentanaAutenticacion.VentanaAutenticacion;

import java.awt.*;
import proyecto.Estudiante;
import proyecto.Registro;

public class VentanaPrincipalEstudiante extends JFrame {
    private static final long serialVersionUID = 1L;

    private Estudiante estudiante;

    public VentanaPrincipalEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;

        // Configuración de la ventana
        setTitle("Ventana Principal - Estudiante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Titulo bienvenida
        PanelBienvenidaEstudiante panelBienvenida = new PanelBienvenidaEstudiante(estudiante);
        add(panelBienvenida, BorderLayout.NORTH);
        
        // Panel de logo
        JPanel panelLogo = new PanelLogoEstudiante();
        add(panelLogo, BorderLayout.EAST);

        // Panel central con los Learning Paths
        JPanel panelLearningPaths = new PanelLearningPathsEstudiante(estudiante);
        add(panelLearningPaths, BorderLayout.WEST);

        // Panel de botones de navegación
        JPanel panelBotones = new PanelBotonesEstudiante(this, estudiante);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void verRecomendaciones() {
        PanelLearningPathsEstudiante panelLearningPaths = (PanelLearningPathsEstudiante) getContentPane().getComponent(1);
        JTable tabla = panelLearningPaths.getTablaLearningPaths();

        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            String titulo = (String) tabla.getValueAt(filaSeleccionada, 0);
            JOptionPane.showMessageDialog(this, "Mostrando recomendaciones para: " + titulo);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un Learning Path.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void inscribirseLearningPath() {
        JOptionPane.showMessageDialog(this, "Abrir ventana para inscribirse a un Learning Path.");
    }

    public void verProgresoGeneral() {
        JOptionPane.showMessageDialog(this, "Abrir ventana para ver progreso general.");
    }

    public void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose(); // Cerrar la ventana actual
            new VentanaAutenticacion(new Registro()).setVisible(true);
        }
    }
}
