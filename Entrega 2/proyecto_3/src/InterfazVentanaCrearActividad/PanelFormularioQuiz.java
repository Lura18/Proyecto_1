package InterfazVentanaCrearActividad;

import javax.swing.*;
import java.awt.*;
import proyecto.Quiz;
import proyecto.LearningPath;
import proyecto.Profesor;

public class PanelFormularioQuiz extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtDescripcion;
    private JTextField txtObjetivo;
    private JTextField txtDificultad;
    private JTextField txtDuracion;
    private JCheckBox chkObligatorio;
    private JTextField txtNotaAprobacion;

    public PanelFormularioQuiz() {
        setLayout(new GridLayout(6, 2, 10, 10));

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

        add(new JLabel("Nota mínima para aprobar:"));
        txtNotaAprobacion = new JTextField();
        add(txtNotaAprobacion);
    }

    public Quiz crearQuiz(Profesor profesor, LearningPath learningPath) {
        if (!validarCampos()) return null;

        String descripcion = txtDescripcion.getText();
        String objetivo = txtObjetivo.getText();
        String dificultad = txtDificultad.getText();
        int duracion = Integer.parseInt(txtDuracion.getText());
        boolean obligatorio = chkObligatorio.isSelected();
        double notaAprobacion = Double.parseDouble(txtNotaAprobacion.getText());

        Quiz quiz = new Quiz(learningPath, descripcion, objetivo, dificultad, duracion, obligatorio, notaAprobacion, profesor, "Texto");
        profesor.añadirActividadALearningPath(learningPath, quiz);

        return quiz;
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

        if (txtNotaAprobacion.getText().isEmpty()) {
            mostrarMensajeError("La nota de aprobación no puede estar vacía.");
            return false;
        }

        try {
            double nota = Double.parseDouble(txtNotaAprobacion.getText());
            if (nota < 0 || nota > 100) {
                mostrarMensajeError("La nota de aprobación debe estar entre 0 y 100.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensajeError("La nota de aprobación debe ser un número válido.");
            return false;
        }

        return true; // Validar también otros campos como en los formularios anteriores
    }
    
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error en el formulario", JOptionPane.ERROR_MESSAGE);
    }
}