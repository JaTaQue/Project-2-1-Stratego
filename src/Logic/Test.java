import PieceLogic.*;
import PlayerClasses.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventListener;
import java.util.Scanner;
import java.util.stream.Collector;

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

        // game.getBoard()[4][9]=game.getBoard()[6][3];
        // game.getBoard()[4][9].setPosition(new int[]{4,9});

        // System.out.println( game.getBoard()[4][9].toString());
        // System.out.println( game.getBoard()[3 ][9].toString());


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

            try{
                System.out.println(Arrays.toString(new int[]{targetPosition[1], targetPosition[0]}) + " " + currPiece.toString());
            }catch(NullPointerException npe){
                System.out.println(Arrays.toString(targetPosition) + " " + "null");
            }

            

        

            boolean canMove = MoveLogic.canMove(currPiece, targetPosition, game.getBoard(), game.getCurrentPlayer().getColor());
            if(canMove){
                MoveLogic.move(currPiece, targetPosition, game.getBoard());
                game.switchCurrentPlayer(); 
                System.out.println("can move");

            }else{
                System.out.println("can't move");
                System.out.println("options: ");
                showAvailablePositions(game, currPiece);
                System.out.println();
            }

            int[] defenderPosition = new int[]{targetPosition[0], targetPosition[1]};
            int[] attackerPosition = new int[]{currX,currY};
            boolean canAttack = AttackLogic.canAttack(currPiece, game.getBoard()[targetPosition[0]][targetPosition[1]], game.getBoard());
            if(canAttack){
                System.out.println("can attack");
                AttackLogic.battle(game.getBoard(), attackerPosition, defenderPosition, game.getCurrentPlayer(), game.getCurrentPlayer().equals(player1) ? player2 : player1);
                game.switchCurrentPlayer();
            }
            else{
                System.out.println("can't attack");
            }

            //TESTED: canMove, canScoutMove, availablePositions


            if(player1.isWinner()){
                System.out.println(player1.getColor()+" is winner!");
                game.setOver();
            }            
            else if(player2.isWinner()){
                System.out.println(player2.getColor()+" is winner!");
                game.setOver();
            }     
            
        }  
    }

    private static void showAvailablePositions(Game game, Piece currPiece) {
        if(currPiece == null || currPiece.getRank() == -1){
            return;
        }

        ArrayList<Integer[]> positions = MoveLogic.returnPossiblePositions(currPiece.getPosition(), game.getBoard());
        
        for (Integer[] p : positions) {
            System.out.print(Arrays.toString(new int[]{p[1], p[0]}));
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
