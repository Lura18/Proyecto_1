package InterfazVentanaEliminar;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;

public class PanelTituloEliminar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelTituloEliminar(LearningPath learningPath) {
        setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Actividades de: " + learningPath.getTitulo(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        lblTitulo.setForeground(Color.BLACK);
        add(lblTitulo, BorderLayout.CENTER);
    }
}