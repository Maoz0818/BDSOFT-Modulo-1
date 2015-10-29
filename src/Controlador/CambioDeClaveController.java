package Controlador;

//Importaciones
import DAO.UsuarioDao;
import Logica.Validaciones;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class CambioDeClaveController implements Initializable {
    
    //Declaracion de variables
    @FXML
    private PasswordField txtClaveActual;
    @FXML
    private PasswordField txtClaveNueva;
    @FXML
    private PasswordField txtConfirmacionClave;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblClaveActual;
    @FXML
    private Label lblClaveNueva;
    @FXML
    private Label lblConfirmacionClave;
    @FXML
    private Button btnMenuPrincipal;
    
    private String claveActual;
    
    ResultSet resultadoConsulta;
    
    String cargo;
    
    private final Validaciones validacion = new Validaciones();
    
    //Objeto DAO
    private final UsuarioDao usuariodao = new UsuarioDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    //Evento boton guardar para guardar la clave nueva
    @FXML
    private void guardarClaveNueva(ActionEvent event) throws SQLException {
        
        lblClaveActual.setText("");
        lblClaveNueva.setText("");
        lblConfirmacionClave.setText("");
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtClaveActual.getText())) {
           lblClaveActual.setText("La clave actual es obligatoria");
           return;
        }
        
        //Declaracion de variables para la validacion
        String claveNueva = txtClaveNueva.getText();
        claveActual = txtClaveActual.getText();

        //Booleano para validar que si se encontro la clave actual ingresada
        boolean existeClave = usuariodao.validarClave(claveActual).next();
        resultadoConsulta = usuariodao.validarClave(claveActual);
                
        //Si la clave actual es correcta
        if (!existeClave) {           
            lblClaveActual.setText("");
            lblClaveActual.setText("su clave actual es incorrecta");
            return;
        }
        
        if (validacion.validarVacios(txtClaveNueva.getText())) {
           lblClaveNueva.setText("clave nueva obligatoria");
        }
        
        if (validacion.validarVacios(txtConfirmacionClave.getText())) {
           lblConfirmacionClave.setText("confirmar su clave nueva");
           return;
        }
        
        //Validacion de tamaño de contraseña
        if (!validacion.validarMaximo(txtClaveNueva.getText())) {
            lblClaveNueva.setText("");
            lblClaveNueva.setText("clave de minimo 8 digitos");
            return;
        }
        
        //Validar que las claves coincidan
        if (!validacion.validaClave(txtClaveNueva.getText(), txtConfirmacionClave.getText())) {
            lblClaveNueva.setText("");
            lblConfirmacionClave.setText("");
            lblConfirmacionClave.setText("Las claves no coinciden");
            return;
        }
            
        PreparedStatement estado = usuariodao.cambiarClave(claveNueva, claveActual);
            try {
                int n = estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Su clave ha sido cambiada con exito");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) throws SQLException {
        
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        cargo = primaryStage.getTitle();
        
        switch(cargo){
                    case "Cambio de clave Gerente":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuGerente.fxml"));
                                AnchorPane Gerente = (AnchorPane) loader.load();

                                //Agregamos a la ventana
                                Scene scene = new Scene(Gerente);
                                primaryStage.setScene(scene);
                                primaryStage.centerOnScreen();
                                primaryStage.setTitle("Menu Gerente");
                                primaryStage.show();
                    
                                } catch (IOException e) {}
                        break;
                        
                        case "Cambio de clave Jefe de Bodega":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuJefeDeBodega.fxml"));
                                AnchorPane JefeBodega = (AnchorPane) loader.load();
                                
                                //Agregamos a la ventana
                                Scene scene = new Scene(JefeBodega);
                                primaryStage.setScene(scene);
                                primaryStage.centerOnScreen();
                                primaryStage.setTitle("Menu Jefe de Bodega");
                                primaryStage.show();
                                
                                } catch (IOException e) {}
                        break;
                            
                        case "Cambio de clave Jefe de Sucursal":
                                try {
                                //Cargamos la scene
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Principal.class.getResource("MenuJefeDeSucursal.fxml"));
                                AnchorPane JefeSucursal = (AnchorPane) loader.load();
                                
                                //Agregamos a la ventana
                                Scene scene = new Scene(JefeSucursal);
                                primaryStage.setScene(scene);
                                primaryStage.centerOnScreen();
                                primaryStage.setTitle("Menu Jefe de Sucursal");
                                primaryStage.show();
                                
                                } catch (IOException e) {}
                        break;
                }
    } 
}
