package InterfazVentanaPrincipalProfesor;

import javax.swing.*;

import InterfazVentanaAutenticacion.VentanaAutenticacion;
import InterfazVentanaPrincipalEstudiante.PanelBienvenidaEstudiante;
import InterfazVentanaPrincipalEstudiante.PanelBotonesEstudiante;
import InterfazVentanaPrincipalEstudiante.PanelLearningPathsEstudiante;
import InterfazVentanaPrincipalEstudiante.PanelLogoEstudiante;

import java.awt.*;
import java.util.List;

import proyecto.Actividad;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Registro;
import proyecto.Usuario;

public class VentanaPrincipalProfesor extends JFrame {
    private static final long serialVersionUID = 1L;

    private Profesor profesor;

    public VentanaPrincipalProfesor(Profesor profesor, List<Actividad> actividades , List<LearningPath> paths, Registro sistema) {
    	
    	this.profesor = profesor;


        // Configuración de la ventana
        setTitle("Ventana Principal - Profesor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Titulo bienvenida
        PanelBienvenidaProfesor panelBienvenida = new PanelBienvenidaProfesor(profesor,actividades, paths);
        add(panelBienvenida, BorderLayout.NORTH);
        
        // Panel de logo
        JPanel panelLogo = new PanelLogoProfesor();
        add(panelLogo, BorderLayout.EAST);

        // Panel central con los Learning Paths
        JPanel panelLearningPaths = new PanelLearningPathsProfesor(profesor);
        add(panelLearningPaths, BorderLayout.WEST);

        // Panel de botones de navegación
        JPanel panelBotones = new PanelBotonesProfesor(this, profesor, paths, sistema, panelLearningPaths);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void verRecomendaciones() {
        PanelLearningPathsEstudiante panelLearningPaths = (PanelLearningPathsEstudiante) getContentPane().getComponent(1);
        JTable tabla = panelLearningPaths.getTablaLearningPaths();

        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            String titulo = (String) tabla.getValueAt(filaSeleccionada, 0);
            JOptionPane.showMessageDialog(this, "Mostrando recomendaciones para: " + titulo);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un Learning Path.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void inscribirseLearningPath() {
        JOptionPane.showMessageDialog(this, "Abrir ventana para inscribirse a un Learning Path.");
    }

    public void verProgresoGeneral() {
        JOptionPane.showMessageDialog(this, "Abrir ventana para ver progreso general.");
    }

    public PanelLearningPathsEstudiante getPanelLearningPaths() {
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof PanelLearningPathsEstudiante) {
                return (PanelLearningPathsEstudiante) component;
            }
        }
        return null;
    }
    
}
