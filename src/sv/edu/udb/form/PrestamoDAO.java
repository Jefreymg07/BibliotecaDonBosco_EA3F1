package sv.edu.udb.form;

import sv.edu.udb.conexion.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private final Conexion conexion = new Conexion();

    public boolean registrarPrestamo(int usuarioId, int materialId, LocalDate fechaPrestamo, LocalDate fechaLimite) throws SQLException {

        String sqlMora = "SELECT mora FROM prestamos WHERE usuario_id = ? AND mora > 0 AND fecha_devolucion IS NULL";
        PreparedStatement psMora = conexion.obtenerConexion().prepareStatement(sqlMora);
        psMora.setInt(1, usuarioId);
        ResultSet rsMora = psMora.executeQuery();
        if (rsMora.next()) return false;

        String sqlDisp = "SELECT disponibles FROM materiales WHERE id = ?";
        PreparedStatement psDisp = conexion.obtenerConexion().prepareStatement(sqlDisp);
        psDisp.setInt(1, materialId);
        ResultSet rsDisp = psDisp.executeQuery();
        if (rsDisp.next() && rsDisp.getInt("disponibles") <= 0) return false;

        String sqlInsert = "INSERT INTO prestamos(usuario_id, material_id, fecha_prestamo, fecha_limite) VALUES (?, ?, ?, ?)";
        PreparedStatement psInsert = conexion.obtenerConexion().prepareStatement(sqlInsert);
        psInsert.setInt(1, usuarioId);
        psInsert.setInt(2, materialId);
        psInsert.setDate(3, Date.valueOf(fechaPrestamo));
        psInsert.setDate(4, Date.valueOf(fechaLimite));
        psInsert.executeUpdate();

        String sqlUpdate = "UPDATE materiales SET disponibles = disponibles - 1 WHERE id = ?";
        PreparedStatement psUpdate = conexion.obtenerConexion().prepareStatement(sqlUpdate);
        psUpdate.setInt(1, materialId);
        psUpdate.executeUpdate();

        return true;
    }

    public boolean registrarDevolucion(int prestamoId, LocalDate fechaDevolucion) throws SQLException {

        String sqlFechaLimite = "SELECT fecha_limite FROM prestamos WHERE id = ?";
        PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sqlFechaLimite);
        ps.setInt(1, prestamoId);
        ResultSet rs = ps.executeQuery();

        double mora = 0;

        if (rs.next()) {
            LocalDate fechaLimite = rs.getDate("fecha_limite").toLocalDate();
            long atraso = java.time.temporal.ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
            if (atraso > 0) mora = atraso * 0.25;
        }

        String sqlUpdate = "UPDATE prestamos SET fecha_devolucion=?, mora=? WHERE id=?";
        PreparedStatement psUpdate = conexion.obtenerConexion().prepareStatement(sqlUpdate);
        psUpdate.setDate(1, Date.valueOf(fechaDevolucion));
        psUpdate.setDouble(2, mora);
        psUpdate.setInt(3, prestamoId);
        psUpdate.executeUpdate();

        String sqlMaterial = "UPDATE materiales SET disponibles = disponibles + 1 WHERE id = (SELECT material_id FROM prestamos WHERE id = ?)";
        PreparedStatement psMat = conexion.obtenerConexion().prepareStatement(sqlMaterial);
        psMat.setInt(1, prestamoId);
        psMat.executeUpdate();

        return true;
    }

    public List<Prestamo> listarPrestamosActivos() throws SQLException {

        String sql = "SELECT p.id, u.usuario, m.titulo, p.fecha_prestamo, p.fecha_limite " +
                     "FROM prestamos p " +
                     "JOIN usuarios u ON p.usuario_id = u.id " +
                     "JOIN materiales m ON p.material_id = m.id " +
                     "WHERE p.fecha_devolucion IS NULL";

        PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Prestamo> lista = new ArrayList<>();

        while (rs.next()) {
            Prestamo p = new Prestamo();
            p.setId(rs.getInt("id"));
            p.setUsuarioNombre(rs.getString("usuario"));
            p.setMaterialTitulo(rs.getString("titulo"));
            p.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
            p.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
            lista.add(p);
        }

        return lista;
    }

    public List<String> listarUsuarios() throws SQLException {
        String sql = "SELECT id, usuario FROM usuarios";
        PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<String> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(rs.getInt("id") + "-" + rs.getString("usuario"));
        }
        return lista;
    }

    public List<String> listarMaterialesDisponibles() throws SQLException {
        String sql = "SELECT id, titulo FROM materiales WHERE disponibles > 0";
        PreparedStatement ps = conexion.obtenerConexion().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<String> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(rs.getInt("id") + "-" + rs.getString("titulo"));
        }
        return lista;
    }
}

