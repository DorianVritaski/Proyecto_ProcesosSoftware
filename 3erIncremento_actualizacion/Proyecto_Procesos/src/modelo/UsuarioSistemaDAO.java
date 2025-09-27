package modelo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioSistemaDAO {

    public UsuarioSistema login(String usuario, String contrasena) {
        UsuarioSistema usr = null;
        String sql = "SELECT usuario, contrasena, rol, estado "
                + "FROM usuarios_sistema "
                + "WHERE usuario = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] hashGuardado = rs.getBytes("contrasena");

                    // Convertir la contraseña a bytes usando US-ASCII
                    byte[] contrasenaBytes = contrasena.getBytes(StandardCharsets.UTF_8);
                    System.out.print("Bytes de la contraseña: ");
                    for (byte b : contrasenaBytes) {
                        System.out.printf("%02X ", b);
                    }
                    System.out.println();

                    // Generar el hash SHA-256
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hashIngresado = md.digest(contrasenaBytes);

                    // Mostrar el hash en HEX para verificar
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hashIngresado) {
                        sb.append(String.format("%02X", b));
                    }
                    System.out.println("Hash en HEX: " + sb.toString());

                    // Comparar hashes
                    if (Arrays.equals(hashGuardado, hashIngresado)) {
                        usr = new UsuarioSistema();
                        usr.setUsuario(rs.getString("usuario"));
                        usr.setContrasenaBytes(hashGuardado);
                        usr.setRol(rs.getString("rol"));
                        usr.setEstado(rs.getInt("estado"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usr;
    }

    public boolean existeUsuario(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios_sistema WHERE usuario = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registrarUsuarioSis(UsuarioSistema usuarioSis) {
        if (existeUsuario(usuarioSis.getUsuario())) {
            System.out.println("⚠️ El usuario ya existe: " + usuarioSis.getUsuario());
            return false;
        }

        String sql = "INSERT INTO usuarios_sistema (usuario, contrasena, rol, estado) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuarioSis.getUsuario());
            ps.setBytes(2, usuarioSis.getContrasenaBytes());
            ps.setString(3, usuarioSis.getRol());
            ps.setInt(4, usuarioSis.getEstado());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UsuarioSistema> listarUsuariosSis() {
        List<UsuarioSistema> lista = new ArrayList<>();
        String sql = "SELECT usuario, contrasena, rol, estado, fecha_creacion FROM usuarios_sistema";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioSistema usr = new UsuarioSistema();
                usr.setUsuario(rs.getString("usuario"));
                usr.setContrasenaBytes(rs.getBytes("contrasena"));
                usr.setRol(rs.getString("rol"));
                usr.setEstado(rs.getInt("estado"));
                usr.setFechaCreacion(rs.getTimestamp("fecha_creacion")); // ← importante

                lista.add(usr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarUsuarioSistema(UsuarioSistema usuarioSis) {
        String sql = "UPDATE usuarios_sistema SET contrasena = ?, rol = ?, estado = ? WHERE usuario = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBytes(1, usuarioSis.getContrasenaBytes());
            ps.setString(2, usuarioSis.getRol());
            ps.setInt(3, usuarioSis.getEstado());
            ps.setString(4, usuarioSis.getUsuario());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
