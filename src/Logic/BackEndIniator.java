import GameLogic.*;
import PieceLogic.*;
import PlayerClasses.HumanPlayer;
import PlayerClasses.PlayerInterface;

public class BackEndIniator {
    private Piece[][] board = new Piece[10][10];

    public HumanPlayer createHumanPlayer (String color) {
        HumanPlayer A = new HumanPlayer(color);
        A.setColor(color);
        A.initializePieces(color);
        return A;
    }

    public void createLakes() {
        PiecesCreator.createLakes(board);
    }
}
