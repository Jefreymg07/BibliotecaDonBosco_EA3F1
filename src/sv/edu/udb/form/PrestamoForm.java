package sv.edu.udb.form;

import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class PrestamoForm extends javax.swing.JFrame {

    private final PrestamoDAO dao = new PrestamoDAO();

    public PrestamoForm() {
        initComponents();
        cargarUsuarios();
        cargarMateriales();
    }

    private void cargarUsuarios() {
        try {
            for (String u : dao.listarUsuarios()) {
                comboUsuarios.addItem(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void cargarMateriales() {
        try {
            for (String m : dao.listarMaterialesDisponibles()) {
                comboMateriales.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando materiales: " + e.getMessage());
        }
    }

    private void btnRegistrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            String usuarioSel = comboUsuarios.getSelectedItem().toString();
            String materialSel = comboMateriales.getSelectedItem().toString();

            int usuarioId = Integer.parseInt(usuarioSel.split("-")[0]);
            int materialId = Integer.parseInt(materialSel.split("-")[0]);

            LocalDate hoy = LocalDate.now();
            LocalDate limite = hoy.plusDays(7);

            boolean exito = dao.registrarPrestamo(usuarioId, materialId, hoy, limite);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Préstamo registrado.");
                comboMateriales.removeAllItems();
                cargarMateriales();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el préstamo.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
