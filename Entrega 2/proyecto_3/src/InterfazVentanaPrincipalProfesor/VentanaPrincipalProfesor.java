package InterfazVentanaPrincipalProfesor;

import javax.swing.*;

import InterfazVentanaAutenticacion.VentanaAutenticacion;

import java.awt.*;
import java.util.List;

import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Usuario;

public class VentanaPrincipalProfesor extends JFrame {
    private static final long serialVersionUID = 1L;

    private PanelBienvenidaP panelBienvenida;
    private PanelBotonesNavegacionP panelBotonesNavegacion;
    private List<Actividad> actividades;
    private List<LearningPath> paths;

    public VentanaPrincipalProfesor(Usuario usuario, List<Actividad> actividades , List<LearningPath> paths) {
    	
        this.actividades = actividades;
        this.paths = paths;
        
        // Configuración de la ventana
        setTitle("Ventana Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear y agregar el panel de bienvenida
        panelBienvenida = new PanelBienvenidaP(usuario);
        add(panelBienvenida, BorderLayout.NORTH);

        // Crear y agregar el panel de navegación
        panelBotonesNavegacion = new PanelBotonesNavegacionP(this, usuario);
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
            new VentanaAutenticacion(new proyecto.Registro(), actividades, paths).setVisible(true);
        }
    }
}