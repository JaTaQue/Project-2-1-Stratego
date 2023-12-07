package GUI.Grid;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class extends GridBase and represents a handler for the grid in the Stratego game GUI.
 * It contains a constructor that initializes the grid with the given plane width, plane height, grid size, and anchor pane.
 * It also contains a method updateGrid() that updates the grid by creating rectangles for each tile and adding them to the anchor pane.
 */
public class GridHandler extends GridBase {

    public GridHandler(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);
    }

    public void updateGrid() {
        for(int i = 0; i < getTileAmount(); i++){
            int x = (i % getTilesAcross());
            int y = (i / getTilesAcross());

            Rectangle rectangle = new Rectangle(x * getGridSize(),y * getGridSize(),getGridSize(),getGridSize());

            rectangle.setFill(new ImagePattern(new Image(String.valueOf(GridHandler.class.getResource("/tile.png")))));
            getAnchorPane().getChildren().add(rectangle);
        }
    }
}