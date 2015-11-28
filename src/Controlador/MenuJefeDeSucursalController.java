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


public class MenuJefeDeSucursalController implements Initializable {
    @FXML
    private Button btnCambioDeClave;
    @FXML
    private Button btnCerrarSesion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
            primaryStage.setTitle("Cambio de clave Jefe de Sucursal");
            primaryStage.show();
                    
            } catch (IOException e) {}
    }
    
    @FXML
    private void ingresoInventarioDeProductos(ActionEvent event) {
        try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("InventarioDeProductos.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Inventario Jefe de Sucursal");
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
            primaryStage.setTitle("Login");
            primaryStage.show();
                    
            } catch (IOException e) {}
    }
    
}