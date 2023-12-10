package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneHome implements Initializable{
    
    @FXML
    private AnchorPane painer;

    @FXML
    private Button pvp;

    @FXML
    private Button pve;

    @FXML
    private Button eve;



    private static Stage stage;
    private Scene scene;

    public void GameToHome(ActionEvent event) throws IOException{
    
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHome.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }

    
    public void HomeToGame(ActionEvent event) throws IOException{
        /*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenGame.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        */
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Person.fxml"));
        Parent root = loader.load();
        SceneGame personController = loader.getController();
        personController.setName("pvp");
        pvp.getScene().setRoot(root);
        */
        

        Stage confirmation_stage;
        Parent confirmation;
        confirmation_stage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScreenGame.fxml"));
        confirmation = loader.load();
        SceneGame controller = (SceneGame)loader.getController();
        controller.setName("Your Text");
        confirmation_stage.setScene(new Scene(confirmation));
        confirmation_stage.show();
        
        
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
