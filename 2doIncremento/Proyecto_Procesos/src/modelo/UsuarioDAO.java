package modelo;

import java.sql.CallableStatement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet; // También falta este

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UsuarioDAO {
    public boolean registrarUsuario(Usuario u) {
        String sql = "{CALL SP_A_Tabla_usuarios (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getDni());
            ps.setString(2,u.getNombre());
            ps.setString(3,u.getApellidos());
            ps.setString(4,u.getDeclaracionJurada());
            ps.setString(5,u.getCorreo());
            ps.setString(6,u.getCelular());
            ps.setString(7,u.getTipoUsuario());
            ps.setString(8,u.getFechaRegistro());
            ps.setString(9,u.getUrlDecJurada());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Error al registrar usuario: "+ e.getMessage());
            return false;
        }
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_usuarios()}";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
               
                u.setDni(rs.getInt("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidos(rs.getString("apellidos"));
                u.setDeclaracionJurada(rs.getString("declaracion_jurada"));
                u.setCorreo(rs.getString("correo"));
                u.setCelular(rs.getString("celular"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                u.setFechaRegistro(rs.getString("fecha_registro"));
                u.setUrlDecJurada(rs.getString("url_DecJurada"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }
    
    public Usuario buscarUsuarioPorDni(int dni) {
        String sql = "{CALL SP_C_Tabla_usuarios(?)}";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("declaracion_jurada"),
                    rs.getString("correo"),
                    rs.getString("celular"),
                    rs.getString("tipo_usuario"),
                    rs.getString("fecha_registro"),
                    rs.getString("url_DecJurada")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
    
public boolean guardarCambiosUsuarios(Usuario u) {
    String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    
    try (Connection con = Conexion.getConexion(); 
         CallableStatement cs = con.prepareCall(sql)) {

        cs.setInt(1, u.getDni());
        cs.setString(2, u.getNombre());
        cs.setString(3, u.getApellidos());
        cs.setString(4, u.getDeclaracionJurada());
        cs.setString(5, u.getCorreo());
        cs.setString(6, u.getCelular());
        cs.setString(7, u.getTipoUsuario());

        // Convertimos fecha de String a java.sql.Date si es necesario
        cs.setDate(8, java.sql.Date.valueOf(u.getFechaRegistro())); // asegúrate que sea formato yyyy-MM-dd

        cs.setString(9, u.getUrlDecJurada());

        cs.execute();
        return true;

    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario (SP): " + e.getMessage());
        return false;
    }
}

    
    public boolean eliminarUsuario(int dni) {
        String sql = "{CALL sp_eliminar_usuario(?)}";
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, dni);
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean existeDni(int dni){
        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)){
        
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1)>0;
            }
        
        }catch(SQLException e){
            System.out.println("Error al verificar existencia del DNI: " + e.getMessage());
        }
        return false;
    
    }

}
