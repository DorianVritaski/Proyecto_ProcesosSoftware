package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.UsuarioSistema;
import modelo.UsuarioSistemaDAO;
import vista.FrmAgregarUsuarioSis;

public class UsuarioSistemaControlador {

    private FrmAgregarUsuarioSis vista;
    private UsuarioSistemaDAO dao;

    public UsuarioSistemaControlador(FrmAgregarUsuarioSis vista) {
        this.vista = vista;
        cargarTablaUsuariosSis();

        this.vista.btnRegUserSis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuarioSis();

            }

        });

        this.vista.btnGuardarCam.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosUserSis();
            }
        });

    }

    private void registrarUsuarioSis() {
        String usuario = vista.txtUsuarioSis.getText().trim();
        String pass = new String(vista.txtPassUserSis.getPassword());
        String passCon = new String(vista.txtPassUserSisCon.getPassword());
        String rol = vista.cbxRol.getSelectedItem().toString();
        boolean activo = vista.jRadioButtonActivo.isSelected();

        // Validaciones básicas
        if (usuario.isEmpty() || pass.isEmpty() || passCon.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            return;
        }

        if (!pass.equals(passCon)) {
            javax.swing.JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden");
            return;
        }

        try {
            // Hashear la contraseña
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pass.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // Crear objeto UsuarioSistema
            java.util.Date fechaActual = new java.util.Date();
            UsuarioSistema usuarioSis = new UsuarioSistema(usuario, hash, rol, activo ? 1 : 0, fechaActual);

            // Insertar en BD
            dao = new UsuarioSistemaDAO();
            boolean exito = dao.registrarUsuarioSis(usuarioSis);

            if (exito) {
                javax.swing.JOptionPane.showMessageDialog(vista, "Usuario registrado correctamente");

                // Limpiar campos
                vista.txtUsuarioSis.setText("");
                vista.txtPassUserSis.setText("");
                vista.txtPassUserSisCon.setText("");
                vista.cbxRol.setSelectedIndex(0);
                vista.jRadioButtonActivo.setSelected(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(vista, "El usuario ya existe o hubo un error al registrar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(vista, "Error interno: " + e.getMessage());
        }
    }

    private void cargarTablaUsuariosSis() {
        UsuarioSistemaDAO dao = new UsuarioSistemaDAO();
        List<UsuarioSistema> lista = dao.listarUsuariosSis();

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) vista.jTableUsersSis.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (UsuarioSistema u : lista) {
            String usuario = u.getUsuario();
            String hashHex = bytesToHex(u.getContrasenaBytes());
            String rol = u.getRol();
            String estado = u.getEstado() == 1 ? "Activo" : "Inactivo";
            String fecha = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(u.getFechaCreacion());

            modelo.addRow(new Object[]{usuario, hashHex, rol, estado, fecha});
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private void guardarCambiosUserSis() {
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de guardar los cambios realizados?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) vista.jTableUsersSis.getModel();
        boolean actualizacionExitosa = true;

        UsuarioSistemaDAO dao = new UsuarioSistemaDAO();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                String usuario = modelo.getValueAt(i, 0).toString();
                String hashHex = modelo.getValueAt(i, 1).toString();
                String rol = modelo.getValueAt(i, 2).toString();
                String estadoStr = modelo.getValueAt(i, 3).toString();
                int estado = estadoStr.equalsIgnoreCase("Activo") ? 1 : 0;

                // Convertir hash HEX a byte[]
                byte[] hashBytes = hexStringToByteArray(hashHex);

                UsuarioSistema usuarioSis = new UsuarioSistema();
                usuarioSis.setUsuario(usuario);
                usuarioSis.setContrasenaBytes(hashBytes);
                usuarioSis.setRol(rol);
                usuarioSis.setEstado(estado);

                boolean actualizado = dao.actualizarUsuarioSistema(usuarioSis);
                if (!actualizado) {
                    actualizacionExitosa = false;
                }

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

        cargarTablaUsuariosSis(); // refrescar la tabla
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
