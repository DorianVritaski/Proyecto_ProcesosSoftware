package modelo;

import java.io.*;
import java.awt.Desktop;
import java.util.List;
import javax.swing.JOptionPane;

public class ReporteUtil {

    public static void generarReporteHTMLUsuarios(List<Usuario> listaUsuarios) {
        String rutaArchivo = "reporte_usuarios.html";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<meta charset='UTF-8'>");
            writer.write("<title>Reporte de Usuarios</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
            writer.write("h2 { color: #333; text-align: center; }");
            writer.write("table { width: 100%; border-collapse: collapse; background-color: #fff; }");
            writer.write("th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }");
            writer.write("th { background-color: #007BFF; color: white; }");
            writer.write("tr:nth-child(even) { background-color: #f2f2f2; }");
            writer.write("tr:hover { background-color: #e9e9e9; }");
            writer.write("</style>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h2>Reporte de Usuarios</h2>");
            writer.write("<table>");
            writer.write("<tr>");
            writer.write("<th>DNI</th>");
            writer.write("<th>Nombre</th>");
            writer.write("<th>Apellidos</th>");
            writer.write("<th>Declaración Jurada</th>");
            writer.write("<th>Correo</th>");
            writer.write("<th>Celular</th>");
            writer.write("<th>Tipo Usuario</th>");
            writer.write("<th>Fecha Registro</th>");
            writer.write("<th>URL Dec. Jurada</th>");
            writer.write("</tr>");;

            for (Usuario u : listaUsuarios) {
                writer.write("<tr>");
                writer.write("<td>" + u.getDni() + "</td>");
                writer.write("<td>" + u.getNombre() + "</td>");
                writer.write("<td>" + u.getApellidos() + "</td>");
                writer.write("<td>" + u.getDeclaracionJurada() + "</td>");
                writer.write("<td>" + u.getCorreo() + "</td>");
                writer.write("<td>" + u.getCelular() + "</td>");
                writer.write("<td>" + u.getTipoUsuario() + "</td>");
                writer.write("<td>" + u.getFechaRegistro() + "</td>");
                writer.write("<td>" + u.getUrlDecJurada() + "</td>");
                writer.write("</tr>");
            }

            writer.write("</table>");
            writer.write("</body>");
            writer.write("</html>");
            writer.flush();

            // Abre el archivo HTML en el navegador predeterminado
            File archivo = new File(rutaArchivo);
            Desktop.getDesktop().browse(archivo.toURI());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }
}
