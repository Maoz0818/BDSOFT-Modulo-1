
package Logica;


public class Productos {
    private int codigo;
    private int tipo;
    private String nombre;  
    private int precio; 
    private String marca;

    public Productos() {
    }

    public Productos(int codigo, int tipo, String nombre, int precio, String marca){
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
    }
    
    public int getCodigo() {
        return codigo;
    }
       
    public int getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
}
