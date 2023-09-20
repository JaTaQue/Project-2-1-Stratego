import java.util.Arrays;

import Pieces.Piece;
import Pieces.PiecesCreator;

public class Main{
    public static void main(String[] args) {
        PiecesCreator pc = new PiecesCreator();
        Piece[][] newPieces = pc.createPieces();
        for (Piece[] pa : newPieces){
            for (Piece p : pa){
            System.out.println(p.getRank() + " "+Arrays.toString(p.getPosition())+" "+p.isDead());
        }
        }
    }
} 
