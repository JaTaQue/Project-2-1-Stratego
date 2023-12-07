package GUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHome.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stratego");
        
        // Set the application icon.
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/icon.png")).toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);


        
        primaryStage.show();
    }
}
