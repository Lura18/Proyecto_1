package InterfazVentanaEnviosYProgreso;

import proyecto.Estudiante;
import javax.swing.*;
import java.awt.*;

public class VentanaProgresoYEnvios extends JFrame {

    public VentanaProgresoYEnvios(Estudiante estudiante) {
        setTitle("Progreso y Envíos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Agregar paneles
        add(new PanelTitulo("Progreso General de Learning Paths"), BorderLayout.NORTH);
        add(new PanelTablaProgreso(estudiante), BorderLayout.CENTER);
        add(new PanelGraficoActividades(estudiante), BorderLayout.SOUTH);
    }


}

