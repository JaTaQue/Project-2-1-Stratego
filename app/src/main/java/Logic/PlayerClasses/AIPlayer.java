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
        int[][] nextMove = mcts.findBestMove(game);

        try{
            System.out.println();
            System.out.println(Arrays.toString(nextMove[0]) + " -> " + Arrays.toString(nextMove[1]) + ", " + game.getBoard()[nextMove[0][0]][nextMove[0][1]].toString());
        }catch(NullPointerException npe){
            System.out.println(Arrays.toString(nextMove[1]) + " " + "null");
        }

        return nextMove;
    }

    @Override 
    public void placePiecesForPlayer(Game game) {
        int counter = 0;
        ArrayList<Piece> pieces = new ArrayList<>();
        int [] positionFlagPlayer1 = new int [] {0,0}; 
        int [] positionMarshalPlayer1 = new int [] {2,2};
        int [] positionGenralPlayer1 = new int [] {1,2};
        int [] positionSpyPlayer1 = new int [] {0,2};
        int[] positionBombPlayer1Y = new int[] {0,1};
        int[] positionBombPlayer1X = new int[] {1,0};
        int[] positionBombPlayer1XY = new int[] {1,1};
        int[] positionColonel1Player1 = new int[] {3,0};
        int[] positionColonel2Player1 = new int[] {3,9};
        int[] positionMajor1Player1 = new int[] {3,1};
        int[] positionMajor2Player1 = new int[] {3,5};
        int[] positionMajor3Player1 = new int[] {3,8};

        Player player = game.getCurrentPlayer();


        int counterBomb = 0;
        int counterColonel = 0;
        // Place the flag
        for (Piece piece : player.getAvailablePieces()) {
            if (piece.getRank() == 11 && Game.canPlaceAtPosition(positionFlagPlayer1, game.board, game.placingBordersPlayer1)) {
                placePiece(piece, positionFlagPlayer1, game);
                pieces.add(piece);
                player.getAvailablePieces().remove(piece);
                break;
            }
            
        }
    
        //for the marshal
        for (Piece piece : player.getAvailablePieces()) {
            if(piece.getRank() == 10 && Game.canPlaceAtPosition(positionMarshalPlayer1, game.board, game.placingBordersPlayer1)){
                placePiece(piece, positionMarshalPlayer1, game);
                pieces.add(piece);
                player.getAvailablePieces().remove(piece);
                break;
            }
        }

        //major
        for (Piece piece :player.getAvailablePieces()) {
           if(piece.getRank() == 7 && Game.canPlaceAtPosition(positionMajor1Player1, game.board, game.placingBordersPlayer1)){
               
               placePiece(piece, positionMajor1Player1, game);
               pieces.add(piece);
               player.getAvailablePieces().remove(piece);
                 
               break;
               
               
           }
           
        }
        //major
        for (Piece piece : player.getAvailablePieces()) {
           if(piece.getRank() == 7 && Game.canPlaceAtPosition(positionMajor2Player1, game.board, game.placingBordersPlayer1)){
               
               placePiece(piece, positionMajor2Player1, game);
               pieces.add(piece);
               player.getAvailablePieces().remove(piece);
              
               break;
               
               
           }
           
        }
        //major
        for (Piece piece : player.getAvailablePieces()) {
           if(piece.getRank() == 7 && Game.canPlaceAtPosition(positionMajor3Player1, game.board, game.placingBordersPlayer1)){
               
               placePiece(piece, positionMajor3Player1, game);
               pieces.add(piece);
               player.getAvailablePieces().remove(piece);
           
               break;
               
           }
           
        }

        

        
    
        // the general
        for (Piece piece : player.getAvailablePieces()) {
            if(piece.getRank() == 9 && Game.canPlaceAtPosition(positionGenralPlayer1, game.board, game.placingBordersPlayer1)){
                placePiece(piece, positionGenralPlayer1, game);
                pieces.add(piece);
                player.getAvailablePieces().remove(piece);
                break;
            }
        }
    
        // the spy
        for (Piece piece : player.getAvailablePieces()) {
            if(piece.getRank() == 1 && Game.canPlaceAtPosition(positionSpyPlayer1, game.board, game.placingBordersPlayer1)){
            //    System.out.println("ahhhh");
                placePiece(piece, positionSpyPlayer1, game);
                pieces.add(piece);
                player.getAvailablePieces().remove(piece);
                break;
           }
        }
        //bombs
        for (Piece piece : player.getAvailablePieces()) {
           if(piece.getRank()==12 && Game.canPlaceAtPosition(positionBombPlayer1Y, game.board, game.placingBordersPlayer1)  ){
               if(counterBomb==0){
                   placePiece(piece, positionBombPlayer1Y, game);
                   pieces.add(piece);
                   player.getAvailablePieces().remove(piece);
                   counterBomb++;
                      
               }
               
               

           }
           if(piece.getRank()==12 && Game.canPlaceAtPosition(positionBombPlayer1X, game.board, game.placingBordersPlayer1)){
               if(counterBomb==1){
                   placePiece(piece, positionBombPlayer1X, game);
                   pieces.add(piece);
                   player.getAvailablePieces().remove(piece);
                   counterBomb++;
                   
               }

           }

           if(piece.getRank()==12 && Game.canPlaceAtPosition(positionBombPlayer1XY, game.board, game.placingBordersPlayer1)){
               if(counterBomb==2){
                   placePiece(piece, positionBombPlayer1XY, game);
                   pieces.add(piece);
                   player.getAvailablePieces().remove(piece);
                   counterBomb++;
                   break;
                   
               }

           }
        }

        for (Piece piece : player.getAvailablePieces()) {
           if(piece.getRank()==8 && Game.canPlaceAtPosition(positionColonel1Player1, game.board, game.placingBordersPlayer1)){
               
               if(counterColonel == 0){
                   placePiece(piece, positionColonel1Player1, game);
                   pieces.add(piece);
                   player.getAvailablePieces().remove(piece);
                   counterColonel++;
                   break;
               }

           
           }

           
            
        }
        //colonel
        for (Piece piece : player.getAvailablePieces()) {
   
           if(piece.getRank() == 8 && Game.canPlaceAtPosition(positionColonel2Player1, game.board, game.placingBordersPlayer1)){
               
               if(counterColonel == 1){
                   
                   placePiece(piece, positionColonel2Player1, game);
                   pieces.add(piece);
                   player.getAvailablePieces().remove(piece);
                   counterColonel++;
                   break;
               }
           }
            
        }
        


        // Then, place the rest of the pieces
        while(!game.isEveryPieceAtBeginningOnBoard(player)){
           int randomX = (int)(Math.random()*10);
           int randomY = (int)(Math.random()*10);
           int[] targetPosition = new int[]{randomX,randomY};
    
           Piece pieceToBePlaced = player.getAvailablePieces().get(counter);
    
           if (Game.canPlaceAtPosition(targetPosition, game.board, player.equals(game.player1) ? game.placingBordersPlayer1 : game.placingBordersPlayer2)) {
                placePiece(pieceToBePlaced, targetPosition, game);
                counter++;
                pieces.add(pieceToBePlaced);
           }
        }
        player.setPieces(pieces);
       // System.out.println("player pieces count: " + player.getPieces().size());

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

