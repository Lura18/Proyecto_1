package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import InterfazVentanaAutenticacion.VentanaAutenticacion;
import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;

public class PanelBienvenidaEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<Actividad> actividades;
    private List<LearningPath> paths;
    
    public PanelBienvenidaEstudiante(Estudiante estudiante, List<Actividad> actividades , List<LearningPath> paths) {
        this.actividades = actividades;
        this.paths = paths;
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY); // Fondo claro

        // Panel superior para centrar el mensaje
        JPanel panelMensaje = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMensaje.setBackground(Color.LIGHT_GRAY); // Coincidir fondo
        JLabel mensajeBienvenida = new JLabel("¡Bienvenido, " + estudiante.getNombre() + "!");
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 30));
        panelMensaje.add(mensajeBienvenida);

        add(panelMensaje, BorderLayout.CENTER);

        // Panel derecho para el icono y botón
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelDerecho.setBackground(Color.LIGHT_GRAY); // Coincidir fondo

        // Ícono del usuario
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/user.png"));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
            JLabel labIcono = new JLabel(iconoEscalado);
            panelDerecho.add(labIcono);
        } catch (NullPointerException e) {
            JLabel errorLabel = new JLabel("Icono no disponible", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            panelDerecho.add(errorLabel);
        }

        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnCerrarSesion.setPreferredSize(new Dimension(150, 40));
        btnCerrarSesion.setBackground(new Color(173, 216, 230));
        panelDerecho.add(btnCerrarSesion);

        add(panelDerecho, BorderLayout.EAST);
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            new VentanaAutenticacion(new proyecto.Registro(), actividades, paths).setVisible(true);
        }
    }
}
