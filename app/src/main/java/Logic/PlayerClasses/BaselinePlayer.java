package Logic.PlayerClasses;

import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.Tester.Game;

import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Creates a deep copy of the BaselinePlayer object
     * @return new BaseLinePlayer that represents a deep copy of the original
     */
    public BaselinePlayer copyPlayer() {
        int[] deadCopy = deadPiecesAmount.clone();
        int[] availablePiecesAmountCopy = availablePiecesAmount.clone();
        ArrayList<Piece> piecesCopy = super.copyPieces();
        ArrayList<Piece> availableCopy = copyAvailablePieces();
        return new BaselinePlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }

    /**
     * This method gives the next move based on the specific conditions of the peter N.lewis agent
     * @author Group 7
     * @version 1 
     * @param game the current game state
     * @return an array of integer where the first element represent the current position and the second element the destination position on the board.
     */
    @Override    
    public int[][] getNextMove(Game game){
        if(this.IsPlayable()){
            System.out.println("Something went wrong, playable player uses method of Inhanced baselineplayer");
            return null;
        }

        Piece[][] board = game.getBoard(); 
        ArrayList<Piece> availablePieces = game.getCurrentPlayer().getAvailablePieces();
        

        for (Piece piece : availablePieces) {
            //first condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 1)){
                
                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size()-1;
                for (int i = 0; i < length ; i++) {
                    
                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);

                    if(board[Pos[0]][Pos[1]] != null && board[Pos[0]][Pos[1]].getRank() == 10 && board[Pos[0]][Pos[1]].isVisible()){
                        return new int[][]{piece.getPosition(),board[Pos[0]][Pos[1]].getPosition()};
                    }    
                        
                }

            }
            //second condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 10)){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for(int i = 0; i<length; i++){

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);

                    if(board[Pos[0]][Pos[1]].getRank() == 1 && board[Pos[0]][Pos[1]].isVisible()){
                        return new int[][]{piece.getPosition(),board[Pos[0]][Pos[1]].getPosition()}; 
                    }
                    //marshal should retreat   
                }
            }
            //Third condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty()){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for (int i = 0; i < length; i++) {

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);
                    //add a way to chack if the now pos is dangerous, maybe another fori inside
                    if(board[Pos[0]][Pos[1]] != null && board[Pos[0]][Pos[1]].getRank() < piece.getRank() && board[Pos[0]][Pos[1]].isVisible()){
                        return new int[][]{piece.getPosition(),board[Pos[0]][Pos[1]].getPosition()};
                    }
                    
                }

            }
            //Fourth condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 2 || piece.getRank() == 4 || piece.getRank() == 5 || piece.getRank() == 6 )){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for (int i = 0; i < length; i++) {

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);
                    if(board[Pos[0]][Pos[1]] != null && !board[Pos[0]][Pos[1]].isVisible()){
                        
                        return new int[][]{piece.getPosition(),board[Pos[0]][Pos[1]].getPosition()}; 
                    }   
                }
            }
            //Fifth condition 
            else{
                RandomPlayer rp = new RandomPlayer(this.color);
                int[] movablePosition = rp.getRandomMovablePosition(game);
                int[] move = rp.getRandomMove(game, movablePosition);
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

        return null;

        

    }

    /**
     * Generates a random move for the player based on the provided current position.
     *
     * @param game The Game object representing the current game state
     * @param currentPosition The current position on the board
     * @return An array of two integers representing the X and Y coordinates of the random move
     */
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