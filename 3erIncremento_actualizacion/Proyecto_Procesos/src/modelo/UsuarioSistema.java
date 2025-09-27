package modelo;

public class UsuarioSistema {

    private String usuario;
    private byte[] contrasenaBytes; // ahora se guarda como hash en bytes
    private String rol;
    private int estado;
    private java.util.Date fechaCreacion;

    public UsuarioSistema() {
    }

    public UsuarioSistema(String usuario, byte[] contrasenaBytes, String rol, int estado, java.util.Date fechaCreacion) {
        this.usuario = usuario;
        this.contrasenaBytes = contrasenaBytes;
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public byte[] getContrasenaBytes() {
        return contrasenaBytes;
    }

    public void setContrasenaBytes(byte[] contrasenaBytes) {
        this.contrasenaBytes = contrasenaBytes;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public java.util.Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.util.Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
