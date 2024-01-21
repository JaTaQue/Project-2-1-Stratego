package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneHome implements Initializable{

    public Button exit;
    public Button help;
    @FXML
    private AnchorPane painer;

    @FXML
    private Label gameModeLabel;

    public String gameMode;

    private static Stage stage;

    private Scene scene;



    private void HomeToGame(ActionEvent event) throws IOException{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScreenGame.fxml"));
        Parent root = loader.load();
        SceneGame controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        controller.setGame(gameMode);
    }

    public void HomeToPVP(ActionEvent event) throws IOException{
        gameModeLabel.setText("PvP");
        gameMode = "PvP";
        HomeToGame(event);
    }

    public void HomeToPVE(ActionEvent event) throws IOException{
        gameModeLabel.setText("PvE");
        gameMode = "PvE";
        HomeToGame(event);
    }

    public void HomeToEVE(ActionEvent event) throws IOException{
        gameModeLabel.setText("EvE");
        gameMode = "EvE";
        HomeToGame(event);
    }

    public static Stage getStage() {
        return stage;
    }


    public void HomeToHelp(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHelp.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    public void exitGame() {
        stage = (Stage)(painer.getScene().getWindow());
        stage.close();
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SceneHelp.Stop(painer);
    }

}
