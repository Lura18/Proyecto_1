package InterfazVentanaAutenticacion;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipalAutenticar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelPrincipalAutenticar(VentanaAutenticacion ventana) {
        setLayout(new BorderLayout());

        JLabel etiquetaPregunta = new JLabel("¿Ya tienes cuenta?", SwingConstants.CENTER);
        JButton botonSi = new JButton("Sí, iniciar sesión");
        JButton botonNo = new JButton("No, crear cuenta");

        botonSi.addActionListener(e -> ventana.mostrarPanel("Autenticacion"));
        botonNo.addActionListener(e -> ventana.mostrarPanel("Registro"));

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonSi);
        panelBotones.add(botonNo);

        add(etiquetaPregunta, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
