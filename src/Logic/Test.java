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

        boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

        while(!game.hasStarted()){
            game.placePieces(game.getCurrentPlayer());
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());
            game.switchCurrentPlayer(); 
        }

        System.out.println("GAME ON\n");

        while(!game.isOver()){
            System.out.println("Current player: " + game.getCurrentPlayer().getColor());

            game.switchCurrentPlayer(); 
            boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

            scanner.nextLine();
        }

    //     Board board = new Board();
    //     board.createHumanPlayer("Blue");
    //     board.createHumanPlayer("Red");
        

    //     int counter = 0;

    //     ArrayList<Piece> availablePiece = board.getAvailablePieces();
    //     System.out.println(availablePiece.get(0).getColor());

   
    //     for (int i = 0; i < 4; i++) {
    //         for (int j = 0; j < 10; j++) {
    //             board.board[i][j]=availablePiece.get(counter);
    //             availablePiece.get(counter).setPosition(new int[]{i, j});
    //             counter++;

    //         }
    //     }

    //     board.switchPlayers();
       

    //     availablePiece = board.getAvailablePieces();
    //     counter = 0;
    //     System.out.println(availablePiece.get(0).getColor());
    //     for (int i = 9; i > 5; i--) {
    //         for (int j = 0; j < 10; j++) {
    //             board.board[i][j]=availablePiece.get(counter);
    //             availablePiece.get(counter).setPosition(new int[]{i, j});
    //             counter++;
    //         }
    //     }
       

    //     System.out.println(Arrays.deepToString(board.board) + " " + board);
        
        
    }

    public static void boardToASCIIArt(Piece[][] board, PlayerInterface currentPlayer){
        if(currentPlayer.getColor().equals("Blue")){
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    printRow(board, i, j);
                }

                System.out.println();
            }
        }else{
            for(int i = 9; i >= 0; i--){
                for(int j = 9; j >= 0; j--){
                    printRow(board, i, j);
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void printRow(Piece[][] board, int i, int j) {
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
