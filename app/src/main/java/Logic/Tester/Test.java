package Logic.Tester;
import java.util.Scanner;

import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.BaselinePlayer;
import Logic.PlayerClasses.RandomPlayer;
import Logic.PlayerClasses.Player;

/**
 * Blackbox testing class with a console UI
 * @author Group 7
 * @version 1
 */
public class Test {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_LAKE = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Game game = Game.PlayerVsAI();
            
            Player aiPlayer = new AIPlayer("Blue");
            game.setPlayer1(aiPlayer);
            game.setCurrentPlayer(aiPlayer);

            Player blPlayer = new RandomPlayer("Red");
            game.setPlayer2(blPlayer);

            System.out.println();

            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

            System.out.println();

            while(!game.hasStarted()){ 
                System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");
                scanner.nextLine();     

                //game.placePiecesBlackBox(game.getCurrentPlayer());
                game.getCurrentPlayer().placePiecesSimulation(game);

                boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());
                
                System.out.println();
                game.switchCurrentPlayer(); 

            }

            System.out.println("GAME ON\n");

            while(!game.isOver()){
                System.out.println("\n\nCurrent player: " + game.getCurrentPlayer().getColor()+"\n");
                boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

                int[][] nextMove = game.getCurrentPlayer().getNextMove(game);
                int[] currentPosition = nextMove[0];
                int[] targetPosition = nextMove[1];

                game.makeAMove(currentPosition, game.getBoard()[currentPosition[0]][currentPosition[1]], targetPosition);  

            }  

            //when game is done it prints the final board one more time
            System.out.println();
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());
            System.out.println("\n  ~ GAME OVER ~");
        }
    }

    /**
     * generates an ASCII art representation of the game board and prints it to the console
     * @param board the 2D array representing the game board with pieces
     * @param currentPlayer the current player whose perspective the board is shown from
     */
    public static void boardToASCIIArt(Piece[][] board, Player currentPlayer){
        if(currentPlayer.getColor().equals("Blue")){
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    printTile(board, i, j);
                }
                System.out.print(" "+i);
                System.out.println();
            }
            
            System.out.println("\n0 1 2 3 4 5 6 7 8 9\n");
        }else{
            for(int i = 9; i >= 0; i--){
                for(int j = 9; j >= 0; j--){
                    printTile(board, i, j);
                }
                System.out.print(" "+i);
                System.out.println();
            }
            System.out.println("\n9 8 7 6 5 4 3 2 1 0\n");
        }
    }

    /**
     * Prints a single tile of the game board with a piece's rank in a player's color
     * @param board the 2D array representing the game board with pieces
     * @param i the row index of the tile
     * @param j the column index of the tile
     */
    private static void printTile(Piece[][] board, int i, int j) {
        try{
            int rank = board[i][j].getRank();
            String symb = checkRank(rank);
            if(board[i][j].getColor().equals("Red")){
                System.out.print(ANSI_RED + symb + " " + ANSI_RESET);
            }else{
                System.out.print(ANSI_BLUE + symb + " " + ANSI_RESET);
            }
        }catch(NullPointerException npe){
            if(board[i][j] != null && board[i][j].getRank() == -1){
                System.out.print(ANSI_LAKE + "L " + ANSI_RESET);
            }else{
                System.out.print("* ");
            }
        }
    }

    /**
     * Checks the rank of a piece and returns the corresponding symobl
     * @param rank the rank of the piece
     * @return the symbol for the piece's rank
     */
    private static String checkRank(int rank){
        if(rank < 10){
            return String.valueOf(rank);
        }else if(rank == 10){
            return "M";
        }else if(rank == 11){
            return "F";
        }else{
            return "B";
        }
    }

}
