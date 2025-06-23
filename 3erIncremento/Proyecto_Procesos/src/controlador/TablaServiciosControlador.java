package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DetalleServicio;
import modelo.DetalleServicioDAO;
import modelo.DetalleServicioVista;
import modelo.Servicio;
import modelo.ServicioDAO;
import vista.tablaServicios;

public class TablaServiciosControlador {

    private tablaServicios vista;
    private DetalleServicio dao;

    public TablaServiciosControlador(tablaServicios vista) {
        this.vista = vista;
        this.dao = new DetalleServicio();
        cargarServiciosEnCombo();

        this.vista.btnBuscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarServicioVistaPorDni();
            }
        });

        //agregando funcionalidad para eliminar
        this.vista.btnEliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarServicio();
            }

        });

        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosServicios();
            }
        });

        this.vista.btnBuscarServ.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTipoServicio();
            }
        });

        this.vista.btnBuscarFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorFecha();

            }
        });

        this.vista.tblServicios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // SOLO en doble clic
                    int fila = vista.tblServicios.rowAtPoint(e.getPoint());
                    int columna = vista.tblServicios.columnAtPoint(e.getPoint());

                    if (columna == 5 && fila != -1) {
                        Object valorCelda = vista.tblServicios.getValueAt(fila, columna);

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

    }

    //implementnado eliminarServicio();
    private void eliminarServicio() {
        int filaSeleccionada = vista.tblServicios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un servicio para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro que desea eliminar este servicio?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Suponiendo que la columna 0 contiene el idDetalleServ
            int idDetalleServ = Integer.parseInt(vista.tblServicios.getValueAt(filaSeleccionada, 0).toString());

            DetalleServicioDAO dao = new DetalleServicioDAO(); // Asegúrate de instanciarlo si no lo tienes arriba
            boolean eliminado = dao.eliminarServicioPorId(idDetalleServ); // <-- Método corregido

            if (eliminado) {
                JOptionPane.showMessageDialog(vista, "Servicio eliminado correctamente.");
                vista.cargarTablaServicios();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar el servicio.");
            }
        }
    }

    private void buscarServicioVistaPorDni() {
        String dniTexto = vista.txtBuscarDni.getText();
        if (dniTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un DNI para buscar.");
            return;
        }

        try {
            int dni = Integer.parseInt(dniTexto);
            DetalleServicioDAO dao = new DetalleServicioDAO();
            List<DetalleServicioVista> lista = dao.buscarServiciosVistaPorDni(dni); // nueva lista

            DefaultTableModel modelo = (DefaultTableModel) vista.tblServicios.getModel();
            modelo.setRowCount(0);

            if (!lista.isEmpty()) {
                for (DetalleServicioVista detalle : lista) {
                    Object[] fila = {
                        detalle.getIdDetalleServ(),
                        detalle.getDni(),
                        detalle.getNombre(),
                        detalle.getApellidos(),
                        detalle.getServicio(),
                        detalle.getUrlServicio(),
                        detalle.getFechaServicio()
                    };
                    modelo.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontraron servicios con ese DNI.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un DNI válido.");
        }
    }

    private void guardarCambiosServicios() {
        int fila = vista.tblServicios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un servicio para guardar cambios.");
            return;
        }

        try {
            int idDetalleServ = Integer.parseInt(vista.tblServicios.getValueAt(fila, 0).toString());
            int dni = Integer.parseInt(vista.tblServicios.getValueAt(fila, 1).toString());
            String nombre = vista.tblServicios.getValueAt(fila, 2).toString();
            String apellidos = vista.tblServicios.getValueAt(fila, 3).toString();
            String nombreServicio = vista.tblServicios.getValueAt(fila, 4).toString();
            String url = vista.tblServicios.getValueAt(fila, 5).toString();
            String fecha = vista.tblServicios.getValueAt(fila, 6).toString();

            ServicioDAO servicioDAO = new ServicioDAO();
            int idServicio = servicioDAO.obtenerIdPorNombre(nombreServicio);
            if (idServicio == -1) {
                JOptionPane.showMessageDialog(vista, "Nombre del servicio no válido.");
                return;
            }

            // Crea un objeto o simplemente haz la actualización directa
            DetalleServicioDAO dao = new DetalleServicioDAO();
            boolean actualizado = dao.actualizarServicio(idDetalleServ, idServicio, url, fecha);

            if (actualizado) {
                JOptionPane.showMessageDialog(vista, "Servicio actualizado correctamente.");
                vista.cargarTablaServicios();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el servicio.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar los cambios: " + e.getMessage());
        }
    }

    private void cargarServiciosEnCombo() {
        ServicioDAO servicioDAO = new ServicioDAO();
        vista.cbxServ2.removeAllItems();

        for (Servicio s : servicioDAO.listarServicios()) {
            vista.cbxServ2.addItem(s.getTipoServicio());
        }

    }

    private void buscarTipoServicio() {
        String tipoServicio = (String) vista.cbxServ2.getSelectedItem();

        if (tipoServicio == null || tipoServicio.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de servicio para buscar.");
            return;
        }

        DetalleServicioDAO dao = new DetalleServicioDAO();
        List<DetalleServicioVista> lista = dao.buscarServiciosPorTipo(tipoServicio);

        DefaultTableModel modelo = (DefaultTableModel) vista.tblServicios.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        if (!lista.isEmpty()) {
            for (DetalleServicioVista d : lista) {
                Object[] fila = {
                    d.getIdDetalleServ(),
                    d.getDni(),
                    d.getNombre(),
                    d.getApellidos(),
                    d.getServicio(),
                    d.getUrlServicio(),
                    d.getFechaServicio()
                };
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontraron servicios de ese tipo.");
        }
    }

    private void buscarPorFecha() {
        java.util.Date utilDesde = vista.dateDesde.getDate();
        java.util.Date utilHasta = vista.dateHasta.getDate();

        if (utilDesde == null || utilHasta == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione ambas fechas para buscar.");
            return;
        }

        java.sql.Date sqlDesde = new java.sql.Date(utilDesde.getTime());
        java.sql.Date sqlHasta = new java.sql.Date(utilHasta.getTime());

        DetalleServicioDAO dao = new DetalleServicioDAO();
        List<DetalleServicioVista> lista = dao.buscarServiciosPorFechas(sqlDesde, sqlHasta);

        DefaultTableModel modelo = (DefaultTableModel) vista.tblServicios.getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        if (!lista.isEmpty()) {
            for (DetalleServicioVista d : lista) {
                Object[] fila = {
                    d.getIdDetalleServ(),
                    d.getDni(),
                    d.getNombre(),
                    d.getApellidos(),
                    d.getServicio(),
                    d.getUrlServicio(),
                    d.getFechaServicio()
                };
                modelo.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontraron servicios en ese rango de fechas.");
        }
    }

}
