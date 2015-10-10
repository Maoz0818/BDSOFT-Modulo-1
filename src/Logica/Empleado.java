package Logica;

public class Empleado extends Usuario{
    
    //Declaracion de variables
    private int codigoEmpleado;
    private String cargo;
    
    //Constructor vacio
    public Empleado(){}
    
    //Constructor con atributos
    public Empleado(int codigoEmpleado, String cargo, int codigoUsuario, int estado, String nombres, String apellidos, int telefono, String e_mail, String contraseña) {
        super(codigoUsuario, estado, nombres, apellidos, telefono, e_mail, contraseña);
        this.codigoEmpleado = codigoEmpleado;
        this.cargo = cargo;
    }
    
    //Metodos get
    public int getcodigoEmpleado() {
        return codigoEmpleado;
    }

    public String getCargo() {
        return cargo;
    }
    
    //Metodos get
    public void setcodigoEmpleado(int empleadoid) {
        this.codigoEmpleado = empleadoid;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}
