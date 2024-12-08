package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import proyecto.Estudiante;

public class PanelBotonesEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelBotonesEstudiante(JFrame ventanaPrincipal, Estudiante estudiante) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Espaciado entre botones
        setBackground(Color.LIGHT_GRAY); // Fondo claro
        setPreferredSize(new Dimension(800, 100)); // Tamaño preferido del panel

        // Crear botones

        JButton btnInscribir = crearBoton("Inscribirse a un Learning Path");
        btnInscribir.addActionListener(e -> ((VentanaPrincipalEstudiante) ventanaPrincipal).inscribirseLearningPath());
        add(btnInscribir);

        JButton btnProgreso = crearBoton("Ver progreso general");
        btnProgreso.addActionListener(e -> ((VentanaPrincipalEstudiante) ventanaPrincipal).verProgresoGeneral());
        add(btnProgreso);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 14)); // Tamaño de fuente
        boton.setPreferredSize(new Dimension(300, 40)); // Tamaño del botón
        boton.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        boton.setForeground(Color.BLACK); // Color del texto
        return boton;
    }
}

