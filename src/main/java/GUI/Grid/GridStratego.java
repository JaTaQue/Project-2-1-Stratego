package GUI.Grid;
import javafx.scene.layout.AnchorPane;

public class GridStratego extends GridBase{


    public GridStratego(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);
    }

    //method for returning the coordinates of a click on the board
    public int[] getClickCoordinates(double mouseAnchorX, double mouseAnchorY){
        int[] arr = new int[2];
            arr[0] = (int) ((mouseAnchorX/getGridSize()) % getTilesAcross());
            arr[1] = (int) ((mouseAnchorY/getGridSize()) % getTilesDown());
        return arr;
    }
}