package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.DetalleServicio;
import modelo.UsuarioDAO;
import vista.FrmRegistroUsuario;
import modelo.Usuario;
import modelo.DetalleServicioDAO;
import modelo.Servicio;
import modelo.ServicioDAO;




public class UsuarioControlador {
    private FrmRegistroUsuario vista;
    private UsuarioDAO dao;
    //private DetalleServicioDAO dao;
    //private DetalleServicioDAO detalleDao;
    
    
    public UsuarioControlador(FrmRegistroUsuario vista){
        this.vista = vista;
        this.dao = new UsuarioDAO();
        //this.dao = new DetalleServicioDAO();
        cargarServiciosEnCombo();
        //this.detalleDao = new DetalleServicioDAO(conexion);
        /*
        this.vista.btnRegistrar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
                registrarUsuario();
            }
        });
        */
        
        this.vista.btnRegistrar.addActionListener(new ActionListener() {
    
            @Override
            public void actionPerformed(ActionEvent e){
                int pestañaSeleccionada = vista.tabRegUsuarios.getSelectedIndex();
                
                if(pestañaSeleccionada == 0){
                    //pestaña0: registro de usuarios
                    registrarUsuario();
                } else if(pestañaSeleccionada == 1) {
                    //pestaña 1: Registro de servicios
                    registrarServicio();
                }
                
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
        
        this.vista.btnVerServicios.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Click detectado");
                vista.tablaServicios vistaTabla2 = new vista.tablaServicios();
                new controlador.TablaServiciosControlador(vistaTabla2);
                vistaTabla2.setVisible(true);
                vistaTabla2.setLocationRelativeTo(null);
            }
        
        });
        
    }
    
    private void registrarUsuario() {
        try {
            String dniTexto = vista.txtDni.getText().trim();

            // Validar que el DNI tenga exactamente 8 dígitos
            if (!dniTexto.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(vista, "El DNI debe tener exactamente 8 dígitos numéricos.");
                return;
            }

            int dni = Integer.parseInt(dniTexto);

            // Verificar si el DNI ya existe
            if (dao.existeDni(dni)) {
                JOptionPane.showMessageDialog(vista, "El DNI ingresado ya está registrado.");
                return;
            }
            
            //Verificar que el nombre solo contenga letras y espacios
            String nombre = vista.txtNombre.getText().trim();
            
            if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
                JOptionPane.showMessageDialog(vista, "El nombre solo debe contener letras");
                return;
            }
            
            //Verificar que el apellido solo contenga letras y espacios
            String apellido = vista.txtApellidos.getText().trim();
            
            if(!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
                JOptionPane.showMessageDialog(vista, "El apellido solo debe contener letras");
            }
            
            // Continuar con el registro si todo está bien
            //String nombre = vista.txtNombre.getText();
            String apellidos = vista.txtApellidos.getText();
            String correo = vista.txtCorreo.getText();
            String celular = vista.txtCelular.getText();
            String tipoUsuario = vista.cbxTipo.getSelectedItem().toString();
            String fecha = vista.txtFecha.getText();
            String urlDecJurada = vista.txtUrlDecJurada.getText();
            String declaracionJurada = vista.rdSi.isSelected() ? "S" : "N";

            Usuario u = new Usuario(dni, nombre, apellidos, declaracionJurada, correo, celular, tipoUsuario, fecha, urlDecJurada);
            if (dao.registrarUsuario(u)) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado con éxito");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar usuario");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "DNI inválido. Solo se permiten números.");
        }
    }
    
    private void registrarServicio(){
        try{
            String dniTexto = vista.txtDniServicios.getText().trim();
            
            //validar que el dni tengo 8 digitos
            if(!dniTexto.matches("\\d{8}")){
                JOptionPane.showMessageDialog(vista, "El DNI debe tener exactamente 8 digitos.");
                return;
            
            }
            
            int dni = Integer.parseInt(dniTexto);
            
            //verificar si el usuario existe
            if(!dao.existeDni(dni)){
                JOptionPane.showMessageDialog(vista, "El DNI ingresado no esta regsitrado como usuario.");
                return;
            }
            
            int idServicio = vista.cbxServ.getSelectedIndex() + 1;
            String urlServicio = vista.txtUrlServicio.getText().trim();
            String fechaServicio = vista.txtFechaServ.getText().trim();
            
            if(fechaServicio.isEmpty()){
                JOptionPane.showMessageDialog(vista, "Complete el campo Fecha");
            }
            
            DetalleServicio detalle = new DetalleServicio(dni,idServicio, urlServicio, fechaServicio);
            
            DetalleServicioDAO detalleDao = new DetalleServicioDAO();
            if(detalleDao.registrarDetalleServicio(detalle)){
                JOptionPane.showMessageDialog(vista, "Servicio registrado con éxito");
            }else{
                JOptionPane.showMessageDialog(vista, "Error al registrar el servicio");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    
    }
    
    private void cargarServiciosEnCombo() {
        ServicioDAO servicioDAO = new ServicioDAO();
        vista.cbxServ.removeAllItems(); // Limpia el combo

        for (Servicio s : servicioDAO.listarServicios()) {
            vista.cbxServ.addItem(s.getTipoServicio()); // Asegúrate de que Servicio tenga getTipoServicio()
        }
    }
    

}
