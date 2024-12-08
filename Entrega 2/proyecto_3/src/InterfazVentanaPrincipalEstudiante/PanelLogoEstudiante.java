package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class PanelLogoEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelLogoEstudiante() {
        setBackground(Color.WHITE); // Fondo blanco
        setLayout(new BorderLayout());

        try {
            // Cargar la imagen
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/logo.jpg"));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(335, 335, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

            JLabel labLogo = new JLabel(iconoEscalado, SwingConstants.CENTER);
            labLogo.setBorder(new LineBorder(Color.DARK_GRAY));
            add(labLogo, BorderLayout.CENTER);
        } catch (NullPointerException e) {
            JLabel errorLabel = new JLabel("No se pudo cargar el logo", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            add(errorLabel, BorderLayout.CENTER);
        }
    }
}
