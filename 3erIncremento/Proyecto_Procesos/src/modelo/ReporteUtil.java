package modelo;

import java.io.*;
import java.awt.Desktop;
//import java.util.List;
//import org.apache.poi.ss.usermodel.Cell;
import javax.swing.JOptionPane;
//import org.apache.poi.ss.usermodel.Sheet; // ✔️ Esto es para Excel
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//--------------
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
//import modelo.Usuario;

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

    public static void generarReporteXLSUsuarios(List<Usuario> listaUsuarios) {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String rutaArchivo = "reporte_usuarios_" + fecha + ".xls";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<meta charset='UTF-8'>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
            writer.write("h2 { color: #333; text-align: center; }");
            writer.write("table { width: 100%; border-collapse: collapse; background-color: #fff; }");
            writer.write("th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }");
            writer.write("th { background-color: #007BFF; color: white; }");
            writer.write("tr:nth-child(even) { background-color: #f2f2f2; }");
            writer.write("</style>");
            writer.write("</head><body>");
            writer.write("<h2>Reporte de Usuarios</h2>");
            writer.write("<table>");
            writer.write("<tr><th>DNI</th><th>Nombre</th><th>Apellidos</th><th>Declaración Jurada</th><th>Correo</th><th>Celular</th><th>Tipo Usuario</th><th>Fecha Registro</th><th>URL Dec. Jurada</th></tr>");

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

            writer.write("</table></body></html>");
            writer.flush();

            // Abre el archivo en el navegador o Excel predeterminado
            File archivo = new File(rutaArchivo);
            Desktop.getDesktop().open(archivo);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte XLS: " + e.getMessage());
        }
    }

    public static void generarReporteHTMLServicios(List<Object[]> servicios) {
        String nombreArchivo = "reporte_servicios_" + new SimpleDateFormat("yyyyMMdd_HHmm").format(new java.util.Date()) + ".html";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("<html><head><meta charset='UTF-8'><title>Reporte Servicios</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial; }");
            writer.write("table { width: 100%; border-collapse: collapse; }");
            writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }");
            writer.write("th { background-color: #007BFF; color: white; }");
            writer.write("tr:nth-child(even) { background-color: #f2f2f2; }");
            writer.write("</style></head><body>");
            writer.write("<h2>Reporte de Servicios</h2>");
            writer.write("<table>");
            writer.write("<tr><th>ID</th><th>DNI</th><th>Nombre</th><th>Apellidos</th><th>Servicio</th><th>URL</th><th>Fecha</th></tr>");

            for (Object[] fila : servicios) {
                writer.write("<tr>");
                for (Object dato : fila) {
                    writer.write("<td>" + (dato != null ? dato.toString() : "") + "</td>");
                }
                writer.write("</tr>");
            }

            writer.write("</table></body></html>");
            writer.flush();

            Desktop.getDesktop().browse(new File(nombreArchivo).toURI());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar HTML: " + e.getMessage());
        }
    }
    
    public static void generarReporteExcelServicios(List<Object[]> listaServicios) {
    String fecha = new SimpleDateFormat("yyyyMMdd_HHmm").format(new java.util.Date());
    String rutaArchivo = "reporte_servicios_" + fecha + ".xls";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<meta charset='UTF-8'>");
        writer.write("<style>");
        writer.write("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }");
        writer.write("h2 { color: #333; text-align: center; }");
        writer.write("table { width: 100%; border-collapse: collapse; background-color: #fff; }");
        writer.write("th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }");
        writer.write("th { background-color: #007BFF; color: white; }");
        writer.write("tr:nth-child(even) { background-color: #f2f2f2; }");
        writer.write("</style>");
        writer.write("</head><body>");

        writer.write("<h2>Reporte de Servicios</h2>");
        writer.write("<table>");
        writer.write("<tr>");
        writer.write("<th>ID Detalle</th>");
        writer.write("<th>DNI</th>");
        writer.write("<th>Nombre</th>");
        writer.write("<th>Apellidos</th>");
        writer.write("<th>Servicio</th>");
        writer.write("<th>URL Servicio</th>");
        writer.write("<th>Fecha Servicio</th>");
        writer.write("</tr>");

        for (Object[] fila : listaServicios) {
            writer.write("<tr>");
            for (Object campo : fila) {
                writer.write("<td>" + (campo != null ? campo.toString() : "") + "</td>");
            }
            writer.write("</tr>");
        }

        writer.write("</table></body></html>");
        writer.flush();

        // Abre el archivo con el programa predeterminado (normalmente Excel)
        Desktop.getDesktop().open(new File(rutaArchivo));

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al generar el reporte XLS de servicios: " + e.getMessage());
    }
}


}
