package DAO;

import Logica.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SucursalDao {
    // Variable para conexion    
    private Connection conexion;
    
    // SQL para registrar un nuevo usuario
    public PreparedStatement nuevaSucursal(Sucursal nuevaSucursal){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO SUCURSALES" 
                    + "(JEFEID, NOMBRE, CIUDAD, DIRECCION)"
                    + " VALUES (?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
            
            estado.setInt(1, nuevaSucursal.getCodigoJefe());
            estado.setString(2, nuevaSucursal.getNombre());
            estado.setString(3, nuevaSucursal.getCiudad());
            estado.setString(4, nuevaSucursal.getDireccion());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    //SQL para obtener el ultimo id
    public ResultSet ultimoId(){
        
        ResultSet resuladoConsultaUltimoId = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT MAX(JEFEID) JEFEID FROM JEFE_SUCURSAL";
                   
            resuladoConsultaUltimoId = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoConsultaUltimoId;
    }
    
    // SQL para modificar usuario
    public PreparedStatement modificarSucursal(Sucursal modificarSucursal){
        
        PreparedStatement estado = null;
                
        try {  conexion = ConexionDao.Conectar();
            String sql = "UPDATE SUCURSALES "
                    + " SET  NOMBRE = ?, CIUDAD = ?, DIRECCION = ?"
                    + " WHERE JEFEID = " + modificarSucursal.getCodigoJefe();
            
            estado = conexion.prepareStatement(sql);
            estado.setString(1, modificarSucursal.getNombre());
            estado.setString(2, modificarSucursal.getCiudad());
            estado.setString(3, modificarSucursal.getDireccion());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
}
