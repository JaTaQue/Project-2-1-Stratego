package PieceClasses;

public class PiecesCreator {
    private int[] pieceInfos = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6}; //gives us the amount of figures we need prelast index is flag,
    //last index is bombs

    public Piece[][] createPieces(String color) {
        Piece[][] newPieces = new Piece[pieceInfos.length][];
        for (int i = 0; i < pieceInfos.length; i++) {
            Piece[] newFigureTyp = new Piece[pieceInfos[i]];
            for (int j = 1; j <= pieceInfos[i]; j++) {
                newFigureTyp[j - 1] = createPiece(i+1, color);
            }
            newPieces[i] = newFigureTyp;
        }
        return newPieces;
    }

    public Piece[] createLakes() {
        Piece[] lakePeaces = new Piece[8];
        for (int i = 0; i < 8; i++) {
            lakePeaces[i] = createPiece(-1, null);
        }
        return lakePeaces;
    }

    private Piece createPiece(int rank, String color) {
        return new Piece(rank, color);

    }
}
