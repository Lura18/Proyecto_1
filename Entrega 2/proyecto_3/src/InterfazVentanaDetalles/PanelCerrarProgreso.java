package InterfazVentanaDetalles;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;

public class PanelCerrarProgreso extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelCerrarProgreso(JFrame ventana) {
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> ventana.dispose()); // Llama a dispose() de la ventana principal
        add(btnCerrar, BorderLayout.CENTER);
    }
}
