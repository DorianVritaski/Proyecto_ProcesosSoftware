package modelo;


public class DetalleServicio {
    private int dniFK;
    private int idServicioFK;
    private String urlServicio;
    private String fechaServicio;
    
    public DetalleServicio(int dniFK, int idServicioFK, String urlServicio, String fechaServicio){
        this.dniFK = dniFK;
        this.idServicioFK = idServicioFK;
        this.urlServicio = urlServicio;
        this.fechaServicio = fechaServicio;
    
    }
    
    public DetalleServicio(){
    
    }
    
    public int getDniFK(){
        return dniFK;
    }
    
    public void setDniFK(int dniFK){
        this.dniFK = dniFK;
    }
    
    public int getIdServicioFK(){
        return idServicioFK;
    }
    
    public void setIdServicioFK(int idServicioFK){
        this.idServicioFK = idServicioFK;
    }
    
    public String getUrlServicio(){
        return urlServicio;
    }
    
    public void setUrlServicio(String urlServicio){
        this.urlServicio = urlServicio;
    }
    
    public String getFechaServicio(){
        return fechaServicio;
    }
    
    public void setFechaServicio(String fechaServicio){
        this.fechaServicio = fechaServicio;
    }
}
