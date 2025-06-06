package modelo;

public class DetalleServicioVista {
    
    private int dni;
    private String nombre;
    private String apellidos;
    private String servicio;
    private String urlServicio;
    private String fechaServicio;
    private int idDetalleServ;
    
    public DetalleServicioVista(int dni, String nombre, String apellidos, String servicio, String urlServicio, String fechaServicio, int idDetalleServ){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.servicio = servicio;
        this.urlServicio = urlServicio;
        this.fechaServicio = fechaServicio;
        this.idDetalleServ = idDetalleServ;
    }
    //getters
    public int getDni(){
        return dni;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public String getServicio(){
        return servicio;
    }
    
    public String getUrlServicio(){
        return urlServicio;
    }
    
    public String getFechaServicio(){
        return fechaServicio;
    }
    
    public int getIdDetalleServ(){
        return idDetalleServ;
    }
}
