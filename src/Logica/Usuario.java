package Logica;

public class Usuario {
    //Declaracion de variables
    private int codigoUsuario;
    private int estado;
    private String nombres;
    private String apellidos; 
    private int telefono; 
    private String e_mail;
    private String contraseña;
    
    // Constructor vacio
    public Usuario(){}
    
    //Constructor con atributos
    public Usuario(int codigoUsuario, int estado, String nombres, String apellidos, int telefono, String e_mail, String contraseña) {
        this.codigoUsuario = codigoUsuario;
        this.estado = estado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.e_mail = e_mail;
        this.contraseña = contraseña;
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

    public String getContraseña() {
        return contraseña;
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

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
}
