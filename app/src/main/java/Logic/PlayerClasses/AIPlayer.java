package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AI.MCTS;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.Tester.Game;

/**
 * The AI class represents a bot in a AI vs AI or player vs AI game
 * It is a subclass of the Player class.
 * @author Group 7
 * @version 1 
 */
public class AIPlayer extends Player{

    private MCTS mcts = new MCTS();

    /**
     * constructs a Player object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public AIPlayer(String color) {
        super(color);
    }

    public AIPlayer(String color, ArrayList<Piece> pieces, int[] deadPiecesAmount, int[] availablePiecesAmount, ArrayList<Piece> availablePieces, int[] piecesToBePlacedAmount, boolean isWinner) {
        super(color, pieces, deadPiecesAmount, availablePiecesAmount, availablePieces, piecesToBePlacedAmount, isWinner);
    }

    @Override
    public boolean IsPlayable() {
        return false;
    }

    public AIPlayer copyPlayer() {
        int[] deadCopy = deadPiecesAmount.clone();
        int[] availablePiecesAmountCopy = availablePiecesAmount.clone();
        ArrayList<Piece> piecesCopy = super.copyPieces();
        ArrayList<Piece> availableCopy = copyAvailablePieces();
        return new AIPlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }

    @Override
    public int[][] getNextMove(Game game) {
        // int[][] nextMove = mcts.returnNextMove(game.getBoard(), game.getCurrentPlayer(), game.getEnemyPlayer());
        int[][] nextMove = mcts.findBestMove2(game);

        try{
            System.out.println();
            System.out.println(Arrays.toString(nextMove[0]) + " -> " + Arrays.toString(nextMove[1]) + ", " + game.getBoard()[nextMove[0][0]][nextMove[0][1]].toString());
        }catch(NullPointerException npe){
            System.out.println(Arrays.toString(nextMove[1]) + " " + "null");
        }

        return nextMove;
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
}

