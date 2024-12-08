package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;

import Interfazs.VentanaAutenticacion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.Usuario;
import proyecto.Estudiante;
import proyecto.Profesor;

public class VentanaPrincipalEstudiante extends JFrame {
    private static final long serialVersionUID = 1L;

    private PanelBienvenidaE panelBienvenida;
    private PanelBotonesNavegacionE panelBotonesNavegacion;

    public VentanaPrincipalEstudiante(Usuario usuario) {
        // Configuración de la ventana
        setTitle("Ventana Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear y agregar el panel de bienvenida
        panelBienvenida = new PanelBienvenidaE(usuario);
        add(panelBienvenida, BorderLayout.NORTH);

        // Crear y agregar el panel de navegación
        panelBotonesNavegacion = new PanelBotonesNavegacionE(this, usuario);
        add(panelBotonesNavegacion, BorderLayout.CENTER);
    }

    // Métodos llamados desde los botones del panel de navegación
    public void mostrarLearningPaths() {
        JOptionPane.showMessageDialog(this, "Función para ver Learning Paths no implementada aún.");
    }

    public void mostrarProgresoGeneral() {
        JOptionPane.showMessageDialog(this, "Función para ver Progreso General no implementada aún.");
    }

    public void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose(); // Cerrar la ventana actual
            new Interfazs.VentanaAutenticacion(new proyecto.Registro()).setVisible(true);
        }
    }
}