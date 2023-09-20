import PieceLogic.*;
public class Player{
    private Piece[][] pieces;
    private String color; 

    public Player(String color, PiecesCreator piecesCreator) {
        this.pieces = piecesCreator.createPieces(color);
        this.color = color;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

}