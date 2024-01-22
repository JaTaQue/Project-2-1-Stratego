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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneHome implements Initializable{
    @FXML
    public Button exit;
    @FXML
    public Button help;

    @FXML
    public Button pve;

    @FXML
    public Button eve;

    @FXML
    public Button pvp;

    @FXML
    private ChoiceBox<String> gamebox;
    private String[] modes = {"Player", "AI"};


    @FXML
    private AnchorPane painer;

    @FXML
    private Label gameModeLabel;

    @FXML
    private ChoiceBox<String> boxergamer;

    @FXML
    private Button goButton;

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
        controller.setGame(gameMode.charAt(0), gameMode.charAt(0));
    }

    public void HomeToPVP(ActionEvent event) throws IOException{
        //gameModeLabel.setText("PvP");
        gameMode = "PvP";
        HomeToGame(event);
    }

    public void HomeToPVE(ActionEvent event) throws IOException{
        //gameModeLabel.setText("PvE");
        gameMode = "PvE";
        HomeToGame(event);
    }

    public void HomeToEVE(ActionEvent event) throws IOException{
        //gameModeLabel.setText("EvE");
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
        gamebox.getItems().addAll(modes);
        boxergamer.getItems().addAll(modes);
        /*
        gamebox.setOnAction(event -> {
            try {
                dogame(event);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        */
        SceneHelp.Stop(painer);
    }


    public void dogame(ActionEvent event) throws IOException{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScreenGame.fxml"));
        Parent root = loader.load();
        SceneGame controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        String p1 = gamebox.getValue().charAt(0);
        String p2 = boxergamer.getValue().charAt(0);
        controller.setGame(p1, p2);
    }




}
