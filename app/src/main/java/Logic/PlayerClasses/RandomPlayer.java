package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;

import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.Tester.Game;

/**
 * The RandomPlayer class represents a random agent in a AI vs AI or player vs AI game
 * It is a subclass of the Player class.
 * @author Group 7
 * @version 1 
 */
public class RandomPlayer extends Player {
    /**
     * constructs a baseline agent Player object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public RandomPlayer(String color) {
        super(color);
    }

    public RandomPlayer(String color, ArrayList<Piece> pieces, int[] deadPiecesAmount, int[] availablePiecesAmount, ArrayList<Piece> availablePieces, int[] piecesToBePlacedAmount, boolean isWinner) {
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
        return new RandomPlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }

    @Override
    public int[][] getNextMove(Game game) {
        int[] movablePosition = getRandomMovablePosition(game);
        int[] move = getRandomMove(game, movablePosition);
        Piece pieceMovablePosition = game.getBoard()[movablePosition[0]][movablePosition[1]];

        try{
            System.out.println();
            System.out.println(Arrays.toString(movablePosition) + " -> " + Arrays.toString(move) + ", " + pieceMovablePosition.toString());
        }catch(NullPointerException npe){
            System.out.println(Arrays.toString(move) + " " + "null");
        }

        return new int[][]{movablePosition, move};

    }

    public int[] getRandomMovablePosition(Game game){
        if(this.IsPlayable()){
            System.out.println("Something went wrong, playable player uses method of baselineplayer");
            return null;
        }
        ArrayList<int[]> movablePieces = getMovablePieces(game);

        if(!movablePieces.isEmpty()){
            int movablePiecesSize = movablePieces.size();
            int randomInteger = (int) (Math.random()*(movablePiecesSize-1));
            return movablePieces.get(randomInteger);
        }
        else{
            System.out.println("Something went wrong or player does not have movable pieces");
            return null;
        }
        
    }

    public int[] getRandomMove(Game game, int[] currentPosition){
        if(this.IsPlayable()){
            System.out.println("Something went wrong, playable player uses method of baselineplayer");
            return null;
        }
        
        Piece[][] board = game.getBoard();

        if(!MoveLogic.returnPossiblePositions(currentPosition, board).isEmpty()){
            int possiblePositionsSize = MoveLogic.returnPossiblePositions(currentPosition, board).size();
            int randomInteger = (int) (Math.random()*(possiblePositionsSize-1));

            int X = MoveLogic.returnPossiblePositions(currentPosition, board).get(randomInteger)[0];
            int Y = MoveLogic.returnPossiblePositions(currentPosition, board).get(randomInteger)[1];
            return new int[]{X,Y};
        }
        else{
            System.out.println("Something went wrong or piece does not have possible positions");
            return null;
        }
    }
}