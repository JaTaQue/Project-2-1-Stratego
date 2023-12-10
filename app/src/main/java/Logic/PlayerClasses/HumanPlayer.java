package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Logic.PieceLogic.Piece;
import Logic.Tester.Game;

/**
 * The HumanPlayer class represents a human player in a player vs player or player vs AI game
 * It is a subclass of the Player class.
 * @author Group 7
 * @version 1 
 */
public class HumanPlayer extends Player {
    /**
     * constructs a HumanPlayer object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public HumanPlayer(String color) {
        super(color);
    }

    public HumanPlayer(String color, ArrayList<Piece> pieces, int[] deadPiecesAmount, int[] availablePiecesAmount, ArrayList<Piece> availablePieces, int[] piecesToBePlacedAmount, boolean isWinner) {
        super(color, pieces, deadPiecesAmount, availablePiecesAmount, availablePieces, piecesToBePlacedAmount, isWinner);
    }

    @Override
    public boolean IsPlayable(){
        return true;
    }

    public HumanPlayer copyPlayer() {
        int[] deadCopy = deadPiecesAmount.clone();
        int[] availablePiecesAmountCopy = availablePiecesAmount.clone();
        ArrayList<Piece> piecesCopy = super.copyPieces();
        ArrayList<Piece> availableCopy = copyAvailablePieces();
        return new HumanPlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }

    @Override
    public int[][] getNextMove(Game game) {
        Scanner scanner = new Scanner(System.in);
        int currY = scanner.nextInt(); 
        int currX = scanner.nextInt();

        int targY = scanner.nextInt();  
        int targX = scanner.nextInt();  

        Piece currPiece = game.getBoard()[currX][currY];
        int[] currentPosition = new int[]{currX, currY};
        int[] targetPosition = new int[]{targX, targY};

        try{
            System.out.println();
            System.out.println(Arrays.toString(new int[]{currY, currX}) + " -> " + Arrays.toString(new int[]{targetPosition[1], targetPosition[0]}) + ", " + currPiece.toString());
        }catch(NullPointerException npe){
            System.out.println(Arrays.toString(targetPosition) + " " + "null");
        }

        scanner.close();

        return new int[][]{currentPosition, targetPosition};
    }

}
