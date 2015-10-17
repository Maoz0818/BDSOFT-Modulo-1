package Logica;

public class Empleado extends Usuario{
    
    //Declaracion de variables
    private int codigoEmpleado;
        
    //Constructor vacio
    public Empleado(){}
    
    //Constructor con atributos
    public Empleado(int estado, String nombres, String apellidos, String cargo, int telefono, String e_mail, String clave) {
        super(estado, nombres, apellidos, cargo, telefono, e_mail, clave);        
    }
    
    //Metodos get
    public int getcodigoEmpleado() {
        return codigoEmpleado;
    }
    
    //Metodos get
    public void setcodigoEmpleado(int empleadoid) {
        this.codigoEmpleado = empleadoid;
    }
    
}
