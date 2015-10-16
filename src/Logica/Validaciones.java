package Logica;

//Importaciones
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Clase que se encarga de realizar validaciones de campos
public class Validaciones {
    
    //Declaracion de variables
    private Pattern patron;
    private Matcher validar;
    
    //Constructor vacio
    public Validaciones () {}
    
    
    //Validacion de campos vacios en login
    public boolean validarVacios(String correo) {
        return (correo.isEmpty());
    }
    
    // Validar longitud de entrada en contraseña
    public boolean validarMaximo(String datos) {
       
        if (!datos.isEmpty()) {
            if (datos.length() > 16 || datos.length() < 8) {
                return false;
            }
        }
        return true;
    }
    
    // Validar sintaxis de correo
    public boolean validarCorreo(String datos) {
        
        patron = Pattern.compile("^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}$");
        validar = patron.matcher(datos);
        
        if (!datos.isEmpty()) {
            if (!validar.find()) {
                
                return false;
            }
        }
        return true;
    }
    
    // Validar que solo reciba letras
    public boolean soloLetras(String datos) {
        
        //patron = Pattern.compile("^[a-zA-Z]*$");
        patron = Pattern.compile("[a-zA-Z]");
        validar = patron.matcher(datos); 
        if (!datos.isEmpty()) {
            if (validar.find()) {
                return false;
            }
        }
        return true;
    }
    
    // Validar que solo reciba numeros
    public boolean soloNumeros(String datos) {
        
        patron = Pattern.compile("^[0-9]*$");
        validar = patron.matcher(datos); 
        if (!datos.isEmpty()) {
            if (validar.find()) {
                return false;
            }
        }
        return true;
    }
    
    // Validar que las contraseñas coincidan
    public boolean validaClave(String claveNueva, String confirmacion) {
        return !(claveNueva == null ? confirmacion != null : !claveNueva.equals(confirmacion));
    }
}
