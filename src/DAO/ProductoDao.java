
package DAO;

import Logica.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductoDao {
    
    // Variable para conexion  
     private Connection conexion;
     
     //SQL buscar producto por rut
    public ResultSet buscarProductoPorCodigo(String busquedaPorCodigo){
        ResultSet resuladoProducto = null;
    
        try {  conexion = ConexionDao.Conectar();
            
            String sql = "SELECT PROD.PRODUCTOID CODIGO, PROD.NOMBRE, TIP.NOMBRE TIPO, EXIS.CANTIDAD, PROV.NOMBRE PROVEEDOR, PROD.PRECIO\n" +
                         "FROM TIPO TIP INNER JOIN PRODUCTOS PROD ON TIP.TIPOID = PROD.TIPOID\n" +
                         "INNER JOIN PRODUCTOS_EN_EXISTENCIA EXIS ON PROD.PRODUCTOID = EXIS.PRODUCTOID\n" +
                         "INNER JOIN PROVEEDORES PROV ON EXIS.PROVEEDORID = PROV.PROVEEDORID\n" +
                         "WHERE PROD.PRODUCTOID = "+ busquedaPorCodigo;
            resuladoProducto = conexion.createStatement().executeQuery(sql);
           
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoProducto;
    }
     
    //SQL buscar producto por nombre
    public ResultSet buscarProductoPorNombre(String busquedaPorNombre){
        ResultSet resuladoProducto = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROD.PRODUCTOID CODIGO, PROD.NOMBRE, TIP.NOMBRE TIPO, EXIS.CANTIDAD, PROV.NOMBRE PROVEEDOR, PROD.PRECIO\n" +
                         "FROM TIPO TIP INNER JOIN PRODUCTOS PROD ON TIP.TIPOID = PROD.TIPOID\n" +
                         "INNER JOIN PRODUCTOS_EN_EXISTENCIA EXIS ON PROD.PRODUCTOID = EXIS.PRODUCTOID\n" +
                         "INNER JOIN PROVEEDORES PROV ON EXIS.PROVEEDORID = PROV.PROVEEDORID\n" +
                         "WHERE PROD.NOMBRE ILIKE '%" + busquedaPorNombre + "%'";
            resuladoProducto = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoProducto;
    }
    
    // SQL para traer los datos del registro seleccionado
    public ResultSet datosParaFormulario(String dato){
        
        ResultSet resuladoDatosParaFormulario = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROD.NOMBRE, TIP.NOMBRE TIPO, ALM.SECCION, PROV.NOMBRE PROVEEDOR,PROD.MARCA, PROD.PRECIO, EXIS.CANTIDAD\n" +
                         "FROM TIPO TIP INNER JOIN PRODUCTOS PROD ON TIP.TIPOID = PROD.TIPOID\n" +
                         "INNER JOIN PRODUCTOS_EN_EXISTENCIA EXIS ON PROD.PRODUCTOID = EXIS.PRODUCTOID\n" +
                         "INNER JOIN ALMACEN ALM ON EXIS.ALMACENID = ALM.ALMACENID\n" +
                         "INNER JOIN PROVEEDORES PROV ON EXIS.PROVEEDORID = PROV.PROVEEDORID\n" +
                         "WHERE PROD.PRODUCTOID = '"+dato+"'";
            
            resuladoDatosParaFormulario = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoDatosParaFormulario;
    }
    
    // SQL para registrar un nuevo producto
    public PreparedStatement nuevoProducto(Productos nuevoProducto){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO PRODUCTOS( TIPOID, NOMBRE, PRECIO, MARCA)\n" +
                         "VALUES (?, ?, ?, ?);";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setInt(1, nuevoProducto.getTipo());
            estado.setString(2, nuevoProducto.getNombre());
            estado.setDouble(3, nuevoProducto.getPrecio());
            estado.setString(4, nuevoProducto.getMarca());
                       
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // SQL PARA BUSCAR UN PRODUCTO POR NOMBRE //////////////////////////////////////////////////////////////////////////
    public int buscarCodigoDeProducto(String nombreProducto){
        
        int codigoProducto = 0;
        ResultSet resultadoCodigoProducto;
               
        try {
        conexion = ConexionDao.Conectar();
        String slqTipo = "SELECT PRODUCTOID\n" +
                         "  FROM PRODUCTOS\n" +
                         "  WHERE NOMBRE = '"+nombreProducto+"'";
        resultadoCodigoProducto = conexion.createStatement().executeQuery(slqTipo);
        while(resultadoCodigoProducto.next()){
        codigoProducto = resultadoCodigoProducto.getInt("PRODUCTOID");
        }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
             
        return codigoProducto;
    }
    
    public PreparedStatement modificarProducto(Productos producto, int codigo) {

        PreparedStatement estado = null;

        try {
            conexion = ConexionDao.Conectar();
            String sql = "UPDATE PRODUCTOS\n" +
                         "SET TIPOID = ?, NOMBRE = ?, PRECIO = ?, MARCA = ?" +
                         "WHERE  PRODUCTOID = " + codigo;

            estado = conexion.prepareStatement(sql);
            estado.setInt(1, producto.getTipo());
            estado.setString(2, producto.getNombre());
            estado.setDouble(3, producto.getPrecio());
            estado.setString(4, producto.getMarca());
      
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return estado;
    }
    
    //SQL para el inventario de gerente
    public ResultSet mostrarInventarioGerente(){
        
        ResultSet resuladoInventarioGerente = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROD.PRODUCTOID CODIGO, PROD.NOMBRE, TIP.NOMBRE TIPO, EXIS.CANTIDAD, PROV.NOMBRE PROVEEDOR, PROD.PRECIO\n" +
                         "FROM TIPO TIP INNER JOIN PRODUCTOS PROD ON TIP.TIPOID = PROD.TIPOID\n" +
                         "INNER JOIN PRODUCTOS_EN_EXISTENCIA EXIS ON PROD.PRODUCTOID = EXIS.PRODUCTOID\n" +
                         "INNER JOIN PROVEEDORES PROV ON EXIS.PROVEEDORID = PROV.PROVEEDORID";
            resuladoInventarioGerente = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoInventarioGerente;
    }
    
    //SQL para el inventario del Jefe de Bodega
    public ResultSet mostrarInventarioJefeDeBodega(){
        
        ResultSet resuladoInventarioJefeDeBodega = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROD.PRODUCTOID CODIGO, PROD.NOMBRE, ALM.SECCION, ALM.PASILLO, ALM.ESTANTERIA , EXIS.CANTIDAD, PROD.PRECIO \n" +
                         "FROM PRODUCTOS PROD INNER JOIN PRODUCTOS_EN_EXISTENCIA EXIS ON PROD.PRODUCTOID = EXIS.PRODUCTOID\n" +
                         "INNER JOIN ALMACEN ALM ON EXIS.ALMACENID = ALM.ALMACENID";
            resuladoInventarioJefeDeBodega = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoInventarioJefeDeBodega;
    }
    
    //SQL para el inventario del Jefe de Sucursal
    public ResultSet mostrarInventarioJefeDeSucursal(){
        
        ResultSet resuladoInventarioJefeDeSucursal = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT PROD.PRODUCTOID CODIGO, PROD.NOMBRE, PROD.MARCA, PROD.PRECIO\n" +
                         "FROM PRODUCTOS PROD";
            resuladoInventarioJefeDeSucursal = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoInventarioJefeDeSucursal;
    }
    
}
