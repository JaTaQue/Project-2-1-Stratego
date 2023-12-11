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

    
    public Player copyPlayer() {
        int[] deadCopy = deadPiecesAmount.clone();
        int[] availablePiecesAmountCopy = availablePiecesAmount.clone();
        ArrayList<Piece> piecesCopy = super.copyPieces();
        ArrayList<Piece> availableCopy = copyAvailablePieces();
        return new BaselinePlayer(this.color, piecesCopy, deadCopy, availablePiecesAmountCopy, availableCopy, null, isWinner);
    }


    /*
     * "1. Use Spy: If the spy is next to the enemy marshal, capture it. ---> done
        2. Defend against Spy: If the marshal is next to the enemy spy, capture it. If it
        is next to unknown pieces, evaluate if it should be captured or if the marshal
        should move away.
        3. Attack weaker: If a known piece is next to a weaker known piece, capture it
        unless the new position is dangerous.
        4. Explore attack: If a scout, sergeant, lieutenant or captain is next to an unknown
        piece, capture it.
        5. Retreat: If a known piece is next to a stronger enemy piece, run away.
        6. Scout: Try advancing scouts forward rapidly.
        7. Attack distant: If a known piece is distant but a path exists that moves a stronger
        piece towards it, advance the stronger piece over the path.
        8. Explore distant: Advance lower-ranked pieces towards unknown pieces.
        9. Attack known with same distant: If a known piece can be attacked by a known
        identical piece, advance towards it and capture it.
        10. Move forward: Move any piece we can forward.
        11. Move: Move any piece we can.
        12. Resign: No moves can be made at all, so the agent should resign"
     */
    @Override    
     public int[] getNextMove(Game game){
        if(this.IsPlayable()){
            System.out.println("Something went wrong, playable player uses method of Inhanced baselineplayer");
            return null;
        }

        Piece[][] board = game.getBoard(); 
        ArrayList<Piece> availablePieces = game.getPlayer1().getAvailablePieces();
        

        for (Piece piece : availablePieces) {
            //first condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 1)){
                
                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size()-1;
                for (int i = 0; i < length ; i++) {
                    
                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);

                    if(board[Pos[0]][Pos[1]].getRank() == 10 && board[Pos[0]][Pos[1]].isVisible()){
                        return Pos; 
                    }    
                        
                }

            }
            //second condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 10)){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for(int i = 0; i<length; i++){

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);

                    if(board[Pos[0]][Pos[1]].getRank() == 1 && board[Pos[0]][Pos[1]].isVisible()){
                        return Pos; 
                    }
                    else{
                        // marshal should go back
                    }    
                }
            }
            //Third condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty()){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for (int i = 0; i < length; i++) {

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);
                    //add a way to chack if the now pos is dangerous, maybe another fori inside
                    if(board[Pos[0]][Pos[1]] != null && board[Pos[0]][Pos[1]].getRank() < piece.getRank() && board[Pos[0]][Pos[1]].isVisible()){
                        return Pos;
                    }
                    
                }

            }
            //Fourth condition
            if(!MoveLogic.returnPossiblePositions(piece.getPosition(),board).isEmpty() && (piece.getRank() == 2 || piece.getRank() == 4 || piece.getRank() == 5 || piece.getRank() == 6 )){

                int length = MoveLogic.returnPossiblePositions(piece.getPosition(), board).size() -1;
                for (int i = 0; i < length; i++) {

                    int[] Pos = MoveLogic.returnPossiblePositions(piece.getPosition(), board).get(i);
                    if(board[Pos[0]][Pos[1]] != null && !board[Pos[0]][Pos[1]].isVisible()){
                        return Pos;
                    }   
                }
            }
            //Fifth condition retreat if know piece is next to stronger piece


        }

        return null;

    }


}