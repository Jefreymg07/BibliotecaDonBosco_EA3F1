package sv.edu.udb.form;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class DevolucionForm extends JFrame {

    private final PrestamoDAO dao = new PrestamoDAO();

    private JTable tablaPrestamos;
    private JButton btnRegistrarDevolucion;

    public DevolucionForm() {
        initComponents();
        cargarPrestamosActivos();
    }

    private void initComponents() {
        setTitle("Devoluciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(content);

        tablaPrestamos = new JTable();
        content.add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrarDevolucion = new JButton("Registrar Devolución");
        btnRegistrarDevolucion.addActionListener(e -> btnRegistrarDevolucionActionPerformed());
        bottom.add(btnRegistrarDevolucion);
        content.add(bottom, BorderLayout.SOUTH);
    }

    private void cargarPrestamosActivos() {
        try {
            List<Prestamo> lista = dao.listarPrestamosActivos();
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error cargando préstamos: " + ex.getMessage());
        }
    }

    private void btnRegistrarDevolucionActionPerformed() {
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DevolucionForm().setVisible(true));
    }
}
