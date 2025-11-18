package sv.edu.udb.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrestamoForm extends JFrame {

    private final PrestamoDAO dao = new PrestamoDAO();

    // Componentes usados en el código anterior
    private JComboBox<String> comboUsuarios;
    private JComboBox<String> comboMateriales;
    private JSpinner spDias;
    private JButton btnRegistrarPrestamo;

    public PrestamoForm() {
        initComponents();
        cargarUsuarios();
        cargarMateriales();
    }

    private void initComponents() {
        setTitle("Registrar Préstamo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 240);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(content);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Usuario:"));
        comboUsuarios = new JComboBox<>();
        comboUsuarios.setPreferredSize(new java.awt.Dimension(320,25));
        p1.add(comboUsuarios);
        centro.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Material:"));
        comboMateriales = new JComboBox<>();
        comboMateriales.setPreferredSize(new java.awt.Dimension(320,25));
        p2.add(comboMateriales);
        centro.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Días:"));
        spDias = new JSpinner(new SpinnerNumberModel(7, 1, 30, 1));
        p3.add(spDias);
        centro.add(p3);

        content.add(centro, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrarPrestamo = new JButton("Registrar Préstamo");
        btnRegistrarPrestamo.addActionListener(e -> btnRegistrarPrestamoActionPerformed());
        bottom.add(btnRegistrarPrestamo);
        content.add(bottom, BorderLayout.SOUTH);
    }

    private void cargarUsuarios() {
        comboUsuarios.removeAllItems();
        try {
            for (String u : dao.listarUsuarios()) comboUsuarios.addItem(u);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void cargarMateriales() {
        comboMateriales.removeAllItems();
        try {
            for (String m : dao.listarMaterialesDisponibles()) comboMateriales.addItem(m);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando materiales: " + e.getMessage());
        }
    }

    private void btnRegistrarPrestamoActionPerformed() {
        try {
            String usuarioSel = (String) comboUsuarios.getSelectedItem();
            String materialSel = (String) comboMateriales.getSelectedItem();
            if (usuarioSel == null || materialSel == null) {
                JOptionPane.showMessageDialog(this, "Seleccione usuario y material.");
                return;
            }
            int usuarioId = Integer.parseInt(usuarioSel.split("-")[0]);
            int materialId = Integer.parseInt(materialSel.split("-")[0]);
            LocalDate hoy = LocalDate.now();
            int dias = (int) spDias.getValue();
            LocalDate limite = hoy.plusDays(dias);
            boolean exito = dao.registrarPrestamo(usuarioId, materialId, hoy, limite);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Préstamo registrado.");
                cargarMateriales();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el préstamo (mora o sin disponibilidad).");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Para probar rápidamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrestamoForm().setVisible(true));
    }
}
