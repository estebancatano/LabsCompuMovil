package co.edu.udea.compumovil.gr05.lab2apprun.model;

/**
 * Created by asus on 17/03/2016.
 */
public class Evento {
    private String nombre;
    private String descripcion;
    private String distancia;
    private String lugar;
    private String fecha;
    private String telefono;
    private String correo;
    private byte[] foto;

    public Evento() {
    }

    public Evento(String nombre, String distancia, String lugar, String fecha) {
        this.nombre = nombre;
        this.distancia = distancia;
        this.lugar = lugar;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
