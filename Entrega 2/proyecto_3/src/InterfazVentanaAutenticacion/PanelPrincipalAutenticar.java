package InterfazVentanaAutenticacion;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipalAutenticar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelPrincipalAutenticar(VentanaAutenticacion ventana) {
        setLayout(new BorderLayout());

        // Fondo con la imagen
        JLabel fondo = new JLabel(new ImageIcon("src/imagenes/logo.jpg"));
        fondo.setLayout(new BorderLayout());

        // Panel para centrar el texto y los botones
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false); // Deja transparente para que no tape la imagen

        // Texto con el fondo blanco
        JPanel panelTexto = new JPanel(new GridBagLayout());
        panelTexto.setOpaque(false); // Fondo transparente
        JLabel etiquetaPregunta = new JLabel("¿Ya tienes cuenta?", SwingConstants.CENTER);
        etiquetaPregunta.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaPregunta.setOpaque(true); // Fondo visible
        etiquetaPregunta.setBackground(Color.WHITE); // Fondo blanco
        etiquetaPregunta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Márgenes
        panelTexto.add(etiquetaPregunta);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton botonSi = new JButton("Sí, iniciar sesión");
        JButton botonNo = new JButton("No, crear cuenta");

        botonSi.addActionListener(e -> ventana.mostrarPanel("Autenticacion"));
        botonNo.addActionListener(e -> ventana.mostrarPanel("Registro"));

        panelBotones.add(botonSi);
        panelBotones.add(botonNo);

        // Añadir los elementos al panel central
        panelCentral.add(panelTexto, BorderLayout.CENTER);
        panelCentral.add(panelBotones, BorderLayout.SOUTH);

        // Añadir el panel central sobre el fondo
        fondo.add(panelCentral, BorderLayout.CENTER);
        add(fondo);
    }
}

