package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;
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


    // Rank 1 = Spy
    // Rank 2 = Scout
    // Rank 3 = Miner
    // Rank 4 = Sergeant
    // Rank 5 = Lieutenant
    // Rank 6 = Captain
    // Rank 7 = Major
    // Rank 8 = Colonel
    // Rank 9 = General
    // Rank 10 = Marshal
    // Rank 11 = Flag
    // Rank 12 = Bomb

    // protected int[] availablePiecesAmount = {1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 1, 6}; 
    // protected ArrayList<Piece> availablePieces = new ArrayList<Piece>();
    // protected int[] piecesToBePlacedAmount = Arrays.copyOf(availablePiecesAmount, availablePiecesAmount.length);

    @Override 
    public void placePiecesForPlayer(Game game) {
        ArrayList<ArrayList<int[]>> positions = getPositions(game);

        Player player = game.getCurrentPlayer();


        for (Piece piece : player.getAvailablePieces()) {
            
            int rank = piece.getRank();

            placePiece(piece, positions.get(rank-1).get(0), game);
            positions.get(rank-1).remove(0);
            pieces.add(piece);
        
        }

    }
 
    private ArrayList<ArrayList<int[]>> getPositions(Game game) {
        if(game.getCurrentPlayer() == game.getPlayer1()){
            ArrayList<int[]> positionsRank1 = new ArrayList<int[]>(Arrays.asList(new int[]{2,3}));
            ArrayList<int[]> positionsRank2 = new ArrayList<int[]>(Arrays.asList(new int[]{0,0}, new int[]{0,3}, new int[]{2,5}, new int[]{3,1}, new int[]{3,2}, new int[]{3,4}, new int[]{3,8}, new int[]{2,9}));
            ArrayList<int[]> positionsRank3 = new ArrayList<int[]>(Arrays.asList(new int[]{0,1}, new int[]{0,4}, new int[]{0,8}, new int[]{0,9}, new int[]{3,6}));
            ArrayList<int[]> positionsRank4 = new ArrayList<int[]>(Arrays.asList(new int[]{1,0}, new int[]{1,2}, new int[]{1,9}, new int[]{2,1}));
            ArrayList<int[]> positionsRank5 = new ArrayList<int[]>(Arrays.asList(new int[]{1,5}, new int[]{1,7}, new int[]{2,0}, new int[]{3,3}));
            ArrayList<int[]> positionsRank6 = new ArrayList<int[]>(Arrays.asList(new int[]{1,8}, new int[]{3,0}, new int[]{3,5}, new int[]{3,9}));
            ArrayList<int[]> positionsRank7 = new ArrayList<int[]>(Arrays.asList(new int[]{1,3}, new int[]{2,6}, new int[]{2,7}));
            ArrayList<int[]> positionsRank8 = new ArrayList<int[]>(Arrays.asList(new int[]{1,4}, new int[]{2,8}));
            ArrayList<int[]> positionsRank9 = new ArrayList<int[]>(Arrays.asList(new int[]{2,4}));
            ArrayList<int[]> positionsRank10 = new ArrayList<int[]>(Arrays.asList(new int[]{3,7}));
            ArrayList<int[]> positionsRank11 = new ArrayList<int[]>(Arrays.asList(new int[]{0,6}));
            ArrayList<int[]> positionsRank12 = new ArrayList<int[]>(Arrays.asList(new int[]{0,2}, new int[]{0,5}, new int[]{0,7}, new int[]{1,1}, new int[]{1,6}, new int[]{2,2}));

            ArrayList<ArrayList<int[]>> positions = new ArrayList<ArrayList<int[]>>(Arrays.asList(positionsRank1,positionsRank2,positionsRank3,positionsRank4,positionsRank5,positionsRank6,positionsRank7,positionsRank8,positionsRank9,positionsRank10,positionsRank11,positionsRank12));
            return positions;
        }else{
            ArrayList<int[]> positionsRank1 = new ArrayList<int[]>(Arrays.asList(new int[]{7,3}));
            ArrayList<int[]> positionsRank2 = new ArrayList<int[]>(Arrays.asList(new int[]{9,0}, new int[]{9,3}, new int[]{7,5}, new int[]{6,1}, new int[]{6,2}, new int[]{6,4}, new int[]{6,8}, new int[]{7,9}));
            ArrayList<int[]> positionsRank3 = new ArrayList<int[]>(Arrays.asList(new int[]{9,1}, new int[]{9,4}, new int[]{9,8}, new int[]{9,9}, new int[]{6,6}));
            ArrayList<int[]> positionsRank4 = new ArrayList<int[]>(Arrays.asList(new int[]{8,0}, new int[]{8,2}, new int[]{8,9}, new int[]{7,1}));
            ArrayList<int[]> positionsRank5 = new ArrayList<int[]>(Arrays.asList(new int[]{8,5}, new int[]{8,7}, new int[]{7,0}, new int[]{6,3}));
            ArrayList<int[]> positionsRank6 = new ArrayList<int[]>(Arrays.asList(new int[]{8,8}, new int[]{6,0}, new int[]{6,5}, new int[]{6,9}));
            ArrayList<int[]> positionsRank7 = new ArrayList<int[]>(Arrays.asList(new int[]{8,3}, new int[]{7,6}, new int[]{7,7}));
            ArrayList<int[]> positionsRank8 = new ArrayList<int[]>(Arrays.asList(new int[]{8,4}, new int[]{7,8}));
            ArrayList<int[]> positionsRank9 = new ArrayList<int[]>(Arrays.asList(new int[]{7,4}));
            ArrayList<int[]> positionsRank10 = new ArrayList<int[]>(Arrays.asList(new int[]{6,7}));
            ArrayList<int[]> positionsRank11 = new ArrayList<int[]>(Arrays.asList(new int[]{9,6}));
            ArrayList<int[]> positionsRank12 = new ArrayList<int[]>(Arrays.asList(new int[]{9,2}, new int[]{9,5}, new int[]{9,7}, new int[]{8,1}, new int[]{8,6}, new int[]{7,2}));

            ArrayList<ArrayList<int[]>> positions = new ArrayList<ArrayList<int[]>>(Arrays.asList(positionsRank1,positionsRank2,positionsRank3,positionsRank4,positionsRank5,positionsRank6,positionsRank7,positionsRank8,positionsRank9,positionsRank10,positionsRank11,positionsRank12));
            return positions;
        }
    }
}

