package InterfazVentanaEnviosYProgreso;

import proyecto.Actividad;
import proyecto.Estudiante;
import proyecto.LearningPath;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaProgresoYEnvios extends JFrame {
    private Estudiante estudiante;
    private JTable tablaProgreso;
    private JPanel panelGrafico;
    private Map<LocalDate, Integer> enviosPorDia;

    public VentanaProgresoYEnvios(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.enviosPorDia = new HashMap<>();

        // Configuración básica de la ventana
        setTitle("Progreso y Envíos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Fondo blanco

        // Crear y agregar el título estilizado
        JPanel tituloPanel = crearTituloEstilizado("Progreso y Envíos");
        add(tituloPanel, BorderLayout.NORTH);

        // Panel central: Contenedor para tabla y gráfico
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Color.WHITE);

        // Panel superior con tabla de progreso
        tablaProgreso = crearTablaProgreso();
        JScrollPane scrollPane = new JScrollPane(tablaProgreso);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panelCentral.add(scrollPane, BorderLayout.NORTH);

        // Panel inferior con gráfico
        panelGrafico = crearGraficoEnvios();
        panelCentral.add(panelGrafico, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE); // Fondo blanco
        JButton btnContinuar = new JButton("Continuar con Learning Path");
        JButton btnCerrar = new JButton("Cerrar");

        btnContinuar.setBackground(new Color(173, 216, 230)); // Azul claro
        btnContinuar.setForeground(Color.BLACK);
        btnCerrar.setBackground(new Color(173, 216, 230)); // Azul claro
        btnCerrar.setForeground(Color.BLACK);

        btnContinuar.addActionListener(e -> mostrarLearningPathsDisponibles());
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnContinuar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarVista();
    }

    private JPanel crearTituloEstilizado(String texto) {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        panelTitulo.setLayout(new BorderLayout());

        JLabel etiquetaTitulo = new JLabel(texto, SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaTitulo.setForeground(Color.BLACK);

        panelTitulo.add(etiquetaTitulo, BorderLayout.CENTER);
        panelTitulo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY)); // Línea oscura inferior
        return panelTitulo;
    }

    private JTable crearTablaProgreso() {
        String[] columnas = {"Learning path", "Porcentaje de Progreso", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables
            }
        };

        List<LearningPath> learningPaths = estudiante.getLearningPathsInscritos();
        for (LearningPath lp : learningPaths) {
            int totalActividades = lp.getActividades().size();
            long completadas = lp.getActividades().stream().filter(Actividad::isCompletada).count();

            double progreso = (totalActividades > 0) ? (completadas * 100.0 / totalActividades) : 0;
            String estado = (progreso == 100) ? "Completado" : "En Progreso";

            modeloTabla.addRow(new Object[]{lp.getTitulo(), progreso, estado});
        }

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30); // Altura de filas
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setGridColor(Color.LIGHT_GRAY);
        tabla.setShowGrid(true);
        tabla.setSelectionBackground(new Color(173, 216, 230)); // Azul claro para selección
        tabla.setSelectionForeground(Color.BLACK);

        // Centrar texto en las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        // Personalizar el encabezado
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(173, 216, 230)); // Azul claro
        header.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        return tabla;
    }


    private JPanel crearGraficoEnvios() {
        JPanel panel = new JPanel(new GridLayout(7, 53, 2, 2)); // 52 semanas, 7 días
        panel.setBackground(Color.WHITE); // Fondo blanco

        for (int semana = 0; semana < 52; semana++) {
            for (int dia = 0; dia < 7; dia++) {
                JPanel celda = new JPanel();
                celda.setBackground(new Color(204, 229, 255)); // Azul muy claro
                celda.setBorder(new LineBorder(Color.LIGHT_GRAY));
                panel.add(celda);
            }
        }

        return panel;
    }

   



    private void mostrarLearningPathsDisponibles() {
        List<LearningPath> learningPaths = estudiante.getLearningPathsInscritos();
        if (learningPaths.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes Learning Paths inscritos.");
            return;
        }

        String[] opciones = learningPaths.stream().map(LearningPath::getTitulo).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona un Learning Path:",
                "Continuar", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            LearningPath lpSeleccionado = learningPaths.stream()
                    .filter(lp -> lp.getTitulo().equals(seleccion))
                    .findFirst()
                    .orElse(null);

            if (lpSeleccionado != null) {
                mostrarActividadesDisponibles(lpSeleccionado);
            }
        }
    }

    
    private void mostrarActividadesDisponibles(LearningPath learningPath) {
        List<Actividad> actividades = learningPath.getActividades();

        // Crear un modelo de lista para las actividades
        DefaultListModel<Actividad> modeloLista = new DefaultListModel<>();
        actividades.forEach(modeloLista::addElement);

        // Crear la lista de actividades con un renderer personalizado
        JList<Actividad> listaActividades = new JList<>(modeloLista);
        listaActividades.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getDescripcion());
            label.setOpaque(true);
            label.setBackground(value.isCompletada() ? new Color(173, 216, 230) : Color.WHITE); // Azul claro para completadas
            if (isSelected) {
                label.setBackground(new Color(135, 206, 250)); // Azul más intenso para selección
            }
            label.setBorder(new LineBorder(Color.LIGHT_GRAY));
            return label;
        });

        // Mostrar la lista de actividades en un diálogo
        int opcion = JOptionPane.showConfirmDialog(this, new JScrollPane(listaActividades),
                "Selecciona una actividad para realizar", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            Actividad actividadSeleccionada = listaActividades.getSelectedValue();
            realizarActividad(actividadSeleccionada);
        }
    }
    
    private void realizarActividad(Actividad actividad) {
        if (actividad == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad para realizarla.");
            return;
        }

        // Solicitar la fecha de envío
        LocalDate fechaEnvio = solicitarFechaEnvio();
        if (fechaEnvio != null) {
            // Marcar la actividad como completada y asignar la fecha de envío
            actividad.setCompletada(true);
            actividad.setFechaEnvio(fechaEnvio);

            // Registrar el envío en el gráfico
            enviosPorDia.put(fechaEnvio, enviosPorDia.getOrDefault(fechaEnvio, 0) + 1);

            // Actualizar la interfaz
            actualizarVista();

            JOptionPane.showMessageDialog(this, "Actividad completada: " + actividad.getDescripcion());
        }
    }

    private LocalDate solicitarFechaEnvio() {
        String fechaStr = JOptionPane.showInputDialog(this, "Ingrese la fecha de envío (YYYY-MM-DD):");
        try {
            return LocalDate.parse(fechaStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Por favor, ingrese un formato válido (YYYY-MM-DD).");
            return null;
        }
    }


    private void actualizarVista() {
        actualizarTablaProgreso();
        actualizarGraficoEnvios();
    }

    private void actualizarTablaProgreso() {
        remove(tablaProgreso);
        tablaProgreso = crearTablaProgreso();
        add(new JScrollPane(tablaProgreso), BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    private void actualizarGraficoEnvios() {
        remove(panelGrafico);
        panelGrafico = crearGraficoEnvios();

        enviosPorDia.forEach((fecha, cantidad) -> {
            int semana = fecha.getDayOfYear() / 7;
            int dia = fecha.getDayOfWeek().getValue() - 1;

            Component celda = panelGrafico.getComponent(semana * 7 + dia);
            if (cantidad > 1) {
                celda.setBackground(new Color(0, 100, 0)); // Verde oscuro
            } else {
                celda.setBackground(Color.GREEN); // Verde claro
            }
        });

        add(panelGrafico, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}

