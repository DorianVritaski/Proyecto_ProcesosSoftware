package controlador;

import modelo.Conexion;
import modelo.UsuarioSistema;
import modelo.UsuarioSistemaDAO;
import vista.FrmLogin;
import vista.FrmMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginControlador implements ActionListener {

    private FrmLogin vista;
    private UsuarioSistemaDAO usuarioDAO;

    public LoginControlador(FrmLogin vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioSistemaDAO();

        // Escuchar el botón de login
        this.vista.btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLogin) {
            login();
        }
    }

    private void login() {
        String usuario = vista.txtUsuario.getText().trim();
        String password = new String(vista.txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar usuario y contraseña", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hashear la contraseña si estás usando hash en la BD
        // String passwordHash = hashSHA256(password);

        // Validar usuario en BD
        UsuarioSistema usuarioSistema = usuarioDAO.login(usuario, password); // o passwordHash si usas hash

        if (usuarioSistema != null) {
            if (usuarioSistema.getEstado() == 1) {
                JOptionPane.showMessageDialog(vista, "Bienvenido " + usuarioSistema.getUsuario(), "Login correcto", JOptionPane.INFORMATION_MESSAGE);

                FrmMenu menu = new FrmMenu();
                new MenuControlador(menu); // ← Conecta el controlador del menú
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);

                vista.dispose(); // cerrar ventana de login
            } else {
                JOptionPane.showMessageDialog(vista, "Usuario inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase(); // importante: coincide con formato de SQL Server
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}