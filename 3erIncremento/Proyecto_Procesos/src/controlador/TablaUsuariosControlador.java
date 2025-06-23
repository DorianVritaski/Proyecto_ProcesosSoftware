package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.tablaUsuarios;
import java.text.SimpleDateFormat;

public class TablaUsuariosControlador {

    private tablaUsuarios vista;
    private UsuarioDAO dao;

    public TablaUsuariosControlador(tablaUsuarios vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();

        // Eventos
        this.vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosUsuarios();
            }
        });

        //agregando funcion de boton para filtrar usuarios por tipo
        this.vista.btnBuscarTipo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTipoUsuario();
            }
        });

        this.vista.btnBuscarFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorFechas();
            }
        });

        //agregando funcion de botnon para filtrar por declaracion jurada
        this.vista.btnBuscarDec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorDecJurada();
            }
        });

        this.vista.tblUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // SOLO en doble clic
                    int fila = vista.tblUsuarios.rowAtPoint(e.getPoint());
                    int columna = vista.tblUsuarios.columnAtPoint(e.getPoint());

                    if (columna == 8 && fila != -1) {
                        Object valorCelda = vista.tblUsuarios.getValueAt(fila, columna);

                        if (valorCelda != null) {
                            String url = valorCelda.toString().trim();

                            if (!url.isEmpty()) {
                                try {
                                    Desktop.getDesktop().browse(new URI(url));
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(vista, "No se pudo abrir la URL: " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        });

        this.vista.btnReporteUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteHTMLUsuarios(dao.listarUsuarios());
            }
        });

        /*
         this.vista.btnBuscar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         buscarUsuario();
         }
         });
         */
        // Agrega más botones aquí si los necesitas
        System.out.println("Controlador de tablaUsuarios inicializado.");
    }

    private void eliminarUsuario() {
        System.out.println("Se hizo clic en el botón Eliminar");
        int filaSeleccionada = vista.tblUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro que desea eliminar este usuario?. Esto tambien eliminará los servicios asociados si los tiene?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int dni = Integer.parseInt(vista.tblUsuarios.getValueAt(filaSeleccionada, 0).toString());

            boolean eliminado = dao.eliminarUsuario(dni);

            if (eliminado) {
                JOptionPane.showMessageDialog(vista, "Usuario eliminado correctamente.");
                vista.cargarTablaUsuarios(); // Asegúrate de tener este método en tu vista
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar el usuario.");
            }
        }

    }

    private void guardarCambiosUsuarios() {
        System.out.println("Nuevo valor en celda (0,1): " + vista.tblUsuarios.getValueAt(0, 1));
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de guardar los cambios realizados?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel modelo = (DefaultTableModel) vista.tblUsuarios.getModel();
            boolean actualizacionExitosa = true;

            for (int i = 0; i < modelo.getRowCount(); i++) {
                try {
                    int dni = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                    String nombre = modelo.getValueAt(i, 1).toString();
                    String apellidos = modelo.getValueAt(i, 2).toString();
                    String dj = modelo.getValueAt(i, 3).toString();
                    String correo = modelo.getValueAt(i, 4).toString();
                    String celular = modelo.getValueAt(i, 5).toString();
                    String tipoUsuario = modelo.getValueAt(i, 6).toString();
                    String fecha = modelo.getValueAt(i, 7).toString();
                    String url = modelo.getValueAt(i, 8).toString();

                    Usuario u = new Usuario(dni, nombre, apellidos, dj, correo, celular, tipoUsuario, fecha, url);
                    boolean actualizado = dao.guardarCambiosUsuarios(u);
                    if (!actualizado) {
                        actualizacionExitosa = false;
                    }

                } catch (Exception ex) {
                    actualizacionExitosa = false;
                    ex.printStackTrace();
                }
            }

            if (actualizacionExitosa) {
                JOptionPane.showMessageDialog(vista, "Cambios guardados correctamente.");
            } else {
                JOptionPane.showMessageDialog(vista, "Hubo errores al guardar algunos registros.");
            }

            vista.cargarTablaUsuarios(); // refrescar la tabla
        }
    }

    private void buscarTipoUsuario() {
        String tipoSeleccionado = vista.cbxTipoUsuario.getSelectedItem().toString();

        // Validar selección
        if (tipoSeleccionado.equals("Seleccione") || tipoSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de usuario válido.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> listaFiltrada = dao.buscarPorTipo(tipoSeleccionado);

        DefaultTableModel modelo = (DefaultTableModel) vista.tblUsuarios.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Usuario u : listaFiltrada) {
            Object[] fila = {
                u.getDni(),
                u.getNombre(),
                u.getApellidos(),
                u.getDeclaracionJurada(),
                u.getCorreo(),
                u.getCelular(),
                u.getTipoUsuario(),
                u.getFechaRegistro(),
                u.getUrlDecJurada()
            };
            modelo.addRow(fila);
        }
    }

    private void buscarPorFechas() {
        java.util.Date utilDesde = vista.dateDesde.getDate();
        java.util.Date utilHasta = vista.dateHasta.getDate();

        if (utilDesde == null || utilHasta == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione ambas fechas para buscar.");
            return;
        }

        // Convertimos las fechas a String en formato yyyy-MM-dd
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechaDesde = new java.sql.Date(utilDesde.getTime());
        java.sql.Date fechaHasta = new java.sql.Date(utilHasta.getTime());

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.buscarUsuariosPorFechas(fechaDesde, fechaHasta);

        DefaultTableModel modelo = (DefaultTableModel) vista.tblUsuarios.getModel();
        modelo.setRowCount(0);

        for (Usuario u : lista) {
            Object[] fila = {
                u.getDni(),
                u.getNombre(),
                u.getApellidos(),
                u.getDeclaracionJurada(),
                u.getCorreo(),
                u.getCelular(),
                u.getTipoUsuario(),
                u.getFechaRegistro(),
                u.getUrlDecJurada()
            };
            modelo.addRow(fila);
        }

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron usuarios en el rango de fechas.");
        }
    }

    private void buscarUsuario() {
        // Aquí puedes usar dao.buscarUsuarioPorDni(...) y actualizar la tabla con el resultado
        // Te ayudo con eso si lo necesitas
    }

    private void buscarPorDecJurada() {
        String seleccion = (String) vista.cbxDecJurada.getSelectedItem();

        if (seleccion == null || seleccion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione una opción (S o N) para buscar.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.buscarUsuariosPorDecJurada(seleccion);

        DefaultTableModel modelo = (DefaultTableModel) vista.tblUsuarios.getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        for (Usuario u : lista) {
            Object[] fila = {
                u.getDni(),
                u.getNombre(),
                u.getApellidos(),
                u.getDeclaracionJurada(),
                u.getCorreo(),
                u.getCelular(),
                u.getTipoUsuario(),
                u.getFechaRegistro(),
                u.getUrlDecJurada()
            };
            modelo.addRow(fila);
        }
    }

    public void generarReporteHTMLUsuarios(List<Usuario> listaUsuarios) {
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
            writer.write("<tr><th>DNI</th><th>Nombre</th><th>Apellidos</th><th>Correo</th></tr>");

            for (Usuario u : listaUsuarios) {
                writer.write("<tr>");
                writer.write("<td>" + u.getDni() + "</td>");
                writer.write("<td>" + u.getNombre() + "</td>");
                writer.write("<td>" + u.getApellidos() + "</td>");
                writer.write("<td>" + u.getCorreo() + "</td>");
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
            JOptionPane.showMessageDialog(vista, "Error al generar el reporte: " + e.getMessage());
        }
    }

}
