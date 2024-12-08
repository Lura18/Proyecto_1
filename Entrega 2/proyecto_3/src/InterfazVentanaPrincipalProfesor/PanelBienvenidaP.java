package InterfazVentanaPrincipalProfesor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.ProgresoPath;
import proyecto.Usuario;

public class PanelBienvenidaP extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel mensajeBienvenida;

    public PanelBienvenidaP(Usuario usuario) {
        setLayout(new BorderLayout());

        // Crear el mensaje de bienvenida
        mensajeBienvenida = new JLabel("Bienvenido/a, " + usuario.getNombre() + "!", SwingConstants.CENTER);
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(mensajeBienvenida, BorderLayout.CENTER);

        // Opcional: agregar un resumen del progreso si es un estudiante
        if (usuario instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuario;
            Map<LearningPath, ProgresoPath> p = estudiante.getProgresoPaths();
            JLabel progreso = new JLabel("Progreso general: " + "%", SwingConstants.CENTER);
            progreso.setFont(new Font("Arial", Font.PLAIN, 14));
            add(progreso, BorderLayout.SOUTH);
        }
    }
}