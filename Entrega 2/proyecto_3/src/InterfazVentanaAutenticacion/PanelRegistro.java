package InterfazVentanaAutenticacion;

import proyecto.Registro;
import proyecto.Estudiante;
import proyecto.Profesor;
import proyecto.Usuario;

import javax.swing.*;
import java.awt.*;

public class PanelRegistro extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelRegistro(VentanaAutenticacion ventana, Registro registro) {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel etiquetaNombre = new JLabel("Nombre:");
        JTextField campoNombre = new JTextField();
        JLabel etiquetaCorreo = new JLabel("Correo:");
        JTextField campoCorreo = new JTextField();
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        JPasswordField campoContrasena = new JPasswordField();
        JLabel etiquetaTipo = new JLabel("Tipo de usuario:");
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Estudiante", "Profesor"});

        JButton botonRegistrar = new JButton("Registrar");
        JButton botonVolver = new JButton("Volver");

        formulario.add(etiquetaNombre);
        formulario.add(campoNombre);
        formulario.add(etiquetaCorreo);
        formulario.add(campoCorreo);
        formulario.add(etiquetaContrasena);
        formulario.add(campoContrasena);
        formulario.add(etiquetaTipo);
        formulario.add(comboTipo);
        formulario.add(new JLabel());
        formulario.add(botonRegistrar);

        JLabel mensajeEstado = new JLabel(" ");
        mensajeEstado.setForeground(Color.RED);
        mensajeEstado.setHorizontalAlignment(SwingConstants.CENTER);

        add(formulario, BorderLayout.CENTER);
        add(mensajeEstado, BorderLayout.NORTH);
        add(botonVolver, BorderLayout.SOUTH);

        botonRegistrar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();
            String tipoUsuario = (String) comboTipo.getSelectedItem();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                mensajeEstado.setText("Por favor, complete todos los campos.");
                return;
            }

            try {
                for (Usuario usuario : registro.getUsuarios()) {
                    if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                        mensajeEstado.setText("El correo ya está registrado.");
                        return;
                    }
                }

                if (tipoUsuario.equals("Estudiante")) {
                    Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena);
                    registro.registrarEstudiante(nuevoEstudiante);
                } else if (tipoUsuario.equals("Profesor")) {
                    Profesor nuevoProfesor = new Profesor(nombre, correo, contrasena);
                    registro.registrarProfesor(nuevoProfesor);
                }

                registro.salvarUsuarios("./datos/usuarios.json");
                mensajeEstado.setText("Usuario registrado exitosamente.");
            } catch (Exception ex) {
                mensajeEstado.setText("Error al registrar usuario.");
            }
        });

        botonVolver.addActionListener(e -> ventana.mostrarPanel("Principal"));
    }
}

