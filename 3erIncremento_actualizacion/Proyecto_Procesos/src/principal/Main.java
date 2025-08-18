package principal;

import controlador.LoginControlador;
import vista.FrmLogin;

public class Main {
    public static void main(String[] args) {
        // Crear la vista de login
        FrmLogin vistaLogin = new FrmLogin();
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);

        // Crear el controlador de login
        new LoginControlador(vistaLogin);
    }
}
