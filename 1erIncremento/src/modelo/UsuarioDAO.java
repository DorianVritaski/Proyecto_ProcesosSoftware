package modelo;

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
        String sql = "SELECT * FROM usuarios";

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
    
    public boolean actualizarUsuario(Usuario u){
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, declaracion_jurada = ?, correo = ?, celular = ?, tipo_usuario = ?, fecha_registro = ?, url_DecJurada = ? WHERE dni = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getDeclaracionJurada());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getCelular());
            ps.setString(6, u.getTipoUsuario());
            ps.setString(7, u.getFechaRegistro());
            ps.setString(8, u.getUrlDecJurada());
            ps.setInt(9, u.getDni());
            
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Error al actualizar usuario: "+ e.getMessage());
            return false;
        }
    
    }
    
    public boolean eliminarUsuario(int dni){
        String sql = "DELETE FROM usuarios where dni = ?";
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
        
            ps.setInt(1, dni);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas >0;
        }catch(SQLException e){
            System.out.println("Error al eliminar usuario: "+e.getMessage());
            return false;
        }
    }

}
