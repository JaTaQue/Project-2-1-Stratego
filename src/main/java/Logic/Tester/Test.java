package Logic.Tester;
import java.util.Scanner;

import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.BaselinePlayer;
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

    private static final int GAMES = 5;
    
    public static void main(String[] args) {
        int winCount = 0;
        int loseCount = 0;
        int drawCount = 0;

        int moves = 0;

        for(int i = 0; i < GAMES; i++){
            try (Scanner scanner = new Scanner(System.in)) {
                Game game = Game.PlayerVsAI();
                
                Player aiPlayer = new AIPlayer("Blue");
                game.setPlayer1(aiPlayer);
                game.setCurrentPlayer(aiPlayer);
    
                Player blPlayer = new BaselinePlayer("Red");   // don't put human !!
                game.setPlayer2(blPlayer);
    
                System.out.println();
    
                //boardToASCIIArt(game.getBoard());
    
                System.out.println();
    
                while(!game.hasStarted()){ 
                    System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");
                    // scanner.nextLine();     
    
                    //game.placePiecesBlackBox(game.getCurrentPlayer());
                    game.getCurrentPlayer().placePiecesSimulation(game);
    
                    //boardToASCIIArt(game.getBoard());
                    
                    System.out.println();
                    game.switchCurrentPlayer(); 
    
                }

                int moveNumber = 1;
    
                System.out.println("GAME ON\n");
    
                while(!game.isOver()){
                    if(game.getCurrentPlayer() == game.getPlayer1())System.out.println("\n\nMOVE #" + moveNumber++ + " GAME #" + (i+1));
                    System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");
                    boardToASCIIArt(game.getBoard());
    
                    int[][] nextMove = game.getCurrentPlayer().getNextMove(game);
                    int[] currentPosition = nextMove[0];
                    int[] targetPosition = nextMove[1];
    
                    game.makeAMove(currentPosition, game.getBoard()[currentPosition[0]][currentPosition[1]], targetPosition);  
    
                }  
    
                //when game is done it prints the final board one more time
                System.out.println();
                boardToASCIIArt(game.getBoard());
                System.out.println("\n  ~ GAME OVER ~");
                if (game.getPlayer1().isWinner()){
                    System.out.println("BLUE WON");
                    winCount++;
                } else if (game.getPlayer2().isWinner()){
                    System.out.println("RED WON");
                    loseCount++;
                }else{
                    System.out.println("DRAW");
                    drawCount++;
                }
                System.out.println();
                
                Game.getAvailableColors().add("Blue");
                Game.getAvailableColors().add("Red");
                moves += moveNumber;

            }
            System.out.println("won: \t" + winCount);
            System.out.println("lost: \t" + loseCount);
            System.out.println("draw: \t" + drawCount);
            System.out.println("moves: \t" + moves / GAMES);
        }
        
    }

    /**
     * generates an ASCII art representation of the game board and prints it to the console
     * @param board the 2D array representing the game board with pieces
     */
    public static void boardToASCIIArt(Piece[][] board){
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    printTile(board, i, j);
                }
                System.out.print(" "+i);
                System.out.println();
            }
            
            System.out.println("\n0 1 2 3 4 5 6 7 8 9\n");
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
