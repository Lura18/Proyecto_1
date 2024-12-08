package InterfazVentanaEnviosYProgreso;

import javax.swing.*;
import java.awt.*;

public class PanelTitulo extends JPanel {
    public PanelTitulo(String texto) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo);
    }
}
