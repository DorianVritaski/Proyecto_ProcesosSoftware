package modelo;

/**
 *
 * @author ACER
 */
public class Usuario {
    private int dni;
    private String nombre;
    private String apellidos;
    private String declaracionJurada;
    private String correo;
    private String celular;
    private String tipoUsuario;
    private String fechaRegistro;
    private String urlDecJurada;
    
     public Usuario(int dni, String nombre, String apellidos, String declaracionJurada, String correo, String celular, String tipoUsuario, String fechaRegistro, String urlDecJurada) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.declaracionJurada = declaracionJurada;
        this.correo = correo;
        this.celular = celular;
        this.tipoUsuario = tipoUsuario;
        this.fechaRegistro = fechaRegistro;
        this.urlDecJurada = urlDecJurada;
    }
     
    public Usuario() {
    // Constructor vacío necesario para instanciación sin argumentos
    }
    
    public int getDni() {
        return dni;
    }
    public void setDni(int dni){
        this.dni=dni;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre=nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos=apellidos;
    }
    
    public String getDeclaracionJurada() {
        return declaracionJurada;
    }
    public void setDeclaracionJurada(String declaracionJurada) {
        this.declaracionJurada=declaracionJurada;
    } 
    
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo=correo;
    }
    
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular=celular;
    }   
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario=tipoUsuario;
    }  
    
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro=fechaRegistro;
    }

    public String getUrlDecJurada() {
        return urlDecJurada;
    }
    public void setUrlDecJurada(String urlDecJurada) {
        this.urlDecJurada=urlDecJurada;
    }
}
