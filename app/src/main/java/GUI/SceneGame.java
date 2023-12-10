package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import GUI.Grid.GridHandler;
import GUI.Grid.GridStratego;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;
import Logic.Tester.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class SceneGame implements Initializable {

    @FXML
    public AnchorPane pane;

    @FXML
<<<<<<< HEAD
    public Label laber;
=======
    public String gameMode;
>>>>>>> 0b27329ae4f209cc566813e866b7e3c84a0117f3

    public static final int GRID_SIZE = 75;
    private String mode;
    public void setmode(String ar){
        mode = ar;
    }

    public void setName(String name) {
        laber.setText(name);
    }

    Game game;
    Component[][] boardGUI = new Component[10][10];
    private int [] currentXY;
    Boolean selected = false;
    Boolean started = false;
    int turn=1;

    private GridStratego draggableMakerGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        draggableMakerGrid = new GridStratego(pane.getPrefWidth(), pane.getPrefHeight(), GRID_SIZE, pane);

        GridHandler backgroundGridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), GRID_SIZE, pane);
        backgroundGridHandler.updateGrid();
        
        setGame("Player vs Player");
        
        //playerTurnDisplay.setText(game.getCurrentPlayer().getColor() + " TURN");

        //if(game.getCurrentPlayer().IsPlayable())

        //listen for mouse clicks
        pane.setOnMouseClicked(this::playHuman);
    }

    private void setGame(String gameMode) {
        //create a new game based on the game mode
        switch (gameMode) {
            case "Player vs Player":
                game = Game.PlayerVsPlayer();
                break;
            case "Player vs AI":
                game = Game.PlayerVsAI();
                break;
            case "AI vs AI":
                game = Game.AIVsAI();
                break;
        }

        //place the pieces on the board
        for (int i = 0; i < 2; i++) {
            game.placePiecesSimulation((game.getCurrentPlayer()));
            //game.placePiecesBlackBox(game.getCurrentPlayer());
            game.switchCurrentPlayer();
        }

        //place the components on the GUI board
        setGUI();
        //shohw the pieces of the current player and hide the pieces of the enemy player
        switchGUI();
    }

    private void setGUI() {
        Piece[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j]; 
                if (piece != null) {
                    Component component = new Component( j, i);
                    //lake
                    if(piece.getRank()==-1)
                        component.draw("lake");
                    boardGUI[i][j] = component;
                    pane.getChildren().add(component.getRectangle());
                }
                
                //coordinates
                if(i==0 || j==0){
                    Component coordinate = new Component(j, i);
                    coordinate.drawNumber(i+j);
                    pane.getChildren().add(coordinate.getRectangle());
                }
            }
        }
    }

    private void switchGUI(){
        //get the current player
        Player currentPlayer = game.getCurrentPlayer();
        Player enemyPlayer = game.getEnemyPlayer();
        
        //hide the pieces of the enemy player
        hideGUI(enemyPlayer);
        
        //display an alert to confirm the move
        nextGUI();
        
        //show the pieces of the current player
        showGUI(currentPlayer);
    }

    private void nextGUI() {
        String phase = started ? "turn" : "placement";
        String color = game.getCurrentPlayer().getColor();
        //create an alert
        Alert alertNext = new Alert(Alert.AlertType.INFORMATION);

        Object scene = pane.getScene();
        if(scene!=null){
            alertNext.initOwner(pane.getScene().getWindow());
        }
        //title
        alertNext.setTitle("Next");
        //header
        alertNext.setHeaderText(color + "'s "+ phase);
        //content
        alertNext.setContentText("Press ENTER to continue");
        //graphic
        Component blankIcon = new Component( 0, 0);
        blankIcon.draw(0,color);
        alertNext.setGraphic(blankIcon.getRectangle());
        //size
        alertNext.getDialogPane().setPrefSize(200, 100);

        
        //show the alert
        alertNext.showAndWait();
        
    }

    private void hideGUI(Player player){
        ArrayList<int[]> playerTiles = getTiles(player);
        for(int[] tile : playerTiles){
            Component component = boardGUI[tile[0]][tile[1]];
            if(!component.getIsVisible()){
                component.draw(0,player.getColor());
            }
        }
    }

    private void showGUI(Player player){
        ArrayList<int[]> playerTiles = getTiles(player);
        for(int[] tile : playerTiles){
            Component component = boardGUI[tile[0]][tile[1]];
            Piece piece = game.getBoard()[tile[0]][tile[1]];
            component.draw(piece.getRank(),piece.getColor());
        }
    }

    private void click() {
        selected = !selected;
        // selected is true: player turn ongoing
        // selected is false: player turn ended
        if(!selected && started){
            //check if player is AI
            if(!game.getCurrentPlayer().IsPlayable()){
                
                int[] movablePosition = game.getCurrentPlayer().getRandomMovablePosition(game);
                int[] move = game.getCurrentPlayer().getRandomMove(game, movablePosition);
                Piece pieceMovablePosition = game.getBoard()[movablePosition[0]][movablePosition[1]];
                
                turnGUI(movablePosition, pieceMovablePosition, move);
                selected = !selected;
            }
        }
    }

    private void playHuman(MouseEvent mouseEvent) {
        double mouseAnchorX = mouseEvent.getX();
        double mouseAnchorY = mouseEvent.getY();

        mouseDebug(mouseAnchorX, mouseAnchorY);


        //first click
        if(!selected){
            //invert coordinates for array indexing
            //noinspection SuspiciousNameCombination
            currentXY = draggableMakerGrid.getClickCoordinates(mouseAnchorY, mouseAnchorX);
            select(currentXY);
        }
        //second click
        else{
            //invert coordinates for array indexing
            //noinspection SuspiciousNameCombination
            int[] targetXY = draggableMakerGrid.getClickCoordinates(mouseAnchorY, mouseAnchorX);
            deselect(currentXY,targetXY);
        }
    }

    private void select(int[] currentXY) {
        Piece currentPiece;
        Component currentComponent;

        ArrayList<int[]> currentTiles;
        ArrayList<int[]>  targetTiles;

        currentComponent = boardGUI[currentXY[0]][currentXY[1]];
        currentPiece = game.getBoard()[currentXY[0]][currentXY[1]];
        currentTiles = getTiles(game.getCurrentPlayer());
        
        //check if tile is not empty, component is clickable
        if(currentPiece != null && currentComponent.getClickable() ){
            //current player's piece
            if(currentPiece.getColor().equals(game.getCurrentPlayer().getColor())){
                targetTiles = getTiles(currentPiece);
                if(!started){
                    highlightTiles(currentTiles, "green");
                    highlightTiles(targetTiles, "blue");
                }
                else
                    highlightTiles(targetTiles, "red");
                click();
            }
        }
    }

    private void deselect(int[] currentXY, int[] targetXY) {
        Piece currentPiece = game.getBoard()[currentXY[0]][currentXY[1]];
        ArrayList<int[]> targetTiles = getTiles(currentPiece);
        dehighlightTiles(targetTiles);
        
        if(started)
            turnGUI(currentXY, currentPiece, targetXY);
        else{
            ArrayList<int[]> currentTiles = getTiles(game.getCurrentPlayer());
            dehighlightTiles(currentTiles);
            swapGUI(currentXY,targetXY);
            confirmGUI(currentPiece, targetXY);
        }
        
        click();
    }

    private void stop() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(pane.getScene().getWindow());
        //title
        alert.setTitle("Game Over");

        //header
        String winner = game.getEnemyPlayer().getColor();
        alert.setHeaderText(winner +" wins!");
        
        //graphic
        Component FlagComponent = new Component(0, 0);
        FlagComponent.draw(11,winner);
        alert.setGraphic(FlagComponent.getRectangle());
        showGUI(game.getCurrentPlayer());

        //content
        alert.setContentText("Turns: "+turn);
        
        //show the alert
        //alert.showAndWait();
        //switch to start screen
        switchToStartScreen();

        //exit the program
        //System.exit(0);
        
    }

    private ArrayList<int[]> getTiles(Player currentPlayer) {
        ArrayList<int[]> tiles = game.getBuildupPositions(currentPlayer);
        //remove the currentXY Integer[] from tiles
        return tiles;
    }

    private ArrayList<int[]> getTiles(Piece piece) {
        ArrayList<int[]> tiles = game.getAvailablePositions(piece);
        if (piece != null){
            int[] currentTile = piece.getTile();
            tiles.add(currentTile);
            }
        return tiles;
        }

    private void highlightTiles(ArrayList<int[]> currentTiles, String color) {
        Component component;
        //get the available moves
        //loop through the available moves
        for(int[] tile : currentTiles){
            //create a component at the move if there is no component there already
            if(game.getBoard()[tile[0]][tile[1]] == null){
                component = new Component(tile[1], tile[0]);
                //draw the component
                component.draw("tile");
                component.setClickable(false);
                //add the component to the pane
                pane.getChildren().add(component.getRectangle());
                //add the component to GUI_board
                boardGUI[tile[0]][tile[1]] = component;
            }
            else
                component = boardGUI[tile[0]][tile[1]];
            
            //highlight the component
            component.highlight(color);
        }
        
    }

    private void dehighlightTiles(ArrayList<int[]> targetTiles) {
        //get the available moves
        
        //loop through the available moves
        for(int[] tile : targetTiles){
            if(game.getBoard()[tile[0]][tile[1]] == null){
                //delete the highlighted piece from the pane
                pane.getChildren().remove(boardGUI[tile[0]][tile[1]].getRectangle());
                boardGUI[tile[0]][tile[1]] = null;
            }
            else{
                //unhighlight the component
                boardGUI[tile[0]][tile[1]].unhighlight();
            }
        }
    }

    private void switchToStartScreen()  {
        Stage stage;
        Scene scene;
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScreenHome.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage = SceneHome.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void turnGUI(int[] currentXY, Piece currentPiece, int[] targetXY){
        Piece targetPiece = game.getBoard()[targetXY[0]][targetXY[1]];
        
        boolean isMove = game.getCanMove(currentPiece, targetXY);
        boolean isAttack = game.getCanAttack(currentPiece, targetXY);
        boolean isConfirm = isAttack || isMove;

        //logic
        game.makeAMove(currentXY, currentPiece, targetXY);
        
        
        //GUI
        if(isConfirm)
            moveGUI(currentXY, targetXY);
        if(isAttack)
            attackGUI(currentPiece, targetPiece, currentXY);
        //check if the game is over
        if(game.isOver())
            stop();
        
        if(isConfirm){
            switchGUI();
            turn++;
        }

    }

    private void confirmGUI(Piece currentPiece, int[] targetXY) {
        boolean isMove = game.getCanMove(currentPiece, targetXY);
        boolean isAttack = game.getCanAttack(currentPiece, targetXY);
        boolean isConfirm = isAttack || isMove;
        if (isConfirm) {
            turn++;
            if(turn>2)
                started = true;
            game.switchCurrentPlayer();
            switchGUI();
        }
    }

    private void moveGUI(int[] currentXY, int[] targetXY) {
        int currentX = currentXY[0];
        int currentY = currentXY[1];
        int targetX = targetXY[0];
        int targetY = targetXY[1];

        int diffX = (targetX - currentX) * GRID_SIZE;
        int diffY = (targetY - currentY) * GRID_SIZE;

        //move the component
        Component currentComponent = boardGUI[currentX][currentY];
        Component targetComponent = boardGUI[targetX][targetY];
        //re-invert the coordinates for array indexing
        //noinspection SuspiciousNameCombination
        currentComponent.move(diffY, diffX);

        if(targetComponent == null){
            boardGUI[targetX][targetY] = currentComponent;
            boardGUI[currentX][currentY] = null;
        }
    }

    private void attackGUI(Piece currentPiece, Piece targetPiece, int[] currentXY) {
        int[] targetXY = targetPiece.getPosition();
        boolean diedTarget = targetPiece.isDead();
        boolean diedCurrent = currentPiece.isDead();
        Component currentComponent = boardGUI[currentXY[0]][currentXY[1]];
        Component targetComponent = boardGUI[targetXY[0]][targetXY[1]];

        if (diedTarget && diedCurrent) {
            currentComponent.removeFly();
            targetComponent.removeFly();
            boardGUI[currentXY[0]][currentXY[1]] = null;
            boardGUI[targetXY[0]][targetXY[1]] = null;
        } else if (diedTarget) {
            targetComponent.removeFly();
            boardGUI[targetXY[0]][targetXY[1]] = boardGUI[currentXY[0]][currentXY[1]];
            boardGUI[currentXY[0]][currentXY[1]] = null;
            boardGUI[targetXY[0]][targetXY[1]].setIsVisible(true);
        } else if (diedCurrent) {
            currentComponent.removeFly();
            boardGUI[currentXY[0]][currentXY[1]] = null;
            boardGUI[targetXY[0]][targetXY[1]].setIsVisible(true);
        }
    }
    
    private void swapGUI(int[] currentXY,int[] targetXY) {
        //check if the current piece is not null and is not an enemy piece
        Piece currentPiece = game.getBoard()[currentXY[0]][currentXY[1]];
        if(currentPiece == null || !Objects.equals(currentPiece.getColor(), game.getCurrentPlayer().getColor()))
            return;
        

        //check if the target is empty or an enemy piece
        Piece tempPiece = game.getBoard()[targetXY[0]][targetXY[1]];
        if(tempPiece == null || !Objects.equals(tempPiece.getColor(), game.getCurrentPlayer().getColor()))
            return;
        

        //swap pieces
        game.getBoard()[targetXY[0]][targetXY[1]] = currentPiece;
        game.getBoard()[currentXY[0]][currentXY[1]] = tempPiece;
        tempPiece.setPosition(currentXY);
        currentPiece.setPosition(targetXY);
        
        //swap GUI_board
        Component tempComponent = boardGUI[targetXY[0]][targetXY[1]];
        if (!tempComponent.getClickable())
            return;

        boardGUI[targetXY[0]][targetXY[1]] = boardGUI[currentXY[0]][currentXY[1]];
        boardGUI[currentXY[0]][currentXY[1]] = tempComponent;
        
        //swap the components
        boardGUI[targetXY[0]][targetXY[1]].move(
            (targetXY[1] - currentXY[1]) * GRID_SIZE, 
            (targetXY[0] - currentXY[0]) * GRID_SIZE);
        boardGUI[currentXY[0]][currentXY[1]].move(
            (currentXY[1] - targetXY[1]) * GRID_SIZE, 
            (currentXY[0] - targetXY[0]) * GRID_SIZE);
    }

    private Boolean matchGUI(Component[][] GUIboard, Piece[][] board){
        //check if GUI_board matches game.getBoard()
        //to do so, check if every piece in GUI_board is in the same position as in game.getBoard()
        //if not, return false
        //if yes, return true
        for(int i = 0; i < GUIboard.length; i++){
            for(int j = 0; j < GUIboard[i].length; j++){
                if(GUIboard[i][j] != null){
                    if(board[i][j] == null){
                        return false;
                    }
                }
                else if (GUIboard[i][j] == null){
                    if(board[i][j] != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void mouseDebug(double mouseAnchorX, double mouseAnchorY) {
        //invert coordinates for array indexing
        //noinspection SuspiciousNameCombination
        int[] clickCoordinates = draggableMakerGrid.getClickCoordinates(mouseAnchorY, mouseAnchorX);
        int[] nodeCoordinates = getNodesCoordinates(clickCoordinates);
        String componentID = getComponentID(clickCoordinates);
        String pieceID = getPieceID(clickCoordinates);
        //print a line
        System.out.println("mode: " + mode);
        System.out.println("mouseAnchorX: " + (mouseAnchorX - mouseAnchorX % GRID_SIZE));
        System.out.println("mouseAnchorY: " + (mouseAnchorY - mouseAnchorY % GRID_SIZE));
        System.out.println("nodeCoordinates: " + nodeCoordinates[0] + ", " + nodeCoordinates[1]);
        System.out.println("clickCoordinates: " + clickCoordinates[0] + ", " + clickCoordinates[1]);
        System.out.println("componentID: " + componentID);
        System.out.println("pieceID: " + pieceID);
        System.out.println("Match: "+matchGUI(boardGUI, game.getBoard()));
        System.out.println("Turn : " + turn);
        //print a line
        System.out.println("--------------------------------------------------");
    }

    private int[] getNodesCoordinates(int[] clickCoordinates) {
        Component component = boardGUI[clickCoordinates[0]][clickCoordinates[1]];
        Rectangle rectangle = component.getRectangle();
        return new int[]{(int) rectangle.getX(), (int) rectangle.getY()};
        

    }

    private String getComponentID(int[] clickCoordinates) {
        Component component = boardGUI[clickCoordinates[0]][clickCoordinates[1]];
        if(component != null){
            return component.getDrawing();
        }
        return null;
    }

    private String getPieceID(int[] coords) {
        Piece selPiece = game.getBoard()[coords[0]][coords[1]];
        String ID = "null";
        if(selPiece != null){
            ID = selPiece.getRank() + selPiece.getColor();
        }
        return ID;
    }
}