package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;

public class HumanPlayer extends PlayerInterface {
    private String COlOR;
    private Piece[][] pieces;
    private ArrayList<Piece> deadPieces = new ArrayList<>();
    private ArrayList<Piece> availablePieces = new ArrayList<>(); //is it needed
    private boolean isWinner;

    @Override
    public ArrayList<Piece> getDeadPieces() {
        return this.deadPieces;
    }

    @Override
    public ArrayList<Piece> getAvailablePieces() {
        return this.availablePieces;
    }

    @Override
    public Piece[][] getPieces() {
        return this.pieces;
    }

    
    @Override
    public String getColor() {
        return this.COlOR;
    }

    @Override
    public boolean isWinner() {
        return this.isWinner;
    }

    @Override
    public void addDeadPiece(Piece deadPiece) {
        this.deadPieces.add(deadPiece);
    }

    @Override
    public void addAvailablePiece(Piece availablePiece) {
        this.availablePieces.add(availablePiece);
    }

    @Override
    public void setWinner() {
        this.isWinner = false;
    }

    @Override
    public boolean hasPieces() {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(!pieces[i][j].isDead()) {
                    return true;
                }
            }
        }
        setWinner();
        return false;
    }
}
