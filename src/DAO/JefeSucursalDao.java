package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JefeSucursalDao {
    // Variable para conexion    
    private Connection conexion;
    
    // SQL para traer el jefeid de Jefe_Sucursal
    public ResultSet consultarJefeId(String codigoUsuario){
        
        ResultSet resuladoConsultaJefeId = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT JEFEID FROM JEFE_SUCURSAL\n" +
                         "WHERE USUARIOID = '"+codigoUsuario+"'";
            resuladoConsultaJefeId = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoConsultaJefeId;
    }
    
}
