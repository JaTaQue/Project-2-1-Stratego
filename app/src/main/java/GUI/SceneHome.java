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
    private Button pvp;

    @FXML
    private Button pve;

    @FXML
    private Button eve;

    @FXML
    private Label gameModeLabel;

    public String gameMode;



    private static Stage stage;
    private Scene scene;

    public void GameToHome(ActionEvent event) throws IOException{
    
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHome.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }


    private void HomeToGame(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/ScreenGame.fxml");
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void HomeToPVP(ActionEvent event) throws IOException{
        gameModeLabel.setText("PvP");
        HomeToGame(event);
    }

    private void HomeToPVE(ActionEvent event) throws IOException{
        gameModeLabel.setText("PvE");
        HomeToGame(event);
    }

    private void HomeToEVE(ActionEvent event) throws IOException{
        gameModeLabel.setText("EvE");
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

    public void etGameModeLabel() {
        gameMode = gameModeLabel.getText();
    }
}
