package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public abstract class PlayerInterface {
    private  String COlOR;
    private Piece[][] pieces;
    private ArrayList<Piece> deadPieces = new ArrayList<>();
    private ArrayList<Piece> availablePieces = new ArrayList<>(); //is it needed
    private ArrayList<ArrayList<Piece>> pieceAtBeginning = new ArrayList<>();
    private boolean isWinner;


    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.length; i++) {
            ArrayList<Piece> newRank = new ArrayList<>();
            this.pieceAtBeginning.add(newRank);
            for(int j = 0; j < pieces[i].length; j++) {
                availablePieces.add(pieces[i][j]);
                this.pieceAtBeginning.get(i).add(pieces[i][j]);
            }
        }
    }

    public ArrayList<ArrayList<Piece>> getPiecesAtBeginning() {
        return this.pieceAtBeginning;
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

    public int hasManysLeft(int rank) {
        int figuresLeft = 0;
        try {
            figuresLeft = getPiecesAtBeginning().get(rank - 1).size();
        } catch (Exception e) {
            System.out.println("Sth went wrong with the ranks");
        }
        return figuresLeft;
    }

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
}
