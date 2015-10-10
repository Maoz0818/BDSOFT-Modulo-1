package DAO;

//Importaciones necesarias
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    // Variable para conexion    
    private Connection conexion;
    
    // SQL para validar usuarios
    public ResultSet loginUsuarios(String correo, String contraseña){
        
        ResultSet resuladoConsultaUsuarios = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT * FROM "
                    + " USUARIOS WHERE "
                    + " E_MAIL = '"+correo+"' AND "
                    + " CLAVE = '"+contraseña+"'";
            resuladoConsultaUsuarios = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoConsultaUsuarios;
    }
    
    //SQL validar clave
    public ResultSet validarClave(String correo, String contraseña){
        ResultSet resuladoConsultaClave = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT CLAVE FROM "
                    + " USUARIOS WHERE "
                    + " CLAVE = '"+correo+"'";
            resuladoConsultaClave = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoConsultaClave;
    }
}


