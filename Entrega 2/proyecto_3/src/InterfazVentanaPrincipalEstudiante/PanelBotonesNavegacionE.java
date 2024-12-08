package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.Usuario;

public class PanelBotonesNavegacionE extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton botonLearningPaths;
    private JButton botonProgresoGeneral;
    private JButton botonCerrarSesion;

    public PanelBotonesNavegacionE(VentanaPrincipalEstudiante ventanaPrincipal, Usuario usuario) {
        setLayout(new GridLayout(3, 1, 10, 10));

        // Botón para ver Learning Paths
        botonLearningPaths = new JButton("Ver Learning Paths");
        botonLearningPaths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrincipal.mostrarLearningPaths();
            }
        });
        add(botonLearningPaths);

        // Botón para ver Progreso General
        botonProgresoGeneral = new JButton("Ver Progreso General");
        botonProgresoGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrincipal.mostrarProgresoGeneral();
            }
        });
        add(botonProgresoGeneral);

        // Botón para cerrar sesión
        botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrincipal.cerrarSesion();
            }
        });
        add(botonCerrarSesion);
    }
}
