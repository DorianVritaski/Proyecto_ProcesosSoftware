package modelo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

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
    
    
}
