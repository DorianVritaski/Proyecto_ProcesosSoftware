package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleServicioDAO {

    public boolean registrarDetalleServicio(DetalleServicio detalle) {
        String sql = "INSERT INTO detalle_serv(dniFK, idServicioFK, url_servicio, fecha_serv) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getDniFK());
            ps.setInt(2, detalle.getIdServicioFK());
            ps.setString(3, detalle.getUrlServicio());
            ps.setString(4, detalle.getFechaServicio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar detalle de servicio: " + e.getMessage());
            return false;
        }
    }
}
