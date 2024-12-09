package InterfazVentanaAutenticacion;

import proyecto.Registro;
import proyecto.Tarea;
import proyecto.Usuario;
import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;
import proyecto.Profesor;
import InterfazVentanaPrincipalEstudiante.VentanaPrincipalEstudiante;
import InterfazVentanaPrincipalProfesor.VentanaPrincipalProfesor;
import Persistencia.PersistenciaActividades;
import Persistencia.PersistenciaLearningPaths;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VentanaAutenticacion extends JFrame {
    private static final long serialVersionUID = 1L;
    private Registro registro;
    private List<Actividad> actividades;
    private List<LearningPath> paths;

    public VentanaAutenticacion(Registro registro, List<Actividad> actividades , List<LearningPath> paths) {
        this.registro = registro;
        this.actividades = actividades;
        this.paths = paths;

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
        new VentanaPrincipalProfesor(profesor, actividades, paths, registro).setVisible(true);
    }

    public void abrirVentanaPrincipalEstudiante(Estudiante estudiante) {
        dispose();
        new VentanaPrincipalEstudiante(estudiante, actividades, paths).setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Registro registro = new Registro();
        PersistenciaActividades pAct = new PersistenciaActividades();
        PersistenciaLearningPaths pLP = new PersistenciaLearningPaths();
        
        try {
            registro.cargarUsuarios("./datos/usuarios.json");
        } catch (Exception e) {
            System.out.println("Error cargando usuarios: " + e.getMessage());
        }
        
        List<Actividad> actividades = pAct.cargarActividades("./datos/actividades.json");

        List<LearningPath> paths = pLP.cargarLearningPaths("./datos/learning_paths.json", actividades, registro.getUsuarios());
        
        Estudiante e = registro.getEstudiantes().getLast();
        Profesor p = (Profesor) registro.getUsuarios().get(6);
        LearningPath lp1 = paths.get(0);
        e.inscribirLearningPath(lp1);
        Actividad act = lp1.getActividades().get(1);

        System.out.println(e.getCorreo());
        System.out.println(e.getContrasena());
        System.out.println(p.getCorreo());
        System.out.println(p.getContrasena());
        
        SwingUtilities.invokeLater(() -> {
            VentanaAutenticacion ventana = new VentanaAutenticacion(registro, actividades, paths);
            ventana.setVisible(true);
        });
    }
}
