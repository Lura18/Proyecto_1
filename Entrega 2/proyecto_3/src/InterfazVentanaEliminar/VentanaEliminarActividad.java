package InterfazVentanaEliminar;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;
import proyecto.Profesor;

public class VentanaEliminarActividad extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaEliminarActividad(Profesor profesor, LearningPath learningPath) {
        // Configuración básica de la ventana
        setTitle("Eliminar Actividad");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de título
        PanelTituloEliminar panelTitulo = new PanelTituloEliminar(learningPath);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de lista de actividades
        PanelListaActividades panelLista = new PanelListaActividades(learningPath);
        add(panelLista, BorderLayout.CENTER);

        // Panel de botones (Eliminar y Cancelar)
        PanelBotonesEliminar panelBotones = new PanelBotonesEliminar(this, profesor, learningPath, panelLista);
        add(panelBotones, BorderLayout.SOUTH);
    }
}