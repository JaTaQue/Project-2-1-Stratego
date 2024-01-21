package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneHelp implements Initializable{
    
    @FXML
    private AnchorPane pain;


    public void HelpToHome(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHome.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stop(pain);
    }

    static void Stop(AnchorPane pain) {
        Stop[] stops = new Stop[] {new Stop(0, Color.BURLYWOOD), new Stop(1, Color.BROWN)};
        LinearGradient lgcolor = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill bgfill = new BackgroundFill(lgcolor, CornerRadii.EMPTY, Insets.EMPTY);
        pain.setBackground(new Background(bgfill));
    }

}
