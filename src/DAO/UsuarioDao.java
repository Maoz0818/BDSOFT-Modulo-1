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
    
    //SQL buscar usuario por nombre
    public ResultSet buscarUsuarioPorNombre(String busquedaPorNombre, String cargo){
        ResultSet resuladoUsuario = null;
    
        try {  conexion = ConexionDao.Conectar();
            if(cargo.equals("Bodega")){
            String sql = "SELECT USU.USUARIOID CODIGO, USU.NOMBRES NOMBRES, USU.APELLIDOS APELLIDOS, USU.CARGO CARGO, USU.E_MAIL CORREO, EST.NOMBRE ESTADO\n" +
            "FROM ESTADOS EST INNER JOIN USUARIOS USU ON EST.ESTADOID = USU.ESTADOID\n" +
            "WHERE ((USU.NOMBRES || ' ' || USU.APELLIDOS) ILIKE '%" + busquedaPorNombre + "%') AND (USU.CARGO = 'Gerente' OR USU.CARGO = 'Jefe de Bodega')";
            resuladoUsuario = conexion.createStatement().executeQuery(sql);
            }
            
            if(cargo.equals("Sucursal")){
            String sql = "SELECT USU.USUARIOID CODIGO, USU.NOMBRES NOMBRES, USU.APELLIDOS APELLIDOS, USU.CARGO CARGO, USU.E_MAIL CORREO, EST.NOMBRE ESTADO\n" +
            "FROM ESTADOS EST INNER JOIN USUARIOS USU ON EST.ESTADOID = USU.ESTADOID\n" +
            "WHERE ((USU.NOMBRES || ' ' || USU.APELLIDOS) ILIKE '%" + busquedaPorNombre + "%') AND (USU.CARGO = 'Jefe de Sucursal')";
            resuladoUsuario = conexion.createStatement().executeQuery(sql);
            }
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoUsuario;
    }
    
    //SQL buscar usuario por codigo
    public ResultSet buscarUsuarioPorCodigo(String busquedaPorCodigo, String cargo){
        ResultSet resuladoUsuario = null;
    
        try {  conexion = ConexionDao.Conectar();
            if(cargo.equals("Bodega")){
            String sql = "SELECT USU.USUARIOID CODIGO, (USU.NOMBRES, USU.APELLIDOS) NOMBRE, USU.CARGO CARGO, USU.E_MAIL CORREO, EST.NOMBRE ESTADO\n" +
            "FROM ESTADOS EST INNER JOIN USUARIOS USU ON EST.ESTADOID = USU.ESTADOID\n" +
            "WHERE (USU.USUARIOID = " + busquedaPorCodigo + ") AND (USU.CARGO = 'Gerente' OR USU.CARGO = 'Jefe de Bpdega')";
            resuladoUsuario = conexion.createStatement().executeQuery(sql);
            }
            
            if(cargo.equals("Sucursal")){
            String sql = "SELECT USU.USUARIOID CODIGO, USU.NOMBRES NOMBRES, USU.APELLIDOS APELLIDOS, USU.CARGO CARGO, USU.E_MAIL CORREO, EST.NOMBRE ESTADO\n" +
            "FROM ESTADOS EST INNER JOIN USUARIOS USU ON EST.ESTADOID = USU.ESTADOID\n" +
            "WHERE (USU.USUARIOID = " + busquedaPorCodigo + ") AND (USU.CARGO = 'Jefe de Sucursal')";
            resuladoUsuario = conexion.createStatement().executeQuery(sql);
            }
            
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoUsuario;
    }
    
}
