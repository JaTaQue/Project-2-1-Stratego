import java.util.Arrays;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;

public class Main{
    public static void main(String[] args) {
        PiecesCreator pc = new PiecesCreator();
        Piece[][] newPieces = pc.createPieces("red");
        for (Piece[] pa : newPieces){
            for (Piece p : pa){
            System.out.println(p.getRank() + " "+Arrays.toString(p.getPosition())+" "+p.isDead());
        }
        }
    }
} 
