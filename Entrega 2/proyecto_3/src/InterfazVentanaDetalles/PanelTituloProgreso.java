package InterfazVentanaDetalles;

import javax.swing.*;
import java.awt.*;

public class PanelTituloProgreso extends JPanel {

    public PanelTituloProgreso(String titulo) {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230)); // Azul claro
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.CENTER);
    }
}
