package sv.edu.udb.form;

import sv.edu.udb.conexion.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private final Conexion con;

    public PrestamoDAO() {
        con = new Conexion();
    }

    public List<String> listarUsuarios() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT id, usuario FROM usuarios";
        ResultSet rs = con.ejecutarConsulta(sql);
        while (rs.next()) {
            lista.add(rs.getInt("id") + "-" + rs.getString("usuario"));
        }
        return lista;
    }

    public List<String> listarMaterialesDisponibles() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT id, titulo FROM materiales WHERE disponibles > 0";
        ResultSet rs = con.ejecutarConsulta(sql);
        while (rs.next()) {
            lista.add(rs.getInt("id") + "-" + rs.getString("titulo"));
        }
        return lista;
    }

    public boolean registrarPrestamo(int usuarioId, int materialId, LocalDate fechaPrestamo, LocalDate fechaLimite) throws SQLException {
        String validarMora = "SELECT mora FROM prestamos WHERE usuario_id=" + usuarioId + " AND mora > 0 AND fecha_devolucion IS NULL";
        ResultSet rs = con.ejecutarConsulta(validarMora);
        if (rs.next()) return false;

        String sql = "INSERT INTO prestamos(usuario_id, material_id, fecha_prestamo, fecha_limite, mora) VALUES("
                + usuarioId + ","
                + materialId + ",'"
                + fechaPrestamo + "','"
                + fechaLimite + "',0)";
        boolean ok = con.ejecutarActualizacion(sql);

        if (ok) {
            String actualizar = "UPDATE materiales SET disponibles = disponibles - 1 WHERE id=" + materialId;
            con.ejecutarActualizacion(actualizar);
        }

        return ok;
    }

    public List<Prestamo> listarPrestamosActivos() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id, u.usuario, m.titulo, p.fecha_prestamo, p.fecha_limite "
                + "FROM prestamos p INNER JOIN usuarios u ON p.usuario_id=u.id "
                + "INNER JOIN materiales m ON p.material_id=m.id "
                + "WHERE p.fecha_devolucion IS NULL";
        ResultSet rs = con.ejecutarConsulta(sql);

        while (rs.next()) {
            Prestamo p = new Prestamo();
            p.setId(rs.getInt(1));
            p.setUsuarioNombre(rs.getString(2));
            p.setMaterialTitulo(rs.getString(3));
            p.setFechaPrestamo(LocalDate.parse(rs.getString(4)));
            p.setFechaLimite(LocalDate.parse(rs.getString(5)));
            lista.add(p);
        }
        return lista;
    }

    public boolean registrarDevolucion(int prestamoId, LocalDate fechaDev) throws SQLException {
        String sql = "SELECT material_id, fecha_limite FROM prestamos WHERE id=" + prestamoId;
        ResultSet rs = con.ejecutarConsulta(sql);
        if (!rs.next()) return false;

        int materialId = rs.getInt("material_id");
        LocalDate limite = LocalDate.parse(rs.getString("fecha_limite"));
        long mora = 0;
        if (fechaDev.isAfter(limite)) {
            mora = java.time.temporal.ChronoUnit.DAYS.between(limite, fechaDev);
        }

        String up = "UPDATE prestamos SET fecha_devolucion='" + fechaDev + "', mora=" + mora + " WHERE id=" + prestamoId;
        boolean ok = con.ejecutarActualizacion(up);

        if (ok) {
            String u2 = "UPDATE materiales SET disponibles = disponibles + 1 WHERE id=" + materialId;
            con.ejecutarActualizacion(u2);
        }

        return ok;
    }
}
