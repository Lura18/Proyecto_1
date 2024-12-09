package InterfazVentanaCrearActividad;

import javax.swing.*;
import java.awt.*;
import proyecto.Tarea;
import proyecto.LearningPath;
import proyecto.Profesor;

public class PanelFormularioTarea extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtDescripcion;
    private JTextField txtObjetivo;
    private JTextField txtDificultad;
    private JTextField txtDuracion;
    private JCheckBox chkObligatorio;

    public PanelFormularioTarea() {
        setLayout(new GridLayout(5, 2, 10, 10));

        // Campos del formulario
        add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        add(txtDescripcion);

        add(new JLabel("Objetivo:"));
        txtObjetivo = new JTextField();
        add(txtObjetivo);

        add(new JLabel("Dificultad:"));
        txtDificultad = new JTextField();
        add(txtDificultad);

        add(new JLabel("Duración esperada (min):"));
        txtDuracion = new JTextField();
        add(txtDuracion);

        add(new JLabel("¿Es obligatorio?"));
        chkObligatorio = new JCheckBox();
        add(chkObligatorio);
    }

    public Tarea crearTarea(Profesor profesor, LearningPath learningPath) {
        if (!validarCampos()) return null;

        String descripcion = txtDescripcion.getText();
        String objetivo = txtObjetivo.getText();
        String dificultad = txtDificultad.getText();
        int duracion = Integer.parseInt(txtDuracion.getText());
        boolean obligatorio = chkObligatorio.isSelected();

        Tarea tarea = new Tarea(learningPath, descripcion, objetivo, dificultad, duracion, obligatorio, profesor);
        profesor.añadirActividadALearningPath(learningPath, tarea);

        return tarea;
    }
    
    private boolean validarCampos() {
        if (txtDescripcion.getText().isEmpty()) {
            mostrarMensajeError("La descripción no puede estar vacía.");
            return false;
        }

        if (txtObjetivo.getText().isEmpty()) {
            mostrarMensajeError("El objetivo no puede estar vacío.");
            return false;
        }

        if (txtDificultad.getText().isEmpty()) {
            mostrarMensajeError("La dificultad no puede estar vacía.");
            return false;
        }

        try {
            int duracion = Integer.parseInt(txtDuracion.getText());
            if (duracion <= 0) {
                mostrarMensajeError("La duración debe ser mayor a 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("La duración debe ser un número válido.");
            return false;
        }

        return true;
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error en el formulario", JOptionPane.ERROR_MESSAGE);
    }
}
