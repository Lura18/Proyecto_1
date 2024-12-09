package InterfazVentanaEditar;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;
import proyecto.Profesor;

public class VentanaEditarPath extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaEditarPath(LearningPath learningPath, Profesor profesor) {
        // Configuración básica de la ventana
        setTitle("Editar Learning Path");
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de título
        PanelTituloEditar panelTitulo = new PanelTituloEditar(learningPath);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de opciones con botones de edición
        PanelOpcionesEditar panelOpciones = new PanelOpcionesEditar(learningPath, profesor);
        add(panelOpciones, BorderLayout.CENTER);

        // Panel de botones (Cerrar)
        PanelBotonesCerrarEditar panelBotones = new PanelBotonesCerrarEditar(this);
        add(panelBotones, BorderLayout.SOUTH);
    }
}