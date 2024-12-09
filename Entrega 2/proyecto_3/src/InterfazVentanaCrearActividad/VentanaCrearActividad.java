package InterfazVentanaCrearActividad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Actividad;

public class VentanaCrearActividad extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> tipoActividad;
    private JPanel panelFormulario;
    private JButton btnCrear;
    private JButton btnCancelar;

    public VentanaCrearActividad(Profesor profesor, LearningPath learningPath) {
        // Configuración básica
        setTitle("Crear Actividad");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título de la ventana
        JLabel lblTitulo = new JLabel("Crear nueva actividad para: " + learningPath.getTitulo(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        lblTitulo.setForeground(Color.BLACK);
        add(lblTitulo, BorderLayout.NORTH);

        // Selector de tipo de actividad
        JPanel panelSelector = new JPanel(new FlowLayout());
        JLabel lblTipo = new JLabel("Seleccione el tipo de actividad:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        tipoActividad = new JComboBox<>(new String[]{"Recurso Educativo", "Encuesta", "Tarea", "Quiz", "Examen"});
        tipoActividad.addActionListener(e -> actualizarFormulario()); // Actualizar el formulario dinámicamente
        panelSelector.add(lblTipo);
        panelSelector.add(tipoActividad);
        add(panelSelector, BorderLayout.NORTH);

        // Panel dinámico para el formulario
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new CardLayout());
        actualizarFormulario();
        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear");
        btnCancelar = new JButton("Cancelar");

        // Acción del botón "Crear"
        btnCrear.addActionListener(e -> {
            Actividad nuevaActividad = crearActividadSegunTipo(profesor, learningPath);
            if (nuevaActividad != null) {
                JOptionPane.showMessageDialog(this, "La actividad fue creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerrar la ventana
            }
        });

        // Acción del botón "Cancelar"
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el formulario dinámico según el tipo de actividad seleccionado.
     */
    private void actualizarFormulario() {
        panelFormulario.removeAll();
        String tipoSeleccionado = (String) tipoActividad.getSelectedItem();

        if (tipoSeleccionado.equals("Recurso Educativo")) {
            panelFormulario.add(new PanelFormularioRecurso());
        } else if (tipoSeleccionado.equals("Encuesta")) {
            panelFormulario.add(new PanelFormularioEncuesta());
        } else if (tipoSeleccionado.equals("Tarea")) {
            panelFormulario.add(new PanelFormularioTarea());
        } else if (tipoSeleccionado.equals("Quiz")) {
            panelFormulario.add(new PanelFormularioQuiz());
        } else if (tipoSeleccionado.equals("Examen")) {
            panelFormulario.add(new PanelFormularioExamen());
        }

        panelFormulario.revalidate();
        panelFormulario.repaint();
    }

    /**
     * Crea la actividad según el tipo seleccionado y los datos ingresados en el formulario.
     */
    private Actividad crearActividadSegunTipo(Profesor profesor, LearningPath learningPath) {
        String tipoSeleccionado = (String) tipoActividad.getSelectedItem();
        JPanel formularioActual = (JPanel) panelFormulario.getComponent(0);

        // Dependiendo del tipo, obtenemos los datos del formulario correspondiente
        if (tipoSeleccionado.equals("Recurso Educativo")) {
            return ((PanelFormularioRecurso) formularioActual).crearRecurso(profesor, learningPath);
        } else if (tipoSeleccionado.equals("Encuesta")) {
            return ((PanelFormularioEncuesta) formularioActual).crearEncuesta(profesor, learningPath);
        } else if (tipoSeleccionado.equals("Tarea")) {
            return ((PanelFormularioTarea) formularioActual).crearTarea(profesor, learningPath);
        } else if (tipoSeleccionado.equals("Quiz")) {
            return ((PanelFormularioQuiz) formularioActual).crearQuiz(profesor, learningPath);
        } else if (tipoSeleccionado.equals("Examen")) {
            return ((PanelFormularioExamen) formularioActual).crearExamen(profesor, learningPath);
        }

        return null; // No se creó ninguna actividad
    }
}