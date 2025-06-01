package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.tablaUsuarios;

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
                "¿Está seguro que desea eliminar este usuario?", 
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
                    if (!actualizado) actualizacionExitosa = false;

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

    

    private void buscarUsuario() {
        // Aquí puedes usar dao.buscarUsuarioPorDni(...) y actualizar la tabla con el resultado
        // Te ayudo con eso si lo necesitas
    }
    
    
}
