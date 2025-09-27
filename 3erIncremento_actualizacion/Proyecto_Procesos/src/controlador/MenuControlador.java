package controlador;

import vista.FrmMenu;
import vista.FrmRegistroUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmAgregarUsuarioSis;

public class MenuControlador {
    private FrmMenu vista;

    public MenuControlador(FrmMenu vista) {
        this.vista = vista;

        this.vista.btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmRegistroUsuario vistaRegistro = new FrmRegistroUsuario();
                new UsuarioControlador(vistaRegistro);
                vistaRegistro.setLocationRelativeTo(null);
                vistaRegistro.setVisible(true);
            }
        });
        
        this.vista.btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                FrmAgregarUsuarioSis vistaAddUser = new FrmAgregarUsuarioSis();
                new UsuarioSistemaControlador(vistaAddUser);
                vistaAddUser.setLocationRelativeTo(null);
                vistaAddUser.setVisible(true);
            }
        
        });
        
        
    }
}