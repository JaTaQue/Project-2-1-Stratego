package Logic.PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;

import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PieceLogic.PiecesCreator;
import Logic.Tester.Game;

/**
 * The Player class represents a player in the game
 * It is an abstract base class for specific player types (HumanPlayer and AI players)
 * @author Group 7
 * @version 1 
 */
public abstract class Player {
    protected String color;
    // protected Piece[][] pieces;
    protected ArrayList<Piece> pieces;
    protected int[] deadPiecesAmount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    protected int[] availablePiecesAmount = {1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 1, 6}; 
    protected ArrayList<Piece> availablePieces = new ArrayList<Piece>();
    protected int[] piecesToBePlacedAmount = Arrays.copyOf(availablePiecesAmount, availablePiecesAmount.length);
    protected boolean isWinner = false;

    /**
     * constructs a Player object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public Player(String color){
        this.color = color;
        initializePieces(color);
    }

    public Player(String color, ArrayList<Piece> pieces, int[] deadPiecesAmount, int[] availablePiecesAmount, ArrayList<Piece> availablePieces, int[] piecesToBePlacedAmount, boolean isWinner) {
        this.color = color;
        this.pieces = pieces;
        this.deadPiecesAmount = deadPiecesAmount;
        this.availablePiecesAmount = availablePiecesAmount;
        this.availablePieces = availablePieces;
        this.piecesToBePlacedAmount = piecesToBePlacedAmount;
        this.isWinner = isWinner;
    }

    /**
     * initializes the player's pieces based on their color: creates Piece objects and adds them to the appropriate fields
     * @author Group 7
     * @version 1 
     * @param color the color associated with the player, "B" for Blue or "R" for Red
     */
    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.size(); i++) {
            availablePieces.add(pieces.get(i));                
        }
    }

    /**
     * returns the list of available pieces owned by the player
     * @author Group 7
     * @version 1
     * @return an ArrayList of available pieces
     */
    public ArrayList<Piece> getAvailablePieces(){
        return this.availablePieces;
    }

    /**
     * returns the number of dead pieces of a specific rank owned by the palyer
     * @author Group 7
     * @version 1
     * @param rank is the rank of the piece (1 to 12)
     * @return the number of dead pieces of the specified rank
     */
    public int getDeadPiece(int rank) {
        return this.deadPiecesAmount[rank - 1];
    }

    /**
     * returns the number of available pieces of a specific rank
     * @author Group 7
     * @version 1
     * @param rank the rank of the piece (1 to 12)
     * @return the number of available pieces of the specified rank
     */
    public int getAvailablePieceAmount(int rank) {
        return this.availablePiecesAmount[rank - 1];
    }

    /**
     * returns the 2D array of pieces owned by the player
     * @author Group 7
     * @version 1
     * @return the 2D array representing the player's pieces
     */
    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    /**
     * sets the color associated with the player
     * @author Group 7
     * @version 1
     * @param color the color to be set for the player
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * returns the color the player's color
     * @author Group 7
     * @version 1
     * @return player's color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * checks whether the player is a winner
     * @author Group 7
     * @version 1
     * @return true if the player is the winner, false otherwise
     */
    public boolean isWinner() {
        return this.isWinner;
    }

    /**
     * sets the winner status associated with the player
     * @author Group 7
     * @version 1
     * @param isWinner the status to be set for the player
     */
    public void setIsWinnerValue(boolean isWinner) {
        this.isWinner = isWinner;
    }

    /**
     * increments the count of dead pieces of the specified rank and decrements the count of available pieces
     * @author Group 7
     * @version 1
     * @param rank the rank of the piece (1 to 12)
     */
    public void addDeadPiece(int rank) {
        this.deadPiecesAmount[rank -1] += 1;
        this.availablePiecesAmount[rank -1] -= 1;
    }

    /**
     * marks the player as the winner of the game and prints a message
     * @author Group 7
     * @version 1
     */
    public void setWinner() {
        this.isWinner = true;
        // System.out.println("\nWe have a winner!");
    }

    /**
     * checks whether the player has any pieces left
     * @author Group 7
     * @version 1
     * @return true if the player has at least one piece available, false otherwise
     */
    public boolean hasPieces() {
        for (int i = 0; i < pieces.size(); i++) {
            if(!pieces.get(i).isDead()) {
                return true;
            }
        }
        setWinner();
        return false;
    }

    /**
     * returns the number of available pieces of the specified rank
     * @author Group 7
     * @version 1
     * @param rank the rank of the piece (1 to 12)
     * @return the number of available pieces of the specified rank
     */
    public int howManyAliveOfRank(int rank) {
        return availablePiecesAmount[rank-1];
    }

    /**
     * returns the number of dead pieces of the specified rank
     * @author Group 7
     * @version 1
     * @param rank the rank of the piece (1 to 12)
     * @return the number of dead pieces of the specified rank
     */
    public int howManyDeadOfRank(int rank) {
        return deadPiecesAmount[rank-1];
    }

    /**
     * checks whether the player has places all their pieces onto the board
     * @author Group 7
     * @version 1
     * @param board shows all the pices on the board and where they are as a 2D array of Pieces
     * @return true if all pieces are on the board, false otherwise
     */
    public boolean isEveryPieceAtBeginningOnBoard(Piece[][] board) {
        int count = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null) {
                    count++; 
                }
            }
        }
        if(count != 48 || count != 88) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if any player has no available moves
     * @author Group 7
     * @version 1
     * @param board shows all the pices on the board and where they are as a 2D array of Pieces
     * @return "Both" if both players are stuck, "Blue" if the blue player is stuck, "Red" if the red player is stuck, or "None" if neither player is stuck
     */
    public static String isSomeoneStuck(Piece[][] board){
        int counterBlue=0;
        int counterRed=0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]!=null&&board[i][j].getRank()!=-1){
                    if(!MoveLogic.returnPossiblePositions(new int[]{i,j}, board).isEmpty()){
                        if(board[i][j].getColor().equals("Blue")){
                            counterBlue++;
                        }else{
                            counterRed++;
                        }
                }
            }
        }
     }
     if(counterBlue==0&&counterRed==0){
        return "Both";
     }
     if(counterBlue==0){
        return "Blue";
     }
     if(counterRed==0){
        return "Red";
     }
     return "None";
    }

    /**
     * decrements the count of not-yet-placed pieces of a specific rank when a piece is placed on the board
     * @author Group 7
     * @version 1
     * @param piece the piece that has been placed on the board
     */    
    public void piecePlaced(Piece piece){
        this.piecesToBePlacedAmount[piece.getRank()-1]--;
    }

    /**
     * returns the array representing the number of pieces to be placed on the board for each rank
     * @author Group 7
     * @version 1
     * @return an integer array where each element represents the count of pieces to be placed for each rank (1 to 12)
     */
    public int[] getPiecesToBePlacedAmount() {
        return this.piecesToBePlacedAmount;
    }

    public abstract boolean IsPlayable();


    //THE FOLLOWING METHODS ARE IN THIS CLASS, BUT
    //MAYBE IT NEEDS ANOTHER PLACE

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
        

        System.out.println("Something went wrong, maybe the piece in this position cannot make a move");
        return null;
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

     public int[] getMoveBaseLineInhanced(Game game){
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

    public ArrayList<int[]> getMovablePieces(Game game){
        Piece[][] board = game.getBoard();
        //all movable pieces of one color.
        ArrayList<int[]> movablePieces = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int[] position = new int[]{i,j};
                //check if piece is movable and has same color as current player
                if(!MoveLogic.returnPossiblePositions(position, board).isEmpty() && board[i][j].getColor().equals(game.getCurrentPlayer().getColor())){
                    movablePieces.add(position);
                }
            }
        }
        return movablePieces;
    }

    public abstract Player copyPlayer();

    protected ArrayList<Piece> copyPieces() {
        // Piece[][] toBeCopied = { new Piece[pieces[0].length], new Piece[pieces[1].length], new Piece[pieces[2].length], new Piece[pieces[3].length], new Piece[pieces[4].length],
        // new Piece[pieces[5].length], new Piece[pieces[6].length], new Piece[pieces[7].length], new Piece[pieces[8].length], new Piece[pieces[9].length], new Piece[pieces[10].length],
        // new Piece[pieces[11].length]};
        // for(int i = 0; i < pieces[0].length; i++) {
        //     for(int j = 0; j < pieces[i].length; j++) {
        //         toBeCopied[i][j] = pieces[i][j].copyPiece();
        //     }
        // }
        // return toBeCopied;
        ArrayList<Piece> copiedPieces = new ArrayList<>();
        for(int i = 0; i < pieces.size(); i++) {
            copiedPieces.add(pieces.get(i).copyPiece());
        }
        return copiedPieces;
    } 


    public int[] copyAvailablePieceAmount() {
        return this.availablePiecesAmount.clone();
    }

    public int[] copyDeadPiecesAmount() {
        return this.deadPiecesAmount.clone();
    }

    public ArrayList<Piece> copyAvailablePieces(){
        ArrayList<Piece> availablePiecesCopy = new ArrayList<Piece>();
        ArrayList<Piece> pieces = this.getAvailablePieces();

        for (Piece p : pieces){
            availablePiecesCopy.add(new Piece(p.getRank(), p.getColor(), p.getPosition().clone()));
        }

        return availablePiecesCopy;
    }

    public void setAvailablePieceAmount(int[] availablePiecesAmount){
        this.availablePiecesAmount = availablePiecesAmount;
    }

    public void setDeadPiecesAmount(int[] deadPiecesAmount){
        this.deadPiecesAmount = deadPiecesAmount;
    }

    public void setAvailablePieces(ArrayList<Piece> availablePieces){
        this.availablePieces = availablePieces;
    }

    public void setPieces(ArrayList<Piece> pieces){
        this.pieces = pieces;
    }

    public abstract int[][] getNextMove(Game game);

    /**
     * places the pieces for a player randomly
     * @param game current game
     * @author Group 7
     * @version 1
     */ 
     public void placePiecesSimulation(Game game){  
        int counter = 0;
    
        if(equals(game.player1)){
            placePiecesForPlayer(game, counter);
        }else{
            placePiecesForPlayer(game, counter);
            game.setStarted(); //start game after placing player2's pieces
        }
    }

    /**
     * private helper-method for placing the pieces for a player randomly
     * @param game current game
     * @param counter the number of pieces placed so far
     * @author Group 7
     * @version 1
     */ 
    public void placePiecesForPlayer(Game game, int counter) {
        ArrayList<Piece> pieces = new ArrayList<>();
        while(!game.isEveryPieceAtBeginningOnBoard(this)){
    
            int randomX = (int)(Math.random()*10);
            int randomY = (int)(Math.random()*10);
            int[] targetPosition = new int[]{randomX,randomY};
    
            Piece pieceToBePlaced = getAvailablePieces().get(counter);
            // System.out.println("piecetobeplaced: " + pieceToBePlaced);
    
            if(Game.canPlaceAtPosition(targetPosition, game.board, equals(game.player1) ? game.placingBordersPlayer1 : game.placingBordersPlayer2)){
                placePiece(pieceToBePlaced, targetPosition, game);
                // System.out.println("pieces: "+currentPlayer.getPieces());
                counter++;
                pieces.add(pieceToBePlaced);
            }
            // System.out.println("amt: " +Arrays.toString(player.getPiecesToBePlacedAmount()));
        }
        setPieces(pieces);
        // System.out.println("player pieces count: " + player.getPieces().size());
    }

    /**
     * places a piece on the game board at the specified target position
     * @param piece the piece to be placed
     * @param targetPosition the position on the board where the piece is to be placed
     * @param game current game
     * @author Group 7
     * @version 1
     */
    public void placePiece(Piece piece, int[] targetPosition, Game game) {
        piece.setPosition(targetPosition);
        game.board[targetPosition[0]][targetPosition[1]] = piece;
        piecePlaced(piece);
    }

}


