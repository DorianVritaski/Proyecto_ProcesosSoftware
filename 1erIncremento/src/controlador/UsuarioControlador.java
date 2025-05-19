package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.UsuarioDAO;
import vista.FrmRegistroUsuario;
import modelo.Usuario;




public class UsuarioControlador {
    private FrmRegistroUsuario vista;
    private UsuarioDAO dao;
    
    public UsuarioControlador(FrmRegistroUsuario vista){
        this.vista = vista;
        this.dao = new UsuarioDAO();
        
        this.vista.btnRegistrar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
                registrarUsuario();
            }
        });
        
        this.vista.btnSalir.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                vista.dispose();
            }
        });
        
        this.vista.btnVerUsuarios.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.tablaUsuarios vistaTabla = new vista.tablaUsuarios();
                new controlador.TablaUsuariosControlador(vistaTabla);
                vistaTabla.setVisible(true);
                vistaTabla.setLocationRelativeTo(null);
                
            }
        
        });   
        
    }
    
    private void registrarUsuario(){
        try{
            int dni = Integer.parseInt(vista.txtDni.getText());
            String nombre = vista.txtNombre.getText();
            String apellidos = vista.txtApellidos.getText();
            String correo = vista.txtCorreo.getText();
            String celular = vista.txtCelular.getText();
            String tipoUsuario = vista.cbxTipo.getSelectedItem().toString();
            String fecha = vista.txtFecha.getText();
            String urlDecJurada = vista.txtUrlDecJurada.getText();
            String declaracionJurada = vista.rdSi.isSelected() ? "S" : "N";
            

            Usuario u = new Usuario(dni, nombre, apellidos, declaracionJurada, correo, celular, tipoUsuario, fecha, urlDecJurada);
            if (dao.registrarUsuario(u)){
                JOptionPane.showMessageDialog(vista, "Usuario registrado con exito");
            }else{
                JOptionPane.showMessageDialog(vista, "Error al registar usuario");
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(vista, "DNI debe ser número entero");
        }
    }
}
