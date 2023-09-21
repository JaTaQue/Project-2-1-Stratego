package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;

public abstract class PlayerInterface {
    private  String COlOR;
    private Piece[][] pieces;
    private ArrayList<Piece> deadPieces = new ArrayList<>();
    private ArrayList<Piece> availablePieces = new ArrayList<>(); //is it needed
    private boolean isWinner;

    public ArrayList<Piece> getDeadPieces() {
        return this.deadPieces;
    }

    public ArrayList<Piece> getAvailablePieces() {
        return this.availablePieces;
    }

    public Piece[][] getPieces() {
        return this.pieces;
    }

    public String getColor() {
        return this.COlOR;
    }

    public boolean isWinner() {
        return this.isWinner;
    }

    public void addDeadPiece(Piece deadPiece) {
        this.deadPieces.add(deadPiece);
    }

    public void addAvailablePiece(Piece availablePiece) {
        this.availablePieces.add(availablePiece);
    }

    public void setWinner() {
        this.isWinner = false;
    }

    public boolean hasPieces() {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(pieces[i][j].isDead() == false) {
                    return true;
                }
            }
        }
        setWinner();
        return false;
    }
}
