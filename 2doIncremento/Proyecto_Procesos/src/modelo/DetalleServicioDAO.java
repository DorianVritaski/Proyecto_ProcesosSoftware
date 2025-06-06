package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Object[]> listarServicios() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT ds.idDetalleServ, u.dni, u.nombre, u.apellidos, s.tipo_serv AS servicio, ds.url_servicio, ds.fecha_serv " +
                 "FROM detalle_serv ds " +
                 "INNER JOIN usuarios u ON ds.dniFK = u.dni " +
                 "INNER JOIN servicios s ON ds.idServicioFK = s.idServicio";

    try (Connection con = Conexion.getConexion(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Object[] fila = {
                rs.getInt("idDetalleServ"),
                rs.getInt("dni"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("servicio"),
                rs.getString("url_servicio"),
                rs.getString("fecha_serv")
            };
            lista.add(fila);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar servicios: " + e.getMessage());
    }

    return lista;
}

    
    public List<DetalleServicioVista> buscarServiciosVistaPorDni(int dni){
    List<DetalleServicioVista> lista = new ArrayList<>();
    String sql = "SELECT ds.idDetalleServ, u.dni, u.nombre, u.apellidos, s.tipo_serv AS servicio, ds.url_servicio, ds.fecha_serv " +
             "FROM detalle_serv ds " +
             "INNER JOIN usuarios u ON ds.dniFK = u.dni " +
             "INNER JOIN servicios s ON ds.idServicioFK = s.idServicio " +
             "WHERE ds.dniFK = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                DetalleServicioVista detalle = new DetalleServicioVista(
                    
                    rs.getInt("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("servicio"), 
                    rs.getString("url_servicio"),
                    rs.getString("fecha_serv"),
                    rs.getInt("idDetalleServ")
                );
                lista.add(detalle);
            }

        } catch(SQLException e){
            System.out.println("Error al buscar los servicios por DNI: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean eliminarServicioPorId(int idDetalleServ){
        String sql = "DELETE FROM detalle_serv WHERE idDetalleServ = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalleServ);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch(SQLException e){
            System.out.println("Error al eliminar servicio: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarServicio(int idDetalleServ, int idServicioFK, String url, String fecha) {
    String sql = "UPDATE detalle_serv SET idServicioFK = ?, url_servicio = ?, fecha_serv = ? WHERE idDetalleServ = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idServicioFK);
        ps.setString(2, url);
        ps.setString(3, fecha);
        ps.setInt(4, idDetalleServ);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al actualizar servicio: " + e.getMessage());
        return false;
    }
}



   
}
