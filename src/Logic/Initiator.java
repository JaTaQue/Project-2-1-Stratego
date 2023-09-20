import java.util.Arrays;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public class Initiator{
    public static void main(String[] args) {
        PiecesCreator piecesCreator = new PiecesCreator();

        Player player1 = new Player("R", piecesCreator);

        Player player2 = new Player("B", piecesCreator);

        
    }
} 
