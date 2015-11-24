
package Logica;

public class Existencias extends Productos{
    private int cantidad;      
    private Integer ubicacion;      
    private Integer proveedor;

    public Existencias() {
    }

    public Existencias(int codigo, int cantidad, Integer ubicacion, Integer proveedor, int tipo, String nombre, int precio, String marca) {
        super(codigo, tipo, nombre, precio, marca);
        this.cantidad = cantidad;
        this.ubicacion = ubicacion;
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public Integer getProveedor() {
        return proveedor;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setProveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }
    
}
