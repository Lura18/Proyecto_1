package InterfazVentanaEliminar;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import proyecto.Actividad;
import proyecto.LearningPath;

public class PanelListaActividades extends JPanel {
    private static final long serialVersionUID = 1L;

    private JList<String> listaActividades;
    private DefaultListModel<String> modeloLista;

    public PanelListaActividades(LearningPath learningPath) {
        setLayout(new BorderLayout());

        // Modelo de la lista
        modeloLista = new DefaultListModel<>();
        List<Actividad> actividades = learningPath.getActividades();
        for (Actividad actividad : actividades) {
            modeloLista.addElement(actividad.getDescripcion());
        }

        // Lista de actividades
        listaActividades = new JList<>(modeloLista);
        listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ScrollPane para la lista
        JScrollPane scrollPane = new JScrollPane(listaActividades);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Métodos para interactuar con la lista
    public int getActividadSeleccionadaIndex() {
        return listaActividades.getSelectedIndex();
    }

    public void eliminarActividadDeLista(int index) {
        modeloLista.remove(index);
    }

    public String getActividadSeleccionada() {
        return listaActividades.getSelectedValue();
    }
}
