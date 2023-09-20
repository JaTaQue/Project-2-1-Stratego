package PieceClasses;

public class PiecesCreator {
    private int[] pieceInfos = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6}; //gives us the amount of figures we need prelast index is flag,
    //last index is bombs

    // Rank 0 = Empty
    // Rank 1 = Spy
    // Rank 2 = Scout
    // Rank 3 = Miner
    // Rank 4 = Sergeant
    // Rank 5 = Lieutenant
    // Rank 6 = Captain
    // Rank 7 = Major
    // Rank 8 = Colonel
    // Rank 9 = General
    // Rank 10 = Marshal
    // Rank 11 = Flag
    // Rank 12 = Bomb

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
