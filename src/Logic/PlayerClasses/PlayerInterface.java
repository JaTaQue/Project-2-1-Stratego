package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public abstract class PlayerInterface {
    private  String COlOR;
    private Piece[][] pieces;
    private ArrayList<Piece> deadPieces = new ArrayList<>();
    private ArrayList<Piece> availablePieces = new ArrayList<>(); //is it needed
    private boolean isWinner;


    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                availablePieces.add(pieces[i][j]);
            }
        }
    }

    public ArrayList<Piece> getDeadPieces() {
        return this.deadPieces;
    }

    public ArrayList<Piece> getAvailablePieces() {
        return this.availablePieces;
    }

    public Piece[][] getPieces() {
        return this.pieces;
    }

    public void setColor(String color) {
        this.COlOR = color;
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
        this.isWinner = true;
    }

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
