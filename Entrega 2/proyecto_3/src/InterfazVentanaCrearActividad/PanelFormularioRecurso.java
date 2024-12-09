package InterfazVentanaCrearActividad;

import javax.swing.*;
import java.awt.*;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.RecursoEducativo;

public class PanelFormularioRecurso extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtDescripcion;
    private JTextField txtObjetivo;
    private JTextField txtDificultad;
    private JTextField txtDuracion;
    private JCheckBox chkObligatorio;
    private JTextField txtTipoRecurso;
    private JTextField txtEnlace;

    public PanelFormularioRecurso() {
        setLayout(new GridLayout(7, 2, 10, 10));

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

        add(new JLabel("Tipo de recurso:"));
        txtTipoRecurso = new JTextField();
        add(txtTipoRecurso);

        add(new JLabel("Enlace:"));
        txtEnlace = new JTextField();
        add(txtEnlace);
    }

    public RecursoEducativo crearRecurso(Profesor profesor, LearningPath learningPath) {
    	if (!validarCampos()) return null;
        String descripcion = txtDescripcion.getText();
        String objetivo = txtObjetivo.getText();
        String dificultad = txtDificultad.getText();
        int duracion = Integer.parseInt(txtDuracion.getText());
        boolean obligatorio = chkObligatorio.isSelected();
        String tipoRecurso = txtTipoRecurso.getText();
        String enlace = txtEnlace.getText();

        RecursoEducativo recurso = new RecursoEducativo(learningPath, descripcion, objetivo, dificultad, duracion, obligatorio, tipoRecurso, enlace, profesor);
        profesor.añadirActividadALearningPath(learningPath, recurso);

        return recurso;
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
        if (txtTipoRecurso.getText().isEmpty()) {
            mostrarMensajeError("El tipo de recurso no puede estar vacío.");
            return false;
        }

        if (txtEnlace.getText().isEmpty()) {
            mostrarMensajeError("El enlace no puede estar vacío.");
            return false;
        }

        return true;
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error en el formulario", JOptionPane.ERROR_MESSAGE);
    }
}