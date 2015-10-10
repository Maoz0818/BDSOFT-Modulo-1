package DAO;

//Importaciones
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDao {
    
    //Declaracion de variables
    private static Connection coneccion;
    private static final String url = "jdbc:postgresql://localhost:5432/DBModulo1";
    private static final String usuario = "postgres";
    private static final String contraseña = "mao4202229";
    
    //Metodo para usar el driver de postgresql
    public static Connection Conectar() throws SQLException{
	try {
            Class.forName("org.postgresql.Driver").newInstance();
	} catch(ClassNotFoundException | InstantiationException | IllegalAccessException cnfe) {
            System.err.println("Error: "+cnfe.getMessage());
	}
            coneccion = DriverManager.getConnection(url,usuario,contraseña);
            return coneccion;
	}
    
    //Metodo que realiza la conexion
    public static Connection getConexion() throws SQLException, ClassNotFoundException{
        if(coneccion != null && !coneccion.isClosed())
            return coneccion;
            Conectar();
            return coneccion;
    }
    
}
