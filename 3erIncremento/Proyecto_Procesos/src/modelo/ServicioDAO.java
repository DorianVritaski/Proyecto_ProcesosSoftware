package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public List<Servicio> listarServicios() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idServicio");
                String tipo = rs.getString("tipo_serv");
                lista.add(new Servicio(id, tipo));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
        }

        return lista;
    }
    
    public int obtenerIdPorNombre(String nombreServicio) {
        String sql = "SELECT idServicio FROM servicios WHERE tipo_serv = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreServicio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("idServicio");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener ID del servicio: " + e.getMessage());
        }
        return -1; // Si no encuentra
    }

}
