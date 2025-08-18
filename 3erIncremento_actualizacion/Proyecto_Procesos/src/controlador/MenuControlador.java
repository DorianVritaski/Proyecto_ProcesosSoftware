package controlador;

import vista.FrmMenu;
import vista.FrmRegistroUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    }
}