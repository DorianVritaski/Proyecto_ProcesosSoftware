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

}
