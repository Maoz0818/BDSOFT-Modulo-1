package Vista;

import javafx.scene.image.Image;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Principal extends Application {
    private Stage primaryStage;
    private AnchorPane Login;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login");

        login();
    }

    public void login() {
                
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("Login.fxml"));
            Login = (AnchorPane) loader.load();
            Scene scene = new Scene(Login);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/CSS/LogoX.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
            } catch (IOException e) {
       }
        
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
