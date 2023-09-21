import java.util.ArrayList;

import PieceLogic.Piece;

public class Board {

Piece[][]board = new Piece[10][10];
ArrayList<Piece> availablePiecesBlue = new ArrayList<Piece>();
ArrayList<Piece> availablePiecesRed = new ArrayList<Piece>();
ArrayList<Piece> deadPiecesBlue = new ArrayList<Piece>();
ArrayList<Piece> deadPiecesRed = new ArrayList<Piece>();

    public Board(ArrayList<Piece> availablePieces){
        this.availablePiecesRed = availablePieces;
        this.availablePiecesBlue = availablePieces;
    }

    public void removeAvailablePiece(Piece piece){
        if(piece.getColor().equals("B")){
            if(availablePiecesBlue.contains(piece)){
            availablePiecesBlue.remove(piece);
        }else{
            System.out.println("This piece is not one of the available pieces!");
        }
    }
        if(piece.getColor().equals("R")){
            if(availablePiecesBlue.contains(piece)){
            availablePiecesBlue.remove(piece);
        }
        else{
            System.out.println("This piece is not one of the available pieces!");
        }
        }
    }

    public void addDeadPieces(Piece piece){
        if(piece.getColor().equals("B")){
        deadPiecesBlue.add(piece);}
        if(piece.getColor().equals("R")){
        deadPiecesRed.add(piece);}
    }

    public void changePosition(int i, int j, Piece piece){
        board[i][j]=piece;
    }

    public Piece getPiece(int i, int j){
        Piece returnPiece = board[i][j];
        return returnPiece;
    }
}