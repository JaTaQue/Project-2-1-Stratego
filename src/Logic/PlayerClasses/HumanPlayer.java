package PlayerClasses;

import java.util.ArrayList;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public class HumanPlayer extends PlayerInterface {
    private final String COLOR;
    private Piece[][] pieces;
    private int[] deadPieces = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] availablePieces = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6};
    private ArrayList<ArrayList<Piece>> pieceAtBeginning = new ArrayList<>();
    private boolean isWinner;

    public HumanPlayer(String color) {
        this.COLOR = color;
        initializePieces(color);
    }

    @Override
    public int getDeadPiece(int rank) {
        return this.deadPieces[rank - 1];
    }

    @Override
    public int getAvailablePieceAmount(int rank) {
        return this.availablePieces[rank - 1];
    }


    @Override
    public Piece[][] getPieces() {
        return this.pieces;
    }

    
    @Override
    public String getColor() {
        return this.COLOR;
    }

    @Override
    public boolean isWinner() {
        return this.isWinner;
    }

    @Override
    public void addDeadPiece(int rank) {
        this.deadPieces[rank -1] += 1;
        this.availablePieces[rank -1] -= 1;
    }

    @Override
    public void setWinner() {
        this.isWinner = true;
    }

    @Override
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

    @Override
    public int hasManysLeft(int rank) {
        int figuresLeft = 0;
        try {
            figuresLeft = getPiecesAtBeginning().get(rank - 1).size();
        } catch (Exception e) {
            System.out.println("Sth went wrong with the ranks");
        }
        return figuresLeft;
    }
    
    @Override
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
