package DAO;

//Importaciones necesarias
import Logica.Usuario;
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
    
    // SQL para registrar un nuevo usuario
    public PreparedStatement nuevoUsuario(Usuario nuevoUsuario){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO USUARIOS"
                    + " (ESTADOID, NOMBRES, APELLIDOS, CARGO, TELEFONO, E_MAIL, CLAVE) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setInt(1, nuevoUsuario.getEstado());
            estado.setString(2, nuevoUsuario.getNombres());
            estado.setString(3, nuevoUsuario.getApellidos());
            estado.setString(4, nuevoUsuario.getCargo());
            estado.setInt(5, nuevoUsuario.getTelefono());
            estado.setString(6, nuevoUsuario.getE_mail());
            estado.setString(7, nuevoUsuario.getClave());
            
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
    // SQL para traer los datos del registro seleccionado
    public ResultSet datosParaFormulario(String dato){
        
        ResultSet resuladoDatosParaFormulario = null;
    
        try {  conexion = ConexionDao.Conectar();
            String sql = "SELECT  USU.NOMBRES NOMBRE_USUARIO, USU.APELLIDOS, SUC.NOMBRE NOMBRE_SUCURSAL, SUC.CIUDAD, SUC.DIRECCION, USU.TELEFONO, USU.E_MAIL, USU.ESTADOID\n" +
                         "FROM USUARIOS USU INNER JOIN JEFE_SUCURSAL JSUC ON USU.USUARIOID = JSUC.USUARIOID\n" +
                         "INNER JOIN SUCURSALES SUC ON JSUC.JEFEID = SUC.JEFEID\n" +
                         "WHERE USU.USUARIOID = '"+dato+"'";
            
            resuladoDatosParaFormulario = conexion.createStatement().executeQuery(sql);
                    
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return resuladoDatosParaFormulario;
    }
    
    // SQL para modificar usuario
    public PreparedStatement modificarUsuario(Usuario modificarUsuario){
        
        PreparedStatement estado = null;
                
        try {  conexion = ConexionDao.Conectar();
            String sql = "UPDATE USUARIOS "
                    + " SET  ESTADOID = ?, NOMBRES = ?, APELLIDOS = ?, TELEFONO = ?, E_MAIL = ?"
                    + " WHERE USUARIOID = " + modificarUsuario.getCodigoUsuario();
            
            estado = conexion.prepareStatement(sql);
            estado.setInt(1, modificarUsuario.getEstado());
            estado.setString(2, modificarUsuario.getNombres());
            estado.setString(3, modificarUsuario.getApellidos());
            estado.setInt(4, modificarUsuario.getTelefono());
            estado.setString(5, modificarUsuario.getE_mail());
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
