package PieceClasses;

public class PiecesCreator {
    private int[] pieceInfos = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6}; //gives us the amount of figures we need prelast index is flag,
    //last index is bombs

    public Piece[][] createPieces() {
        Piece[][] newPieces = new Piece[pieceInfos.length][];
        for (int i = 0; i < pieceInfos.length; i++) {
            Piece[] newFigureTyp = new Piece[pieceInfos[i]];
            for (int j = 1; j <= pieceInfos[i]; j++) {
                newFigureTyp[j - 1] = createPiece(i+1);
            }
            newPieces[i] = newFigureTyp;
        }
        return newPieces;
    }

    private Piece createPiece(int rank) {
        return new Piece(rank);

    }
}
