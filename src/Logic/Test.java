import PieceLogic.*;
import PlayerClasses.*;

import java.util.Arrays;
import java.util.Scanner;

import GameLogic.*;

public class Test {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_LAKE = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game();

        System.out.println();

        boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

        System.out.println();

        while(!game.hasStarted()){ 
            System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");
            scanner.nextLine();     

            //game.placePiecesBlackBox(game.getCurrentPlayer());
            game.placePiecesSimulation(game.getCurrentPlayer());

            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());
            
            System.out.println();
            game.switchCurrentPlayer(); 

        }

        System.out.println("GAME ON\n");

        while(!game.isOver()){
            System.out.println("\n\nCurrent player: " + game.getCurrentPlayer().getColor()+"\n");
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

            int currY = scanner.nextInt(); 
            int currX = scanner.nextInt();

            int targY = scanner.nextInt();  
            int targX = scanner.nextInt();  

            Piece currPiece = game.getBoard()[currX][currY];
            int[] targetPosition = new int[]{targX, targY};

            try{
                System.out.println(Arrays.toString(new int[]{currY, currX}) + "->" + Arrays.toString(new int[]{targetPosition[1], targetPosition[0]}) + " " + currPiece.toString());
            }catch(NullPointerException npe){
                System.out.println(Arrays.toString(targetPosition) + " " + "null");
            }

            game.makeAMove(currY, currX, currPiece, targetPosition);

            game.checkWinner();     
            
        }  

        //when game is done it prints the final board one more time
        boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());
    }

    public static void boardToASCIIArt(Piece[][] board, Player currentPlayer){
        if(currentPlayer.getColor().equals("R")){
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

    private static void printTile(Piece[][] board, int i, int j) {
        try{
            int rank = board[i][j].getRank();
            String symb = checkRank(rank);
            if(board[i][j].getColor().equals("R")){
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
