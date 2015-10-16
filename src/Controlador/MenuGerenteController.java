package Controlador;

import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MenuGerenteController implements Initializable {
    @FXML
    private Button btnGestionDeUsuarios;
    @FXML
    private Button btnOrdenesDeCompra;
    @FXML
    private Button btnCambioDeClave;
    @FXML
    private Button btnCerrarSesion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void ingresoGestionDeUsuarios(ActionEvent event) {
            try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("GestionDeUsuarios.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
                                
            } catch (IOException e) {}
    }

    @FXML
    private void ingresoOrdenesDeCompra(ActionEvent event) {
    }

    @FXML
    private void ingresoCambioDeClave(ActionEvent event) {
            try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("CambioDeClave.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
                    
            } catch (IOException e) {}
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
            try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("Login.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
                    
            } catch (IOException e) {}
    }
    
}
