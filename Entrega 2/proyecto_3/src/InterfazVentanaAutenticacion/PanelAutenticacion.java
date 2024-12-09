package InterfazVentanaAutenticacion;

import proyecto.Registro;
import proyecto.Estudiante;
import proyecto.Profesor;

import javax.swing.*;
import java.awt.*;

public class PanelAutenticacion extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JLabel mensajeEstado;

    public PanelAutenticacion(VentanaAutenticacion ventana, Registro registro) {
        setLayout(new BorderLayout());

        // Fondo con imagen
        JLabel fondo = new JLabel(new ImageIcon("./src/imagenes/logo.jpg"));
        fondo.setLayout(new BorderLayout());
        add(fondo);

        // Contenedor para el formulario
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false); // Transparente para ver el fondo

        JLabel titulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        titulo.setForeground(Color.BLACK);

        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formulario.setOpaque(false); // Transparente para ver el fondo

        JLabel etiquetaCorreo = new JLabel("Correo:");
        etiquetaCorreo.setFont(new Font("Arial", Font.BOLD, 14));
        campoCorreo = new JTextField();
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        campoContrasena = new JPasswordField();
        JButton botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setBackground(new Color(173, 216, 230)); // Botón azul claro
        botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 14));

        formulario.add(etiquetaCorreo);
        formulario.add(campoCorreo);
        formulario.add(etiquetaContrasena);
        formulario.add(campoContrasena);
        formulario.add(new JLabel());
        formulario.add(botonIniciarSesion);

        mensajeEstado = new JLabel(" ");
        mensajeEstado.setForeground(Color.RED);
        mensajeEstado.setHorizontalAlignment(SwingConstants.CENTER);

        panelCentral.add(titulo, BorderLayout.NORTH);
        panelCentral.add(formulario, BorderLayout.CENTER);
        panelCentral.add(mensajeEstado, BorderLayout.SOUTH);

        fondo.add(panelCentral, BorderLayout.CENTER);

        botonIniciarSesion.addActionListener(e -> {
            String correo = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();

            try {
                Profesor profesor = registro.loginProfesor(correo, contrasena);
                ventana.abrirVentanaPrincipalProfesor(profesor);
            } catch (Exception ex1) {
                try {
                    Estudiante estudiante = registro.loginEstudiante(correo, contrasena);
                    ventana.abrirVentanaPrincipalEstudiante(estudiante);
                } catch (Exception ex2) {
                    mensajeEstado.setText("Correo o contraseña incorrectos.");
                }
            }
        });
    }
}

