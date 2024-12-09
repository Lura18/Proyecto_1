package InterfazVentanaDetalles;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;
import proyecto.Estudiante;

public class VentanaDetallesProgreso extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaDetallesProgreso(Estudiante estudiante, LearningPath learningPath) {
        setTitle("Detalles del Progreso");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Agregar paneles
        add(new PanelTituloProgreso("Información Learning Path: " + learningPath.getTitulo()), BorderLayout.NORTH);
        add(new PanelDetallesProgreso(estudiante, learningPath), BorderLayout.CENTER);
        add(new PanelCerrarProgreso(this), BorderLayout.SOUTH);
    }
}