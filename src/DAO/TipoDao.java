
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TipoDao {
    // CONEXION
    private Connection conexion;
    
    // SQL PARA LLENER EL COMBOBOX DE TIPO ////////////////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxTipo(){
        
        ResultSet resultadoTipo = null;
                
        try {
        conexion = ConexionDao.Conectar();
        String slqTipo = "SELECT TIPOID, NOMBRE FROM TIPO ORDER BY TIPOID";
        resultadoTipo = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoTipo;
    }
    
}
