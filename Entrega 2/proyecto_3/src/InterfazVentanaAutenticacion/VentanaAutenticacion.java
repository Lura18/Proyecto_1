package InterfazVentanaAutenticacion;

import proyecto.Registro;
import proyecto.Usuario;
import proyecto.Estudiante;
import proyecto.Profesor;
import InterfazVentanaPrincipalEstudiante.VentanaPrincipalEstudiante;
import InterfazVentanaPrincipalProfesor.VentanaPrincipalProfesor;

import javax.swing.*;
import java.awt.*;

public class VentanaAutenticacion extends JFrame {
    private static final long serialVersionUID = 1L;
    private Registro registro;

    public VentanaAutenticacion(Registro registro) {
        this.registro = registro;

        // Configuración de la ventana
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Agregar paneles
        add(new PanelPrincipalAutenticar(this), "Principal");
        add(new PanelAutenticacion(this, registro), "Autenticacion");
        add(new PanelRegistro(this, registro), "Registro");
    }

    // Método para cambiar entre paneles
    public void mostrarPanel(String panel) {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), panel);
    }

    // Métodos para abrir ventanas principales
    public void abrirVentanaPrincipalProfesor(Profesor profesor) {
        dispose();
        new VentanaPrincipalProfesor(profesor).setVisible(true);
    }

    public void abrirVentanaPrincipalEstudiante(Estudiante estudiante) {
        dispose();
        new VentanaPrincipalEstudiante(estudiante).setVisible(true);
    }

    public static void main(String[] args) {
        Registro registro = new Registro();
        try {
            registro.cargarUsuarios("./datos/usuarios.json");
        } catch (Exception e) {
            System.out.println("Error cargando usuarios: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            VentanaAutenticacion ventana = new VentanaAutenticacion(registro);
            ventana.setVisible(true);
        });
    }
}
