package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public class HumanPlayer extends PlayerInterface {
    private String COlOR;
    private Piece[][] pieces;
    private ArrayList<Piece> deadPieces = new ArrayList<>();
    private ArrayList<Piece> availablePieces = new ArrayList<>(); //is it needed
    private boolean isWinner;

    public HumanPlayer(String color) {
        this.COlOR = color;
        initializePieces(color);
    }

    @Override
    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                availablePieces.add(pieces[i][j]);
            }
        }
    }

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
        this.isWinner = true;
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
