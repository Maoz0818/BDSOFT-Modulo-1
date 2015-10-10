package DAO;

//Importaciones necesarias
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
    // Variable para conexion    
    private Connection conexion;
    
    //SQL validar clave
    public ResultSet validarClave(String clave){
        ResultSet resuladoConsultaClave = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT CLAVE FROM "
                    + " USUARIOS WHERE "
                    + " CLAVE = '"+clave+"'";
            resuladoConsultaClave = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoConsultaClave;
    }
    
    //SQL para cambiar clave
    public PreparedStatement cambiarClave(String claveNueva, String claveActual){
        
        PreparedStatement estado = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "UPDATE USUARIOS "
                    + " SET CLAVE = ? "
                    + " WHERE CLAVE = '"+claveActual+"'";
            
            estado = conexion.prepareStatement(sql);
            estado.setString(1, claveNueva);
                                
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return estado;
    }
}
