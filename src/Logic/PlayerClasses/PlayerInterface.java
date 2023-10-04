package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public abstract class PlayerInterface {
    private  String COlOR;
    private Piece[][] pieces;
    private int[] deadPieces = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] availablePieces = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6}; 
    private ArrayList<ArrayList<Piece>> pieceAtBeginning = new ArrayList<>();
    private boolean isWinner;


    public void initializePieces(String color) {
        this.pieces = PiecesCreator.createPieces(color);
        for(int i = 0; i < pieces.length; i++) {
            ArrayList<Piece> newRank = new ArrayList<>();
            this.pieceAtBeginning.add(newRank);
            for(int j = 0; j < pieces[i].length; j++) {
                this.pieceAtBeginning.get(i).add(pieces[i][j]);
            }
        }
    }

    public ArrayList<ArrayList<Piece>> getPiecesAtBeginning() {
        return this.pieceAtBeginning;
    }

    public int getDeadPiece(int rank) {
        return this.deadPieces[rank - 1];
    }

    public int getAvailablePieceAmount(int rank) {
        return this.availablePieces[rank - 1];
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


    public void addDeadPiece(int rank) {
        this.deadPieces[rank -1] += 1;
        this.availablePieces[rank -1] -= 1;
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
