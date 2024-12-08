package Interfazs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.Registro;
import proyecto.Usuario;
import proyecto.Profesor;
import proyecto.Estudiante;

public class VentanaAutenticacion extends JFrame {
    private static final long serialVersionUID = 1L;

   
    private JPanel panelPrincipal;
    private JPanel panelAutenticacion;
    private JPanel panelRegistro;

    // para autenticar
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JLabel mensajeEstado;

    private Registro registro; 

    public VentanaAutenticacion(Registro registro) {
        this.registro = registro;

       
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout()); 

       
        panelPrincipal = crearPanelPrincipal();
        panelAutenticacion = crearPanelAutenticacion();
        panelRegistro = crearPanelRegistro();

      
        add(panelPrincipal, "Principal");
        add(panelAutenticacion, "Autenticacion");
        add(panelRegistro, "Registro");
    }

    // elegir entre iniciar sesión o registrarse
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel etiquetaPregunta = new JLabel("¿Ya tienes cuenta?");
        etiquetaPregunta.setHorizontalAlignment(SwingConstants.CENTER);

        JButton botonSi = new JButton("Sí, iniciar sesión");
        JButton botonNo = new JButton("No, crear cuenta");

        botonSi.addActionListener(e -> mostrarPanelAutenticacion());
        botonNo.addActionListener(e -> mostrarPanelRegistro());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonSi);
        panelBotones.add(botonNo);

        panel.add(etiquetaPregunta, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    // autenticacion
    private JPanel crearPanelAutenticacion() {
        JPanel panel = new JPanel(new BorderLayout());
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

        panel.add(formulario, BorderLayout.CENTER);
        panel.add(mensajeEstado, BorderLayout.SOUTH);

        //botón de iniciar sesion
        botonIniciarSesion.addActionListener(e -> {
            String correo = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();

            try {
                Profesor profesor = registro.loginProfesor(correo, contrasena);
                mensajeEstado.setForeground(Color.GREEN);
                mensajeEstado.setText("Bienvenido Profesor " + profesor.getNombre());
                abrirVentanaPrincipal();
            } catch (Exception ex1) {
                try {
                    Estudiante estudiante = registro.loginEstudiante(correo, contrasena);
                    mensajeEstado.setForeground(Color.GREEN);
                    mensajeEstado.setText("Bienvenido Estudiante " + estudiante.getNombre());
                    abrirVentanaPrincipal();
                } catch (Exception ex2) {
                    mensajeEstado.setForeground(Color.RED);
                    mensajeEstado.setText("Correo o contraseña incorrectos.");
                }
            }
        });

        return panel;
    }

    //registro
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new BorderLayout());
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

        panel.add(formulario, BorderLayout.CENTER);
        panel.add(mensajeEstado, BorderLayout.NORTH);
        panel.add(botonVolver, BorderLayout.SOUTH);

       
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
                mensajeEstado.setForeground(Color.GREEN);
                mensajeEstado.setText("Usuario registrado exitosamente.");

                
                campoNombre.setText("");
                campoCorreo.setText("");
                campoContrasena.setText("");
            } catch (Exception ex) {
                mensajeEstado.setForeground(Color.RED);
                mensajeEstado.setText("Error al registrar usuario: " + ex.getMessage());
            }
        });

        
        botonVolver.addActionListener(e -> mostrarPanelPrincipal());

        return panel;
    }

    
    private void mostrarPanelAutenticacion() {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "Autenticacion");
    }

    private void mostrarPanelRegistro() {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "Registro");
    }

    private void mostrarPanelPrincipal() {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "Principal");
    }

    
    private void abrirVentanaPrincipal() {
        JOptionPane.showMessageDialog(this, "Acceso permitido. ¡Abriendo ventana principal!");
       
    }

   
    public static void main(String[] args) {
        Registro registro = new Registro();
        try {
            registro.cargarUsuarios("./datos/usuarios.json");
        } catch (Exception e) {
            System.out.println("Error cargando usuarios: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            VentanaAutenticacion ventana = new VentanaAutenticacion(registro);
            ventana.setVisible(true);
        });
    }
}

