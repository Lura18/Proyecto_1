package InterfazVentanaEditar;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;

public class PanelTituloEditar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelTituloEditar(LearningPath learningPath) {
        setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Editando Learning Path: " + learningPath.getTitulo(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        lblTitulo.setForeground(Color.BLACK);
        add(lblTitulo, BorderLayout.CENTER);
    }
}
