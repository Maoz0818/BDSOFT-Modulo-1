package Logica;

public class Sucursal {
    
    //Declaracion de variables
    private int codigoSucursal;
    private String nombre;
    private String ciudad;
    private String direccion;
    
    //Constructor vacio
    public Sucursal(){}
    
    //Constructor con atributos
    public Sucursal(int codigoSucursal, String nombre, String ciudad, String direccion) {
        this.codigoSucursal = codigoSucursal;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    
    //Metodos get
    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }
    
    //Metodos set
    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
