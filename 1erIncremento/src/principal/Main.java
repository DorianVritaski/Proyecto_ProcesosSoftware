package principal;

import controlador.UsuarioControlador;
import vista.FrmRegistroUsuario;

/**
 *
 * @author ACER
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FrmRegistroUsuario vista = new FrmRegistroUsuario();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        new UsuarioControlador(vista);
    }
    
}