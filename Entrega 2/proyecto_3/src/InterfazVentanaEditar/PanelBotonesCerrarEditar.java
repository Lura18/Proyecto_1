package InterfazVentanaEditar;

import javax.swing.*;
import java.awt.*;

public class PanelBotonesCerrarEditar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelBotonesCerrarEditar(JFrame ventana) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(100, 30));
        btnCerrar.addActionListener(e -> ventana.dispose());

        add(btnCerrar);
    }
}
