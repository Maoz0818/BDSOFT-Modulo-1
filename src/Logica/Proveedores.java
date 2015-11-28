
package Logica;


public class Proveedores {
    
    private String rut;  
    private String telefono;
    private String nombre;     
    private String ciudad;
    private String direccion;
    private String eMail;
    private String tipo;
    private Integer estado;
    
    public Proveedores() {
    }

    public Proveedores(String rut, String telefono, String nombre, String ciudad, String direccion, String eMail,String tipo, Integer estado) {
        this.rut = rut;
        this.telefono = telefono;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.eMail = eMail;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
     
}
