import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import GUI.SceneGame;

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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                // TODO Auto-generated method stub
                //controller.yeetButton();
                System.out.println(event.getCode());
                if(event.getCode() == KeyCode.J || event.getCode() == KeyCode.K || event.getCode() == KeyCode.L){
                    try {
                        Stage primaStage = new Stage();
                        FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/ScreenGame.fxml"));
                        Parent rooter = loaderr.load();
                        SceneGame controller = loaderr.getController();
                        String moderas = "";
                        if(event.getCode() == KeyCode.J){
                            moderas = "PvP";
                        }else if(event.getCode() == KeyCode.K){
                            moderas = "PvE";
                        }else{
                            moderas = "EvE";
                        }
                        String por = "sadf";
                        controller.setGame(moderas.charAt(0), por.charAt(0));
                        Scene scener = new Scene(rooter);
                        scener.setOnKeyPressed(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                // TODO Auto-generated method stub
                                Stage secantStage = new Stage();
                                if(event.getCode() == KeyCode.H){
                                    try {
                                        FXMLLoader loaderas = new FXMLLoader(getClass().getResource("/ScreenHelp.fxml"));
                                        Parent rootas;
                                        rootas = loaderas.load();
                                        secantStage.setScene(new Scene(rootas));
                                        secantStage.setResizable(false);
                                        secantStage.show();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }});
                        primaStage.setScene(scener);
                        primaStage.show();
                        primaryStage.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        });
        primaryStage.setScene(scene);



        primaryStage.show();
    }
}
