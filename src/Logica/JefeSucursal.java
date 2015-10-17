package Logica;

public class JefeSucursal extends Usuario{
    
    //Declaracion de variables
    private int codigoJefe;
    
    //Constructor vacio
    public JefeSucursal(){}
    
    //Constructor con atributos
    public JefeSucursal( int estado, String nombres, String apellidos, String cargo, String telefono, String e_mail,String clave) {
        super(estado, nombres, apellidos, cargo, telefono, e_mail, clave);
    }
    
    //Constructor solo con codigo de usuario
    
    //Metodos get
    public int getCodigoJefe() {
        return codigoJefe;
    }
    
    //Metodos set
    public void setCodigoJefe(int codigoJefe) {
        this.codigoJefe = codigoJefe;
    }
   
}
