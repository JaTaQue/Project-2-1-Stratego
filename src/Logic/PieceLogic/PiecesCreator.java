package PieceLogic;

import java.util.Arrays;

public class PiecesCreator {
    private static int[] pieceInfos = {1, 8, 5, 4, 4, 4, 3 , 2, 1, 1, 1, 6}; //gives us the amount of figures we need prelast index is flag,
    
    // Rank 0 = Lake
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

    public static Piece[][] createPieces(String color) {
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

    public static void createLakes(Piece[][] board) {
        //int[][] lakeCoordinates = {{2,4}, {2,5}, {3,4}, {3,5}, {6,4}, {6,5}, {7,4}, {7,5}};
        int[][] lakeCoordinatesTilted = {{4,2}, {5,2}, {4,3}, {5,3}, {4,6}, {5,6}, {4,7}, {5,7}};
        Piece[] lakePeaces = new Piece[8];
        for (int i = 0; i < 8; i++) {
            lakePeaces[i] = createPiece(-1, null);
            lakePeaces[i].setPosition(lakeCoordinatesTilted[i]);
            board[lakeCoordinatesTilted[i][0]][lakeCoordinatesTilted[i][1]] = lakePeaces[i];
            //System.out.println(Arrays.deepToString(board) + " THIS" + board);

        }
    }

    private static Piece createPiece(int rank, String color) {
        return new Piece(rank, color);

    }
}