package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;

import Logic.PieceLogic.Piece;
import Logic.Tester.Game;

/**
 * The BaselinePlayer class represents a baseline agent in a AI vs AI or player vs AI game
 * It is a subclass of the Player class.
 * @author Group 7
 * @version 1 
 */
public class BaselinePlayer extends Player {
    /**
     * constructs a baseline agent Player object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public BaselinePlayer(String color) {
        super(color);
    }

    public BaselinePlayer(String color, ArrayList<Piece> pieces, int[] deadPiecesAmount, int[] availablePiecesAmount, ArrayList<Piece> availablePieces, int[] piecesToBePlacedAmount, boolean isWinner) {
        super(color, pieces, deadPiecesAmount, availablePiecesAmount, availablePieces, piecesToBePlacedAmount, isWinner);
    }

    @Override
    public boolean IsPlayable(){
        return false;
    }

    
    public Player copyPlayer() {
        int[] deadCopy = deadPiecesAmount.clone();
        int[] availablePiecesAmountCopy = availablePiecesAmount.clone();
        ArrayList<Piece> piecesCopy = super.copyPieces();
        ArrayList<Piece> availableCopy = copyAvailablePieces();
        return new BaselinePlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }

    @Override
    public int[][] getNextMove(Game game) {
        int[] movablePosition = getRandomMovablePosition(game);
        int[] move = game.getCurrentPlayer().getRandomMove(game, movablePosition);
        Piece pieceMovablePosition = game.getBoard()[movablePosition[0]][movablePosition[1]];

        try{
            System.out.println();
            System.out.println(Arrays.toString(movablePosition) + " -> " + Arrays.toString(move) + ", " + pieceMovablePosition.toString());
        }catch(NullPointerException npe){
            System.out.println(Arrays.toString(move) + " " + "null");
        }

        return new int[][]{movablePosition, move};

    }

}