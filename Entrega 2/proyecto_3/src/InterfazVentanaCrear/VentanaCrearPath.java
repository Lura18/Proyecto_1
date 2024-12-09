package InterfazVentanaCrear;

import javax.swing.*;

import InterfazVentanaPrincipalProfesor.PanelLearningPathsProfesor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Registro;

public class VentanaCrearPath extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTextField txtTitulo;
    private JTextArea txtDescripcion, txtObjetivos;
    private JComboBox<String> comboDificultad;
    private JTextField txtDuracion;
    
    public VentanaCrearPath(Profesor profesor, Registro sistema, JPanel panelLearningPaths) {
        setTitle("Crear Nuevo Learning Path");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Título de la ventana
        JLabel lblTitulo = new JLabel("Ingrese los detalles del nuevo Learning Path", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        lblTitulo.setForeground(Color.BLACK);
        add(lblTitulo, BorderLayout.NORTH);
        
        // Panel para los campos de texto
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(6, 2, 10, 10));
        
        panelForm.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelForm.add(txtTitulo);
        
        panelForm.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        JScrollPane descripcionScroll = new JScrollPane(txtDescripcion);
        panelForm.add(descripcionScroll);
        
        panelForm.add(new JLabel("Objetivos:"));
        txtObjetivos = new JTextArea(3, 20);
        JScrollPane objetivosScroll = new JScrollPane(txtObjetivos);
        panelForm.add(objetivosScroll);
        
        panelForm.add(new JLabel("Nivel de dificultad:"));
        comboDificultad = new JComboBox<>(new String[] {"Bajo", "Medio", "Alto"});
        panelForm.add(comboDificultad);
        
        panelForm.add(new JLabel("Duración estimada (horas):"));
        txtDuracion = new JTextField();
        panelForm.add(txtDuracion);
        
        add(panelForm, BorderLayout.CENTER);
        
        // Botones para guardar o cancelar
        JPanel panelBotones = new JPanel();
        JButton btnCrear = new JButton("Crear");
        JButton btnCancelar = new JButton("Cancelar");
        
        // Acción para el botón "Crear"
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = txtTitulo.getText();
                String descripcion = txtDescripcion.getText();
                String objetivos = txtObjetivos.getText();
                String dificultad = (String) comboDificultad.getSelectedItem();
                int duracion = 0;
                
                // Validar si los campos están vacíos
                if (titulo.isEmpty() || descripcion.isEmpty() || objetivos.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaCrearPath.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la creación si falta información
                }

                // Convertir la duración en un número entero
                try {
                    duracion = Integer.parseInt(txtDuracion.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VentanaCrearPath.this, "La duración debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Si la duración es negativa o 0, mostrar error
                if (duracion <= 0) {
                    JOptionPane.showMessageDialog(VentanaCrearPath.this, "La duración debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Si todo es válido, crear el Learning Path
                LearningPath nuevoLP = profesor.crearLearningPath(titulo, descripcion, objetivos, dificultad, duracion, sistema);
                JOptionPane.showMessageDialog(VentanaCrearPath.this, "Learning Path creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                ((PanelLearningPathsProfesor) panelLearningPaths).actualizarTabla(profesor); 
                
                dispose();
            }
        });
        
        // Acción para el botón "Cancelar"
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
}
