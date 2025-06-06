package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DetalleServicio;
import modelo.DetalleServicioDAO;
import modelo.DetalleServicioVista;
import modelo.ServicioDAO;
import vista.tablaServicios;


public class TablaServiciosControlador {
    private tablaServicios vista;
    private DetalleServicio dao;
    
    public TablaServiciosControlador(tablaServicios vista){
        this.vista = vista;
        this.dao = new DetalleServicio();
        
        this.vista.btnBuscar.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                buscarServicioVistaPorDni();
            }
        });
        
        //agregando funcionalidad para eliminar
        this.vista.btnEliminar.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                eliminarServicio();
            }
            
        });
        
        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosServicios();
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

}
