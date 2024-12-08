package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;
import proyecto.Estudiante;
import proyecto.LearningPath;

public class PanelLearningPathsEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable tablaLearningPaths;
    private DefaultTableModel modeloTabla;

    public PanelLearningPathsEstudiante(Estudiante estudiante) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Fondo blanco

        // Título del panel
        JLabel titulo = new JLabel("Learning Paths Inscritos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        titulo.setForeground(Color.BLACK);
        add(titulo, BorderLayout.NORTH);

        // Crear modelo de tabla no editable con una sola columna
        modeloTabla = new DefaultTableModel(new Object[]{"Título"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas sean no editables
            }
        };

        // Llenar la tabla con los títulos de los Learning Paths inscritos
        actualizarTabla(estudiante);

        // Crear la tabla y asignarle el modelo
        tablaLearningPaths = new JTable(modeloTabla);

        // Ocultar el encabezado de la tabla
        JTableHeader header = tablaLearningPaths.getTableHeader();
        header.setVisible(false);

        // Personalizar el tamaño del texto en las celdas
        tablaLearningPaths.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaLearningPaths.setRowHeight(40); // Aumentar altura de las filas
        tablaLearningPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección de una fila
        tablaLearningPaths.setShowGrid(false); // Ocultar las líneas de la cuadrícula

        // Personalizar el centrado del texto en las celdas
        TableCellRenderer renderer = tablaLearningPaths.getDefaultRenderer(String.class);
        tablaLearningPaths.setDefaultRenderer(String.class, (table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
            return label;
        });

        // Agregar un borde a las celdas de la tabla (Learning Path titles)
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1); // Borde negro de 1px
        tablaLearningPaths.setBorder(border);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tablaLearningPaths);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin bordes para el scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Ajustar el tamaño para evitar que el contenido se pase por encima del logo
        setPreferredSize(new Dimension(450, 400)); // Ajustar el tamaño del panel para que encaje
    }

    // Método para actualizar la tabla con los Learning Paths inscritos
    public void actualizarTabla(Estudiante estudiante) {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar los nuevos elementos
        List<LearningPath> learningPathsInscritos = estudiante.getLearningPathsInscritos();
        for (LearningPath lp : learningPathsInscritos) {
            modeloTabla.addRow(new Object[]{lp.getTitulo()});
        }
    }

    // Método para obtener el Learning Path seleccionado
    public LearningPath getLearningPathSeleccionado(Estudiante estudiante) {
        int filaSeleccionada = tablaLearningPaths.getSelectedRow();
        if (filaSeleccionada != -1) {
            String tituloSeleccionado = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            for (LearningPath lp : estudiante.getLearningPathsInscritos()) {
                if (lp.getTitulo().equals(tituloSeleccionado)) {
                    return lp;
                }
            }
        }
        return null; // Ningún Learning Path seleccionado
    }

    // Getter para la tabla (si es necesario interactuar con ella)
    public JTable getTablaLearningPaths() {
        return tablaLearningPaths;
    }
}