package Logic.Tester;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PieceLogic.PiecesCreator;
import Logic.PlayerClasses.RandomPlayer;
import Logic.PlayerClasses.HumanPlayer;
import Logic.PlayerClasses.Player;

/**
 * The Game class stores and manages the state and actions in the game environment
 * It connects the GameLogic classes, is used by the console app Test and the GUI Controller
 * @author Group 7
 * @version 1
 */
public class Game {
    private boolean isOver = false;
    private boolean hasStarted = false;
    public Piece[][] board;
    public Player player1;
    private Player player2;
    private Player currentPlayer;
    public final int[][] placingBordersPlayer1 = {{0, 9}, {0, 3}}; //{start x, end x}, {start y, end y}
    public final int[][] placingBordersPlayer2 = {{0, 9}, {6, 9}}; //{start x, end x}, {start y, end y}
    private static final ArrayList<String> availableColors = new ArrayList<>(List.of("Blue", "Red"));

    /**
     * constructs a Game object, creating and initializing the game board and player instances
     * @author Group 7
     */
    public Game(Player player1, Player player2){
        this.createBoard();
        this.addPlayers(player1, player2);
        this.setCurrentPlayer(player1);
    }

    public static Game PlayerVsPlayer(){
        Player player1 = createHumanPlayer();
        Player player2 = createHumanPlayer();
        return new Game(player1, player2);
    }

    public static Game PlayerVsAI(){
        Player player1 = createHumanPlayer();
        Player player2 = createBaselinePlayer();
        return new Game(player1, player2);
    }

    public static Game AIVsAI(){
        Player player1 = createBaselinePlayer();
        Player player2 = createBaselinePlayer();
        return new Game(player1, player2);
    }

    /**
     * initializes the game board with a 10 by 10 grid, 
     * creates and places the lake pieces on it
     * @author Group 7
     */
    public void createBoard(){
        this.board = new Piece[10][10];
        PiecesCreator.createLakes(this.board);
    }

    /**
     * returns the game board
     * @author Group 7
     * @version 1
     * @return the 2D array representing the game board with pieces
     */
    public Piece[][] getBoard(){
        return this.board;
    }

    /**
     * creates a new HumanPlayer object with a fixed available color: 
     * "B" or Blue for player1, "R" or Red for player2
     * @author Group 7
     * @version 1
     * @return a new HumanPlayer
     */
    public static HumanPlayer createHumanPlayer(){
        HumanPlayer h = new HumanPlayer(availableColors.remove(0));
        return h;
    }

     /**
     * creates a new BaselinePlayer object with a fixed available color: 
     * "B" or Blue for player1, "R" or Red for player2
     * @author Group 7
     * @version 1
     * @return a new BaselinePlayer
     */
    public static RandomPlayer createBaselinePlayer(){
        RandomPlayer b = new RandomPlayer(availableColors.remove(0));
        return b;
    }

    /**
     * adds the specified players to the game by initializing the player fields
     * @author Group 7
     * @version 1
     * @param player1 the first player
     * @param player2 the second player
     */
    public void addPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * checks whether the game is over
     * @author Group 7
     * @version 1
     * @return true if the game is over, false otherwise
     */
    public boolean isOver(){
        return this.isOver;
    }

    /**
     * sets the game as over
     * @author Group 7
     * @version 1
     */
    public void setOver(){
        this.isOver = true;
    }

    /**
     * checks whether the game has started
     * @author Group 7
     * @version 1
     * @return true if the game has started, false otherwise
     */
    public boolean hasStarted(){
        return this.hasStarted;
    }

    /**
     * sets the game as started
     * @author Group 7
     * @version 1
     */
    public void setStarted(){
        this.hasStarted = true;
    }

    /**
     * returns the current player
     * @author Group 7
     * @version 1
     * @return player whose turn it currently is
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * sets the current player
     * @author Group 7
     * @version 1
     * @param player the player to be set as the current player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    /**
     * switches the current player to the other player
     * @author Group 7
     * @version 1
     */
    public void switchCurrentPlayer(){
        if(this.currentPlayer.equals(player1)) this.currentPlayer = player2;
        else this.currentPlayer = player1;
    }

    /**
     * places the pieces for a player following a set order
     * @author Group 7
     * @version 1
     * @param player the player for whom pieces are being placed
     */    
    public void placePiecesBlackBox(Player player){
        int counter = 0;
        ArrayList<Piece> availablePiece = player.getAvailablePieces();
    
        if(player.equals(player2)){
            for (int y = placingBordersPlayer1[1][0]; y <= placingBordersPlayer1[1][1]; y++) {
                for (int x = placingBordersPlayer1[0][0]; x <= placingBordersPlayer1[0][1]; x++) {
                    board[y][x]=availablePiece.get(40-1-counter);
                    availablePiece.get(40-1-counter).setPosition(new int[]{y, x});
                    counter++; 
                }
            }
        }else{
            for (int y = placingBordersPlayer2[1][1]; y >= placingBordersPlayer2[1][0]; y--) {
                for (int x = placingBordersPlayer2[0][0]; x <= placingBordersPlayer2[0][1]; x++) {
                    board[y][x]=availablePiece.get(counter);
                    availablePiece.get(counter).setPosition(new int[]{y, x});
                    counter++;
                }
            }
            setStarted(); //start game after placing player2's pieces
        }
    }

    /**
     * checks whether a piece can be placed at the specified target position on the board
     * @author Group 7
     * @version 1
     * @param targetPosition the target  on the board where the piece is to be placed
     * @param board shows all the pices on the board and where they are as a 2D array of Pieces
     * @param placingBorders the placement borders specifying where pieces can be placed 
     * @return true if the piece can be placed at the target position, false otherwise
     */
    public static boolean canPlaceAtPosition(int[] targetPosition, Piece[][] board, int[][] placingBorders) {
        if(board[targetPosition[0]][targetPosition[1]] != null) {
            return false;
        } else 
        if(targetPosition[1] < placingBorders[0][0] || targetPosition[1] > placingBorders[0][1]) { //x,x

            return false;
        } else if(targetPosition[0] < placingBorders[1][0] || targetPosition[0] > placingBorders[1][1]) { //y,y
            return false;
        }
        return true;
    }

    /**
     * checks whether the player has places all their pieces onto the board
     * @author Group 7
     * @version 1
     * @param player the player for whom we want to check
     * @return true if all pieces are on the board, false otherwise
     */
    public boolean isEveryPieceAtBeginningOnBoard(Player player) {
        for(int n : player.getPiecesToBePlacedAmount()){
            if(n > 0) return false;
        }
        return true;
    }

    /**
     * Checks for a winner and sets the game as over if a winner is found
     * @author Group 7
     * @version 1
     */
    public void checkWinner() {
        if(player1.isWinner()){
            // System.out.println(player1.getColor()+" is the winner!");
            this.setOver();
        }            
        else if(player2.isWinner()){
            // System.out.println(player2.getColor()+" is the winner!");
            this.setOver();
        }
        else if(Player.isSomeoneStuck(board).equals("Both")){
            this.setOver();
        }
    }

    /**
     * Performs a move in the game, including movement and attacks
     * @author Group 7
     * @version 1
     * @param currentPosition the current position of the piece to be moved
     * @param currPiece the piece to be moved
     * @param targetPosition the target position where the piece is to be moved
     */
    public void makeAMove(int[] currentPosition,Piece currPiece, int[] targetPosition) {
        int currX = currentPosition[0];
        int currY = currentPosition[1];
        boolean canMove = getCanMove(currPiece, targetPosition);
        if(canMove){
            MoveLogic.move(currPiece, targetPosition, this.getBoard());
            this.switchCurrentPlayer(); 
            // System.out.println("can move");
    
        }else{
            //System.out.println("can't move");
            //System.out.println("options: ");
            //Game.showAvailablePositions(this, currPiece);
        }
    
        int[] defenderPosition = new int[]{targetPosition[0], targetPosition[1]};
        int[] attackerPosition = new int[]{currX,currY};
        boolean canAttack = getCanAttack(currPiece, targetPosition);
        if(canAttack){
            //System.out.println("can attack");
            AttackLogic.battle(board, attackerPosition, defenderPosition, currentPlayer, currentPlayer.equals(player1) ? player2 : player1);
            this.switchCurrentPlayer();
        }
        else{
            //System.out.println("can't attack");
        }
        checkWinner();
    }

    /**
     * checks if a piece can attack another piece at a specified position
     * @author Group 7
     * @version 1
     * @param currPiece the piece initiating the attack
     * @param targetPosition the target position of the potential attack
     * @return true if the attack is possible, false otherwise
     */
    public boolean getCanAttack(Piece currPiece, int[] targetPosition) {
        boolean canAttack = AttackLogic.canAttack(currPiece, board[targetPosition[0]][targetPosition[1]], board, currentPlayer.getColor());
        return canAttack;
    }
    
    /**
     * checks if a piece can move to a specified position
     * @author Group 7
     * @version 1
     * @param currPiece the piece to be moved
     * @param targetPosition the target position for the move
     * @return true if the move is possible, false otherwise
     */
    public boolean getCanMove(Piece currPiece, int[] targetPosition) {
        return MoveLogic.canMove(currPiece, targetPosition, board, currentPlayer.getColor());
    }

    /**
     * Shows available positions for a piece.
     * @author Group 7
     * @version 1
     * @param game the game object
     * @param currPiece the piece for which available positions are to be shown
     */
    static void showAvailablePositions(Game game, Piece currPiece) {
        if(currPiece == null || currPiece.getRank() == -1){
            return;
        }
    
        ArrayList<int[]> positions = game.getAvailablePositions(currPiece);
        
        for (int[] p : positions) {
            System.out.print(Arrays.toString(new int[]{p[1], p[0]}));
        }
    }

    /**
     * retrieves available moves for a piece
     * @author Group 7
     * @version 1
     * @param piece the piece for which available positions are to be retrieved
     * @return a list of available positions as integer arrays
     */

    
    public ArrayList<int[]> getAvailablePositions(Piece piece) {
        ArrayList<int[]> positions = new ArrayList<>();
        if(piece.getColor().equals(getCurrentPlayer().getColor()))
            positions = MoveLogic.returnPossiblePositions(piece.getPosition(), board);
        return positions;
    }


    public ArrayList<int[]> getBuildupPositions(Player currentPlayer) {
        ArrayList<int[]> tiles = new ArrayList<>();
        //loop through the board
        
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                //if the piece is not null
                if(board[i][j] != null){
                    //if the piece is of the current player
                        if(board[i][j].getColor().equals(currentPlayer.getColor())){
                            //add to Integer[] arraylist
                            tiles.add(new int[]{i,j});
                        }
                    }
                }
            }
            return tiles;
        }

    /**
     * @param coords
     * @return surroundingPieces
     */
    public ArrayList<Piece> getSurroundingPieces(int[] coords) {
        ArrayList<Piece> surroundingPieces = new ArrayList<>();
        int x = coords[0];
        int y = coords[1];

        Piece current = board[x][y];
        Piece left;
        Piece right;
        Piece up;
        Piece down;
        //left
        if(x > 0 && board[x-1][y] != null) {
            left = board[x-1][y];
            if(left.getRank() != -1 && left.getColor().equals(currentPlayer.getColor()))
                surroundingPieces.add(left);}
        //right
        if(x < 9 && board[x+1][y] != null) {
            right = board[x+1][y];
            if(right.getRank() != -1 && right.getColor().equals(currentPlayer.getColor()))
                surroundingPieces.add(right);}
        //up
        if(y > 0 && board[x][y-1] != null){
            up = board[x][y-1];
            if(up.getRank() != -1 && up.getColor().equals(currentPlayer.getColor()))
                surroundingPieces.add(up);}
        //down
        if(y < 9 && board[x][y+1] != null){
            down = board[x][y+1];
            if(down.getRank() != -1 && down.getColor().equals(currentPlayer.getColor()))
                surroundingPieces.add(down);}
        //current
        if(!surroundingPieces.isEmpty())
            surroundingPieces.add(current);
        
        return surroundingPieces;
    }

    /**
     * retrieves the enemy player of the current player
     * @author Group 7
     * @version 1
     * @return the enemy player of the current player
     */
    public Player getEnemyPlayer() {
        if(this.currentPlayer.equals(player1)) return player2;
        else if(this.currentPlayer.equals(player2)) return player1;
        else return null;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setBoard(Piece[][] currBoard) {
        this.board = currBoard;
    }

    public Player getPlayer1() {
        return player1;
    }
}

