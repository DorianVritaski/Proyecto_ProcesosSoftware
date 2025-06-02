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
    
    public List<DetalleServicioVista> listarServicios(){
        List<DetalleServicioVista> lista = new ArrayList<>();
        String sql = "SELECT u.dni, u.nombre, u.apellidos, s.tipo_serv AS servicio, ds.url_servicio, ds.fecha_serv " +
                 "FROM detalle_serv ds " +
                 "INNER JOIN usuarios u ON ds.dniFK = u.dni " +
                 "INNER JOIN servicios s ON ds.idServicioFK = s.idServicio";
                
                
        try (Connection con = Conexion.getConexion(); 
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
        
            while(rs.next()){
                int dni = rs.getInt("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String servicio = rs.getString("servicio");
                String url = rs.getString("url_servicio");
                String fecha = rs.getString("fecha_serv");
                
                DetalleServicioVista detalle = new DetalleServicioVista(dni, nombre, apellidos, servicio, url, fecha);
                lista.add(detalle);
            }
        }catch(SQLException e){
                System.out.println("Error al listar los servicios: " + e.getMessage());
        }
        return lista;
    }
    
    public List<DetalleServicioVista> buscarServiciosVistaPorDni(int dni){
    List<DetalleServicioVista> lista = new ArrayList<>();
    String sql = "SELECT u.dni, u.nombre, u.apellidos, s.tipo_serv AS servicio, ds.url_servicio, ds.fecha_serv " +
                 "FROM detalle_serv ds " +
                 "INNER JOIN usuarios u ON ds.dniFK = u.dni " +
                 "INNER JOIN servicios s ON ds.idServicioFK = s.idServicio " + // <-- espacio al final
                 "WHERE ds.dniFK = ?"; // <-- alias corregido

    try (Connection con = Conexion.getConexion(); 
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, dni);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            DetalleServicioVista detalle = new DetalleServicioVista(
                rs.getInt("dni"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("servicio"), // <-- Usar alias "servicio"
                rs.getString("url_servicio"),
                rs.getString("fecha_serv")
            );
            lista.add(detalle);
        }

    } catch(SQLException e){
        System.out.println("Error al buscar los servicios por DNI: " + e.getMessage());
    }

    return lista;
}

   
}
