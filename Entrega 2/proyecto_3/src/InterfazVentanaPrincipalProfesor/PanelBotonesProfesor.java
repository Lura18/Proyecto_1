package InterfazVentanaPrincipalProfesor;

import javax.swing.*;

import InterfazVentanaCrear.VentanaCrearPath;
import InterfazVentanaEditar.VentanaEditarPath;

import java.awt.*;
import java.util.List;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Registro;

public class PanelBotonesProfesor extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelBotonesProfesor(JFrame ventanaPrincipal, Profesor profesor, List<LearningPath> paths, Registro sistema, JPanel panelLearningPaths) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Espaciado entre botones
        setBackground(Color.LIGHT_GRAY); // Fondo claro
        setPreferredSize(new Dimension(800, 100)); // Tamaño preferido del panel

        // Crear botones
        JButton btnEditar = crearBoton("Editar Learning Path");
        btnEditar.addActionListener(e -> editarLearningPath(ventanaPrincipal, profesor, panelLearningPaths));
        add(btnEditar);

        //JButton btnCalificar = crearBoton("Calificar actividades");
        //btnCalificar.addActionListener(e -> calificar(ventanaPrincipal, profesor));
        //add(btnCalificar);

        JButton btnCrear = crearBoton("Crear Learning Path");
        btnCrear.addActionListener(e -> crearPath(ventanaPrincipal, profesor, sistema, panelLearningPaths)); // Pasamos el panel
        add(btnCrear);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 14)); // Tamaño de fuente
        boton.setPreferredSize(new Dimension(260, 40)); // Tamaño del botón
        boton.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        boton.setForeground(Color.BLACK); // Color del texto
        return boton;
    }

    private void calificar(JFrame ventanaPrincipal, Profesor profesor) {
        // Acción para calificar actividades
    }

    private void editarLearningPath(JFrame ventanaPrincipal, Profesor profesor, JPanel panelLearningPaths) {
        // Obtener el Learning Path seleccionado
        LearningPath seleccionado = ((PanelLearningPathsProfesor) panelLearningPaths).getLearningPathSeleccionado(profesor);
        
        if (seleccionado == null) {
            // Mostrar mensaje de error si no se seleccionó un Learning Path
            JOptionPane.showMessageDialog(ventanaPrincipal, "Debe seleccionar un Learning Path para editar primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Abrir la ventana de edición si se seleccionó un Learning Path
        VentanaEditarPath ventanaEditar = new VentanaEditarPath(seleccionado, profesor);
        ventanaEditar.setVisible(true);
    }

    private void crearPath(JFrame ventanaPrincipal, Profesor profesor, Registro sistema, JPanel panelLearningPaths) {
        // Crear ventana para crear un nuevo Learning Path
        VentanaCrearPath ventanaCrear = new VentanaCrearPath(profesor, sistema, panelLearningPaths);
        ventanaCrear.setVisible(true);
    }
}