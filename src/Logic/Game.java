import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GameLogic.AttackLogic;
import GameLogic.MoveLogic;
import PieceLogic.Piece;
import PieceLogic.PiecesCreator;
import PlayerClasses.HumanPlayer;
import PlayerClasses.Player;

public class Game {
    private boolean isOver = false;
    private boolean hasStarted = false;
    private Piece[][] board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int[][] placingBordersPlayer1 = {{0, 9}, {0, 3}}; //{start x, end x}, {start y, end y}
    private int[][] placingBordersPlayer2 = {{0, 9}, {6, 9}}; //{start x, end x}, {start y, end y}
    private ArrayList<String> availableColors = new ArrayList<>(List.of("B", "R"));

    public Game(){
        this.createBoard();
        Player player1 = this.createHumanPlayer();
        Player player2 = this.createHumanPlayer();
        this.addPlayers(player1, player2);
        this.setCurrentPlayer(player1);
    }

    public void createBoard(){
        this.board = new Piece[10][10];
        PiecesCreator.createLakes(this.board);
    }

    public Piece[][] getBoard(){
        return this.board;
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public HumanPlayer createHumanPlayer(){
        HumanPlayer h = new HumanPlayer(availableColors.get(0));
        availableColors.remove(0);
        return h;
    }

    public void addPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isOver(){
        return this.isOver;
    }

    public void setOver(){
        this.isOver = true;
    }

    public boolean hasStarted(){
        return this.hasStarted;
    }

    public void setStarted(){
        this.hasStarted = true;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public void switchCurrentPlayer(){
        if(this.currentPlayer.equals(player1)) this.currentPlayer = player2;
        else this.currentPlayer = player1;
    }

    //TODO: rewrite for gui
    public void placePiecesBlackBox(Player player){
        int counter = 0;
        ArrayList<Piece> availablePiece = player.getAvailablePieces();
    
        if(player.equals(player1)){
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

     public void placePiecesSimulation(Player player){
        int counter = 0;

        if(player.equals(player1)){
            System.out.println("p 1");
            placePiecesForPlayer(player, counter);
        }else{
            System.out.println("p 2");
            System.out.println(player.getColor());
            placePiecesForPlayer(player, counter);
            setStarted(); //start game after placing player2's pieces
        }
    }

    private void placePiecesForPlayer(Player player, int counter) {
        while(!isEveryPieceAtBeginningOnBoard(player)){
            System.out.println(player.getColor());

            int randomX = (int)(Math.random()*10);
            int randomY = (int)(Math.random()*10);
            int[] targetPosition = new int[]{randomX,randomY};
            
            System.out.println("Targetposition: "+Arrays.toString(targetPosition));

            Piece pieceToBePlaced = player.getAvailablePieces().get(counter);

            Test.boardToASCIIArt(board,player);

            System.out.println(board[4][7].getRank());

            if(canPlaceAtPosition(targetPosition, board, player.equals(player1) ? placingBordersPlayer1 : placingBordersPlayer2)){
                
                System.out.println(Arrays.deepToString(player.equals(player1) ? placingBordersPlayer1 : placingBordersPlayer2));
                System.out.println(player.equals(player1));
                System.out.println("Can place: "+pieceToBePlaced.getRank() +" at " + Arrays.toString(targetPosition));
                placePiece(pieceToBePlaced, targetPosition, player);
                System.out.println("132");
                counter++;

            }
        }
    }


    //TODO: start using
    public void placePiece(Piece piece, int[] targetPosition, Player player) {
        piece.setPosition(targetPosition);
        board[targetPosition[0]][targetPosition[1]] = piece;
        player.piecePlaced(piece);
        System.out.println(145);
    }

    //TODO: check & start using
    public static boolean canPlaceAtPosition(int[] targetPosition, Piece[][] board, int[][] placingBorders) {
        System.out.println(Arrays.toString(targetPosition));
        if(board[targetPosition[0]][targetPosition[1]] != null) {
            System.out.println(1);
            return false;
        } else 
        if(targetPosition[1] < placingBorders[0][0] || targetPosition[1] > placingBorders[0][1]) { //x,x
            System.out.println(2);

            return false;
        } else if(targetPosition[0] < placingBorders[1][0] || targetPosition[0] > placingBorders[1][1]) { //y,y
            System.out.println(3);

            return false;
        }
        return true;
    }

    //TODO: check & start using
    public boolean isEveryPieceAtBeginningOnBoard(Player player) {
        for(int n : player.getPiecesToBePlacedAmount()){
            if(n > 0) return false;
        }
        return true;
    }

    public void checkWinner() {
        if(player1.isWinner()){
            System.out.println(player1.getColor()+" is winner!");
            this.setOver();
        }            
        else if(player2.isWinner()){
            System.out.println(player2.getColor()+" is winner!");
            this.setOver();
        }
    }

    public void makeAMove(int currY, int currX, Piece currPiece, int[] targetPosition) {
        boolean canMove = MoveLogic.canMove(currPiece, targetPosition, board, currentPlayer.getColor());
        if(canMove){
            MoveLogic.move(currPiece, targetPosition, this.getBoard());
            this.switchCurrentPlayer(); 
            System.out.println("can move");
    
        }else{
            System.out.println("can't move");
            System.out.println("options: ");
            Game.showAvailablePositions(this, currPiece);
            System.out.println();
        }
    
        int[] defenderPosition = new int[]{targetPosition[0], targetPosition[1]};
        int[] attackerPosition = new int[]{currX,currY};
        boolean canAttack = AttackLogic.canAttack(currPiece, board[targetPosition[0]][targetPosition[1]], board, currentPlayer.getColor());
        if(canAttack){
            System.out.println("can attack");
            AttackLogic.battle(board, attackerPosition, defenderPosition, currentPlayer, currentPlayer.equals(player1) ? player2 : player1);
            this.switchCurrentPlayer();
        }
        else{
            System.out.println("can't attack");
        }
    }

    static void showAvailablePositions(Game game, Piece currPiece) {
        if(currPiece == null || currPiece.getRank() == -1){
            return;
        }
    
        ArrayList<Integer[]> positions = MoveLogic.returnPossiblePositions(currPiece.getPosition(), game.getBoard());
        
        for (Integer[] p : positions) {
            System.out.print(Arrays.toString(new int[]{p[1], p[0]}));
        }
    }

    
}
