package GUI;
import javafx.animation.AnimationTimer;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;


public class Component {

    private final Rectangle rectangle;
    private Boolean isVisible = false;
    private Boolean clickable = true;
    private String drawing;
    private String skin="default";
    private Boolean isMoving = false;


    public Component(int size, int startPositionX, int startPositionY) {
        rectangle = new Rectangle(startPositionX*size, startPositionY*size, size, size);
    }
    public Component(int startPositionX, int startPositionY) {
        int size=SceneGame.GRID_SIZE;
        rectangle = new Rectangle(startPositionX*size, startPositionY*size, size, size);
    }
    
    public void draw(String imageName){
        // Load the PNG image from the resources folder
        String path = Objects.requireNonNull(getClass().getResource("/" + imageName + ".png")).toExternalForm();
        Image img = new Image(path);
        // Fill the Rectangle with the PNG image
        rectangle.setFill(new ImagePattern(img));
        this.drawing = imageName;
    }

    public void draw(int rank, String color){
        String imageName = ""+rank + color.charAt(0);
        this.drawing = imageName;
        // Load the PNG image from the resources folder
        String path = Objects.requireNonNull(getClass().getResource("/" + skin + "/" + imageName + ".png")).toExternalForm();
        Image img = new Image(path);
        // Fill the Rectangle with the PNG image
        rectangle.setFill(new ImagePattern(img));
        //if the component is not revealed, set opacity to 75%
        if(!this.isVisible)
            rectangle.setOpacity(0.75);
        if(rank==0)
            rectangle.setOpacity(1);
    }

    public void drawNumber(int number){
        //downscale the rectangle
        rectangle.setWidth(SceneGame.GRID_SIZE/4);
        rectangle.setHeight(SceneGame.GRID_SIZE/4);
        //turn the number into a string
        String string = String.valueOf(number);
        //create an image from the number
        Image img = createImageFromString(string);
        rectangle.setFill(new ImagePattern(img));
        rectangle.setStroke(Color.WHITE);
        this.drawing = string+" coordinate";
    }

    private Image createImageFromString(String text) {
        Text textNode = new Text(text);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.rgb(255, 255, 255, 0.8));
        return textNode.snapshot(params, null);
    }

    public void removeFade(){
        //fade out animation
        double duration =  2_000_000_000;
        Rectangle node = getRectangle();

        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;

            @Override
            public void handle(long now) {
                if (startTime == -1) {
                    startTime = now;
                }

                double elapsed = now - startTime;
                double progress = elapsed / duration;

                if (progress >= 1.0) {
                    node.setOpacity(0);
                    
                    stop();
                } else {
                    double currentOpacity = 1 - progress;
                    node.setOpacity(currentOpacity);
                }
            }
        };
        // Start the timer
        timer.start();
        
        this.getRectangle().setDisable(true);
    }

    public void removeFly() {
        setClickable(false);
        int pixelsPerTick = 150;
        //starting position
        double startY = getRectangle().getY();
        double endY = -SceneGame.GRID_SIZE * 10;
        double deltaY = endY - startY;
        double duration = deltaY / pixelsPerTick * 1_000_000_000;
        Rectangle node = getRectangle();

        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;

            @Override
            public void handle(long now) {
                if (startTime == -1) {
                    startTime = now;
                }
                if(!isMoving){
                    double elapsed = now - startTime;
                    double progress = elapsed / duration;

                    if (progress >= 1.0) {
                        node.setLayoutY(endY);
                        stop();
                    } else {
                        double currentY = startY - deltaY * progress;
                        node.setLayoutY(currentY);
                    }
                }
            }
        };
        // Start the timer
        timer.start();
        this.getRectangle().setDisable(true);
    }


    public void move(int x, int y) {
        setClickable(false);
        int pixelsPerTick = 150;
        double startX = getRectangle().getLayoutX();
        double startY = getRectangle().getLayoutY();
        double endX = x + getRectangle().getLayoutX(); 
        double endY = y + getRectangle().getLayoutY();
        double deltaX = endX - startX;
        double deltaY = endY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double duration = distance / pixelsPerTick * 1_000_000_000;
        Rectangle node = getRectangle();

        do{
        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;

            
                @Override
                public void handle(long now) {
                    if (startTime == -1) {
                        startTime = now;
                    }
                    setMoving(true);

                    double elapsed = now - startTime;
                    double progress = elapsed / duration;

                    if (progress >= 1.0) {
                        node.setLayoutX(endX);
                        node.setLayoutY(endY);
                        setClickable(true);
                        isMoving = false;
                        stop();
                    } else {
                        double currentX = startX + deltaX * progress;
                        double currentY = startY + deltaY * progress;
                        node.setLayoutX(currentX);
                        node.setLayoutY(currentY);
                    }
                }
            };
            timer.start();
        } while (isMoving);
        // Start the timer
    }



    public void moveTP(int x, int y) {
    this.getRectangle().setLayoutX(this.getRectangle().getLayoutX() + x);
    this.getRectangle().setLayoutY(this.getRectangle().getLayoutY() + y);
    }

    public void highlight(String color) {
        this.getRectangle().setStyle("-fx-stroke: "+color+"; -fx-stroke-width: 5;");
    }

    public void unhighlight() {
        this.getRectangle().setStyle("-fx-stroke: none;");
    }
    
    public Rectangle getRectangle() {
        return rectangle;
    }

    public String getDrawing() {
        return drawing;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public Boolean getClickable() {
        return clickable;
    }

    public void setIsVisible(Boolean revealed) {
        rectangle.setOpacity(1);
        this.isVisible = revealed;
    }

    public void setClickable(Boolean clickable) {
        this.clickable = clickable;
    }

    //set isMoving 
    public void setMoving(Boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
}