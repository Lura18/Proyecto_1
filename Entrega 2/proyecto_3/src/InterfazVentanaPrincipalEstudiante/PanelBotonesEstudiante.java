package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;

import InterfazVentanaDetalles.VentanaDetallesProgreso;
import InterfazVentanaEnviosYProgreso.VentanaProgresoYEnvios;
import InterfazVentanaInscripcion.VentanaInscripcion;

import java.awt.*;
import java.util.List;

import proyecto.Estudiante;
import proyecto.LearningPath;

public class PanelBotonesEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelBotonesEstudiante(JFrame ventanaPrincipal, Estudiante estudiante, List<LearningPath> paths) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Espaciado entre botones
        setBackground(Color.LIGHT_GRAY); // Fondo claro
        setPreferredSize(new Dimension(800, 100)); // Tamaño preferido del panel

        // Crear botones

        JButton btnDetalles = crearBoton("Ver detalles");
        btnDetalles.addActionListener(e -> verDetalles(ventanaPrincipal, estudiante));
        add(btnDetalles);
        
        JButton btnInscribir = crearBoton("Inscribirse a un Learning Path");
        btnInscribir.addActionListener(e -> {
            VentanaInscripcion ventanaInscripcion = new VentanaInscripcion(estudiante, paths, ventanaPrincipal);
            ventanaInscripcion.setVisible(true);
        });
        add(btnInscribir);

        JButton btnProgreso = crearBoton("Ver progreso general");
        btnProgreso.addActionListener(e -> verProgreso(ventanaPrincipal, estudiante));
        add(btnProgreso);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 14)); // Tamaño de fuente
        boton.setPreferredSize(new Dimension(235, 40)); // Tamaño del botón
        boton.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        boton.setForeground(Color.BLACK); // Color del texto
        return boton;
    }
    
    private void verProgreso(JFrame ventanaPrincipal, Estudiante estudiante) {
        // Abrir la ventana de progreso y envíos
    	
        VentanaProgresoYEnvios ventanaProgreso = new VentanaProgresoYEnvios(estudiante);
        ventanaProgreso.setVisible(true);
    }
    
    private void verDetalles(JFrame ventanaPrincipal, Estudiante estudiante) {
        PanelLearningPathsEstudiante panelLearningPaths = (PanelLearningPathsEstudiante) ((VentanaPrincipalEstudiante) ventanaPrincipal).getPanelLearningPaths();
        LearningPath lpSeleccionado = panelLearningPaths.getLearningPathSeleccionado(estudiante);

        if (lpSeleccionado != null) {
            // Abrir la ventana con los detalles del progreso
            VentanaDetallesProgreso ventanaDetalles = new VentanaDetallesProgreso(estudiante, lpSeleccionado);
            ventanaDetalles.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Learning Path para ver los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

