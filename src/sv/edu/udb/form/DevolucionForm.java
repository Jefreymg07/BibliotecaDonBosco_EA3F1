package sv.edu.udb.form;

import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DevolucionForm extends javax.swing.JFrame {

    private final PrestamoDAO dao = new PrestamoDAO();

    public DevolucionForm() {
        initComponents();
        cargarPrestamosActivos();
    }

    private void cargarPrestamosActivos() {
        try {
            var lista = dao.listarPrestamosActivos();
            String[] columnas = {"ID", "Usuario", "Material", "Fecha Prestamo", "Fecha Limite"};
            Object[][] datos = new Object[lista.size()][5];

            for (int i = 0; i < lista.size(); i++) {
                Prestamo p = lista.get(i);
                datos[i][0] = p.getId();
                datos[i][1] = p.getUsuarioNombre();
                datos[i][2] = p.getMaterialTitulo();
                datos[i][3] = p.getFechaPrestamo().toString();
                datos[i][4] = p.getFechaLimite().toString();
            }

            tablaPrestamos.setModel(new DefaultTableModel(datos, columnas));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando préstamos: " + e.getMessage());
        }
    }

    private void btnRegistrarDevolucionActionPerformed(java.awt.event.ActionEvent evt) {

        int fila = tablaPrestamos.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un préstamo.");
            return;
        }

        try {
            int prestamoId = Integer.parseInt(tablaPrestamos.getValueAt(fila, 0).toString());

            boolean exito = dao.registrarDevolucion(prestamoId, LocalDate.now());

            if (exito) {
                JOptionPane.showMessageDialog(this, "Devolución registrada.");
                cargarPrestamosActivos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la devolución.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}

