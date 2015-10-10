package Logica;

public class JefeSucursal extends Usuario{
    
    //Declaracion de variables
    private int codigoJefe;
    
    //Constructor vacio
    public JefeSucursal(){}
    
    //Constructor con atributos
    public JefeSucursal(int codigoJefe, int codigoUsuario, int estado, String nombres, String apellidos, int telefono, String e_mail, String contraseña) {
        super(codigoUsuario, estado, nombres, apellidos, telefono, e_mail, contraseña);
        this.codigoJefe = codigoJefe;
    }
    
    //Metodos get
    public int getCodigoJefe() {
        return codigoJefe;
    }
    
    //Metodos set
    public void setCodigoJefe(int codigoJefe) {
        this.codigoJefe = codigoJefe;
    }
   
}
