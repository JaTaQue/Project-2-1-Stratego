import PieceLogic.*;
import PlayerClasses.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
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
        game.createBoard();
        PlayerInterface player1 = game.createHumanPlayer("Blue");
        PlayerInterface player2 = game.createHumanPlayer("Red");
        game.addPlayers(player1, player2);
        game.setCurrentPlayer(player1);

        System.out.println();

        boardToASCIIArt(game.getBoard(), game.getCurrentPlayer(), player2);

        System.out.println();

        while(!game.hasStarted()){
            System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");
            scanner.nextLine();     
            game.placePieces(game.getCurrentPlayer());
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer(), player2);
            
            System.out.println();
            game.switchCurrentPlayer(); 
        }

        System.out.println("GAME ON\n");

        while(!game.isOver()){
            System.out.println("\n\nCurrent player: " + game.getCurrentPlayer().getColor()+"\n");
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer(), player2);

            int currY = scanner.nextInt(); 
            int currX = scanner.nextInt();

            int targY = scanner.nextInt();  
            int targX = scanner.nextInt();  

            Piece currPiece = game.getBoard()[currX][currY];
            int[] targetPosition = new int[]{targX, targY};

            System.out.println(Arrays.toString(targetPosition) + " " + currPiece.toString());
            boolean canMove = MoveLogic.canMove(currPiece, targetPosition, game.getBoard());
            if(canMove){
                MoveLogic.move(currPiece, targetPosition, game.getBoard());
                game.switchCurrentPlayer(); 
            }else{
                System.out.println("can't move");
            }



            
            
        }  
    }

    public static void boardToASCIIArt(Piece[][] board, PlayerInterface currentPlayer, PlayerInterface player2){
        if(currentPlayer.getColor().equals(player2.getColor())){
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
