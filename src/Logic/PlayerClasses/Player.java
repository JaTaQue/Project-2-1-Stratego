package PlayerClasses;

import java.util.ArrayList;
import java.util.Arrays;

import GameLogic.MoveLogic;
import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public abstract class Player {
    private String color;
    private Piece[][] pieces;
    private int[] deadPiecesAmount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] availablePiecesAmount = {1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 1, 6}; 
    private ArrayList<Piece> availablePieces = new ArrayList<Piece>();
    private int[] piecesToBePlacedAmount = Arrays.copyOf(availablePiecesAmount, availablePiecesAmount.length);
    private boolean isWinner = false;

    public Player(String color){
        this.color = color;
        initializePieces(color);
    }

    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                availablePieces.add(pieces[i][j]);                
            }
        }
    }

    public ArrayList<Piece> getAvailablePieces(){
        return this.availablePieces;
    }

    public int getDeadPiece(int rank) {
        return this.deadPiecesAmount[rank - 1];
    }

    public int getAvailablePieceAmount(int rank) {
        return this.availablePiecesAmount[rank - 1];
    }

    public Piece[][] getPieces() {
        return this.pieces;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isWinner() {
        return this.isWinner;
    }

    public void addDeadPiece(int rank) {
        this.deadPiecesAmount[rank -1] += 1;
        this.availablePiecesAmount[rank -1] -= 1;
    }

    public void setWinner() {
        this.isWinner = true;
        System.out.println("We have a winner!");
    }

    public boolean hasPieces() {
        for (int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(!pieces[i][j].isDead()) {
                    return true;
                }
            }
        }
        setWinner();
        return false;
    }

    public int howManyAliveOfRank(int rank) {
        return availablePiecesAmount[rank-1];
    }

    public int howManyDeadOfRank(int rank) {
        return deadPiecesAmount[rank-1];
    }

    public static String isSomeoneStuck(Piece[][] board){
        int counterBlue=0;
        int counterRed=0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]!=null&&board[i][j].getRank()!=-1){
                    if(!MoveLogic.returnPossiblePositions(new int[]{i,j}, board).isEmpty()){
                        if(board[i][j].getColor().equals("B")){
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

    public void piecePlaced(Piece piece){
        this.piecesToBePlacedAmount[piece.getRank()-1]--;
    }

    public int[] getPiecesToBePlacedAmount() {
        return this.piecesToBePlacedAmount;
    }
}
