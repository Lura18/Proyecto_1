package Interfazs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.Usuario;
import proyecto.Estudiante;
import proyecto.Profesor;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    // Componentes principales
    private JLabel mensajeBienvenida;
    private JButton botonLearningPaths;
    private JButton botonProgresoGeneral;
    private JButton botonCerrarSesion;

    private Usuario usuarioActual; // Usuario que ha iniciado sesión

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;

        // Configuración de la ventana
        setTitle("Ventana Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de bienvenida
        JPanel panelBienvenida = new JPanel();
        mensajeBienvenida = new JLabel("Bienvenido " + usuario.getNombre() + "!");
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        panelBienvenida.add(mensajeBienvenida);
        add(panelBienvenida, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        botonLearningPaths = new JButton("Ver Learning Paths");
        botonProgresoGeneral = new JButton("Ver Progreso General");
        botonCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(botonLearningPaths);
        panelBotones.add(botonProgresoGeneral);
        panelBotones.add(botonCerrarSesion);

        add(panelBotones, BorderLayout.CENTER);

        // Acción para "Ver Learning Paths"
        botonLearningPaths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLearningPaths();
            }
        });

        // Acción para "Ver Progreso General"
        botonProgresoGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProgresoGeneral();
            }
        });

        // Acción para "Cerrar Sesión"
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    // Métodos para las acciones de los botones
    private void mostrarLearningPaths() {
        JOptionPane.showMessageDialog(this, "Función para ver Learning Paths no implementada aún.");
    }

    private void mostrarProgresoGeneral() {
        JOptionPane.showMessageDialog(this, "Función para ver Progreso General no implementada aún.");
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose(); // Cerrar la ventana actual
            VentanaAutenticacion ventanaAutenticacion = new VentanaAutenticacion(new proyecto.Registro());
            ventanaAutenticacion.setVisible(true); // Regresar a la ventana de autenticación
        }
    }

    // Método principal para probar la conexión desde la ventana de autenticación
    public static void abrirDesdeAutenticacion(Usuario usuario) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario);
            ventanaPrincipal.setVisible(true);
        });
    }
}
