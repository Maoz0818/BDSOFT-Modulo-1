
package DAO;

import Logica.Existencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ExistenciaDao {
    
    // Variable para conexion  
     private Connection conexion;
    
    // SQL para registrar un nueva existencia
    public PreparedStatement nuevaExistencia(Existencias nuevoExistencia){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO PRODUCTOS_EN_EXISTENCIA(ALMACENID, PROVEEDORID, PRODUCTOID, CANTIDAD)\n" +
                         "VALUES (?, ?, ?, ?);";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setInt(1, nuevoExistencia.getUbicacion());
            estado.setInt(2, nuevoExistencia.getProveedor());
            estado.setInt(3, nuevoExistencia.getCodigo());
            estado.setInt(4, nuevoExistencia.getCantidad());
                       
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // CONSULTA PARA COMBOBOX ALMACEN
    public ResultSet comboBoxUbicacion(){
        
        ResultSet resultadoUbicacion = null;
                
        try {
        conexion = ConexionDao.Conectar();
        String slq = "SELECT ALMACENID, SECCION " +
                              "FROM ALMACEN " +
                              "ORDER BY ALMACENID;";
        resultadoUbicacion = conexion.createStatement().executeQuery(slq);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoUbicacion;
    }
    
    public PreparedStatement modificarExistencia(Existencias existencia, int codigo) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDao.Conectar();
            String sql = "UPDATE PRODUCTOS_EN_EXISTENCIA\n" +
                         "SET ALMACENID = ?, PROVEEDORID = ?, PRODUCTOID = ?, CANTIDAD = ?" +
                         "WHERE  PRODUCTOID = " + codigo;

            estado = conexion.prepareStatement(sql);
            estado.setInt(1, existencia.getUbicacion());
            estado.setInt(2, existencia.getProveedor());
            estado.setInt(3, existencia.getCodigo());
            estado.setInt(4, existencia.getCantidad());
      
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
    
}
