package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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

    private void buscarUsuario() {
        // Aquí puedes usar dao.buscarUsuarioPorDni(...) y actualizar la tabla con el resultado
        // Te ayudo con eso si lo necesitas
    }
    
    
}
