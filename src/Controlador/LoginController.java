package Controlador;

//Importaciones
import DAO.LoginDao;
import Logica.Validaciones;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {
    
    //Declaracion de variables
    @FXML
    private TextField txtCorreoUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnIngresar;
    @FXML
    private Label lblMensajeCorreo;
    @FXML
    private Label lblMensajeContraseña;

    private final Validaciones validacion = new Validaciones();
    
    private String cargo;
    
    //Objeto DAO
    private final LoginDao logindao = new LoginDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    //Evento del boton ingresar
    @FXML
    private void validarIngreso(ActionEvent event) throws SQLException {
        lblMensajeCorreo.setText("");
        lblMensajeContraseña.setText("");
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtCorreoUsuario.getText())) {
           lblMensajeCorreo.setText("El correo de usuario es obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtCorreoUsuario.getText())) {
            lblMensajeCorreo.setText("");
            lblMensajeCorreo.setText("Correo invalido");
           return;
        }
        
        if (validacion.validarVacios(txtContraseña.getText())) {
           lblMensajeContraseña.setText("La contraseña es obligatoria");
           return;
        }
        
        //Validacion de tamaño de contraseña
        if (!validacion.validarMaximo(txtContraseña.getText())) {
            lblMensajeContraseña.setText("");
            lblMensajeContraseña.setText("Contraseña invalida debe ser de minimo 8 digitos");
            return;
        }
        
        //Declaracion de variables para la validacion
        String correo = txtCorreoUsuario.getText();
        String codigo = txtContraseña.getText();
        
        //Booleano para validar que si se encontro un usuario
        boolean existeUsuario = logindao.loginUsuarios(correo, codigo).next();
        ResultSet resultadoConsulta = logindao.loginUsuarios(correo, codigo);
        
        //While para recorrer el ResultSet y obtener el cargo del usuario
        while(resultadoConsulta.next()){
            cargo = resultadoConsulta.getString("cargo").trim();
        }
        
        //Si existe el usuario se carga la GUI segun sea su cargo
            if (existeUsuario) {
                switch(cargo){
                    case "Gerente":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuGerente.fxml"));
                                AnchorPane Gerente = (AnchorPane) loader.load();

                                //Agregamos a la ventana
                                Scene scene = new Scene(Gerente);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                    
                                } catch (IOException e) {}
                        break;
                        
                        case "Jefe de Bodega":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuJefeDeBodega.fxml"));
                                AnchorPane JefeBodega = (AnchorPane) loader.load();
                                
                                //Agregamos a la ventana
                                Scene scene = new Scene(JefeBodega);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                
                                } catch (IOException e) {}
                        break;
                            
                        case "Jefe de Sucursal":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuJefeDeSucursal.fxml"));
                                AnchorPane JefeSucursal = (AnchorPane) loader.load();
                                
                                //Agregamos a la ventana
                                Scene scene = new Scene(JefeSucursal);
                                Node node = (Node) event.getSource();
                                Stage primaryStage = (Stage) node.getScene().getWindow();
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                
                                } catch (IOException e) {}
                        break;
                }
                
            }
            else{
                   JOptionPane.showMessageDialog(null, "Usuario no registrado favor comunicate con el Gerente");
                }
    }
    
}
