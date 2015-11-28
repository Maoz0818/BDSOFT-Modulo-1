
package DAO;
  
import Logica.Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

   
public class ProveedorDao {
    // Variable para conexion  
     private Connection conexion;
     
     //SQL buscar proveedor por rut
    public ResultSet buscarProveedorPorRut(String busquedaPorRut){
        ResultSet resuladoProveedor = null;
    
        try {  conexion = ConexionDao.Conectar();
            
            String sql = "SELECT PROV.PROVEEDORID CODIGO, PROV.RUT, PROV.NOMBRE, PROV.TELEFONO, PROV.E_MAIL CORREO, PROV.TIPO_DE_PRODUCTO, EST.NOMBRE ESTADO\n" +
                         "FROM PROVEEDORES PROV INNER JOIN ESTADOS EST ON PROV.ESTADOID = EST.ESTADOID\n" +
                         "WHERE PROV.RUT = '" + busquedaPorRut + "'";
            resuladoProveedor = conexion.createStatement().executeQuery(sql);
           
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoProveedor;
    }
     
      //SQL buscar proveedor por nombre
    public ResultSet buscarProveedorPorNombre(String busquedaPorNombre){
        ResultSet resuladoProveedor = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROV.PROVEEDORID CODIGO, PROV.RUT, PROV.NOMBRE, PROV.TELEFONO, PROV.E_MAIL CORREO, PROV.TIPO_DE_PRODUCTO, EST.NOMBRE ESTADO\n" +
                         "FROM PROVEEDORES PROV INNER JOIN ESTADOS EST ON PROV.ESTADOID = EST.ESTADOID\n" +
                         "WHERE PROV.NOMBRE ILIKE '%" + busquedaPorNombre + "%'";
            resuladoProveedor = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoProveedor;
    }
    
    // SQL para traer los datos del registro seleccionado
    public ResultSet datosParaFormulario(String dato){
        
        ResultSet resuladoDatosParaFormulario = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT  RUT, NOMBRE, DIRECCION, CIUDAD, TELEFONO, E_MAIL, ESTADOID ESTADO, TIPO_DE_PRODUCTO\n" +
                         "FROM PROVEEDORES\n" +
                         "WHERE PROVEEDORID = '"+dato+"'";
            
            resuladoDatosParaFormulario = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoDatosParaFormulario;
    }
    
    // SQL para registrar un nuevo proveedor
    public PreparedStatement nuevoProveedor(Proveedores nuevoProveedor){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO PROVEEDORES( ESTADOID, RUT, NOMBRE, TELEFONO, CIUDAD, DIRECCION, E_MAIL, TIPO_DE_PRODUCTO)\n" +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setInt(1, nuevoProveedor.getEstado());
            estado.setString(2, nuevoProveedor.getRut());
            estado.setString(3, nuevoProveedor.getNombre());
            estado.setString(4, nuevoProveedor.getTelefono());
            estado.setString(5, nuevoProveedor.getCiudad());
            estado.setString(6, nuevoProveedor.getDireccion());
            estado.setString(7, nuevoProveedor.geteMail());
            estado.setString(8, nuevoProveedor.getTipo());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    public PreparedStatement modificarProveedor(Proveedores prov, String codigo) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDao.Conectar();
            String sql = "UPDATE PROVEEDORES\n" +
                         "SET ESTADOID=?, RUT=?, NOMBRE=?, TELEFONO=?, CIUDAD=?, \n" +
                         "DIRECCION=?, E_MAIL=?, TIPO_DE_PRODUCTO=?\n" +
                         "WHERE  PROVEEDORID = " + codigo;

            estado = conexion.prepareStatement(sql);
            estado.setInt(1, prov.getEstado());
            estado.setString(2, prov.getRut());
            estado.setString(3, prov.getNombre());
            estado.setString(4, prov.getTelefono());
            estado.setString(5, prov.getCiudad());
            estado.setString(6, prov.getDireccion());
            estado.setString(7, prov.geteMail());
            estado.setString(8, prov.getTipo());

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
    
    // SQL PARA LLENAR EL COMBOBOX PROVEEDOR ///////////////////////////////////////////////////////////////////////////
    public ResultSet comboBoxProveedor(){
        
        ResultSet resultadoProveedor = null;
                
        try {
        conexion = ConexionDao.Conectar();
        String slqTipo = "SELECT PROVEEDORID, NOMBRE " +
                         "FROM PROVEEDORES " +
                         "ORDER BY PROVEEDORID;";
        resultadoProveedor = conexion.createStatement().executeQuery(slqTipo);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return resultadoProveedor;
    }
    
}
