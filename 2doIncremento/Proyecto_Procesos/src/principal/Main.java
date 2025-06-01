package principal;

import controlador.UsuarioControlador;
import vista.FrmRegistroUsuario;
import vista.tablaUsuarios;

/**
 *
 * @author ACER
 */
public class Main {
    public static void main(String[] args) {
        FrmRegistroUsuario vistaRegistro = new FrmRegistroUsuario();
        vistaRegistro.setVisible(true);
        vistaRegistro.setLocationRelativeTo(null);
        new UsuarioControlador(vistaRegistro);
    }
}
