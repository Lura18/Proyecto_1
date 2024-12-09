package InterfazVentanaEditar;

import javax.swing.*;

import InterfazVentanaCrearActividad.VentanaCrearActividad;
import InterfazVentanaEliminar.VentanaEliminarActividad;
import proyecto.LearningPath;
import proyecto.Profesor;

import java.awt.*;

public class PanelOpcionesEditar extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelOpcionesEditar(LearningPath learningPath, Profesor profesor) {
    	setLayout(new BorderLayout());

        // Texto de opciones
        JLabel lblOpciones = new JLabel("¿Cómo quiere editar el Learning Path?", SwingConstants.CENTER);
        lblOpciones.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOpciones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado
        add(lblOpciones, BorderLayout.NORTH);

        // Botones de edición
        JToggleButton btnCrearActividad = new JToggleButton("Crear actividad");
        btnCrearActividad.setPreferredSize(new Dimension(170, 80)); // Tamaño reducido

        JToggleButton btnEliminarActividad = new JToggleButton("Eliminar actividad");
        btnEliminarActividad.setPreferredSize(new Dimension(170, 80)); // Tamaño reducido


        // Añadir funcionalidad al botón "Eliminar actividad"
        btnEliminarActividad.addActionListener(e -> {
            VentanaEliminarActividad ventanaEliminar = new VentanaEliminarActividad(profesor, learningPath);
            ventanaEliminar.setVisible(true);
        });
        
        btnCrearActividad.addActionListener(e -> {
            VentanaCrearActividad ventanaCrear = new VentanaCrearActividad(profesor, learningPath);
            ventanaCrear.setVisible(true);
        });

        // Añadir funcionalidad a los botones
        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(btnCrearActividad);
        grupoBotones.add(btnEliminarActividad);

        // Panel con diseño horizontal
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnCrearActividad);
        panelBotones.add(btnEliminarActividad);

        add(panelBotones, BorderLayout.CENTER);
    }
}
