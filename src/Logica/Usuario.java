package Logica;

public class Usuario {
    //Declaracion de variables
    private int codigoUsuario;
    private String nombres;
    private String apellidos; 
    private int telefono; 
    private String e_mail;
    private int estado;
    private String clave;
    private String cargo;
    private final String cadena = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    // Constructor vacio
    public Usuario(){}
    
    //Constructor con atributos
    public Usuario(int estado, String nombres, String apellidos, String cargo, int telefono, String e_mail, String clave) {
        this.estado = estado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.e_mail = e_mail;
        this.cargo = cargo;
        this.clave = clave;
    }
    
    //Metodos get
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public int getEstado() {
        return estado;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getClave() {
        return clave;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    //Metodos set
    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
       
    public String GenerarClave(){
        
        int longitudCad = cadena.length(); 
        String claveGenerada = "";
        String caracter = "";
    
        for (int i = 0; i < 8; i++) {
        claveGenerada+=(cadena.charAt((int)(Math.random() * cadena.length())));
	}
    
        return claveGenerada;
   } 
}
