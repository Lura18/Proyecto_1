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
        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel etiquetaCorreo = new JLabel("Correo:");
        campoCorreo = new JTextField();
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        campoContrasena = new JPasswordField();
        JButton botonIniciarSesion = new JButton("Iniciar Sesión");

        formulario.add(etiquetaCorreo);
        formulario.add(campoCorreo);
        formulario.add(etiquetaContrasena);
        formulario.add(campoContrasena);
        formulario.add(new JLabel());
        formulario.add(botonIniciarSesion);

        mensajeEstado = new JLabel(" ");
        mensajeEstado.setForeground(Color.RED);
        mensajeEstado.setHorizontalAlignment(SwingConstants.CENTER);

        add(formulario, BorderLayout.CENTER);
        add(mensajeEstado, BorderLayout.SOUTH);

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
