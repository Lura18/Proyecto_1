package InterfazVentanaPrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import proyecto.Estudiante;

public class PanelLearningPathsEstudiante extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable tablaLearningPaths;

    public PanelLearningPathsEstudiante(Estudiante estudiante) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Fondo blanco

        JLabel titulo = new JLabel("Learning Paths inscritos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        titulo.setForeground(Color.BLACK);
        add(titulo, BorderLayout.NORTH);

        // Crear la tabla
        String[] columnas = { "Título", "Descripción" };
        Object[][] datos = {}; // Aquí puedes añadir datos dinámicos
        tablaLearningPaths = new JTable(datos, columnas);

        JScrollPane scrollPane = new JScrollPane(tablaLearningPaths);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTablaLearningPaths() {
        return tablaLearningPaths;
    }
}

