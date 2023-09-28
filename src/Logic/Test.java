import PieceLogic.*;
import PlayerClasses.*;

import java.util.ArrayList;
import java.util.Arrays;

import GameLogic.*;

public class Test {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_LAKE = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static void main(String[] args) {
        Board board = new Board();
        board.createHumanPlayer("Blue");
        board.createHumanPlayer("Red");
        

        int counter = 0;

        ArrayList<Piece> availablePiece = board.getAvailablePieces();
        System.out.println(availablePiece.get(0).getColor());

   
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                board.board[i][j]=availablePiece.get(counter);
                availablePiece.get(counter).setPosition(new int[]{i, j});
                counter++;

            }
        }

        board.switchPlayers();
       

        availablePiece = board.getAvailablePieces();
        counter = 0;
        System.out.println(availablePiece.get(0).getColor());
        for (int i = 9; i > 5; i--) {
            for (int j = 0; j < 10; j++) {
                board.board[i][j]=availablePiece.get(counter);
                availablePiece.get(counter).setPosition(new int[]{i, j});
                counter++;
            }
        }
       

        System.out.println(Arrays.deepToString(board.board) + " " + board);
        toASCIIArt(board);
        
    }

    public static void toASCIIArt(Board board){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                try{
                    int rank = board.board[i][j].getRank();
                    String symb = checkRank(rank);
                    if(board.board[i][j].getColor().equals("Red")){
                        System.out.print(ANSI_RED + symb + " " + ANSI_RESET);
                    }else{
                        System.out.print(ANSI_BLUE + symb + " " + ANSI_RESET);
                    }
                }catch(NullPointerException npe){
                    if(board.board[i][j] != null && board.board[i][j].getRank() == -1){
                        System.out.print(ANSI_LAKE + "L " + ANSI_RESET);
                    }else{
                        System.out.print("* ");
                    }
                }
            }
            System.out.println();
        }
        //System.out.println(ANSI_RED + "This text is red!" );
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
