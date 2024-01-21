package AI;

import AI.Node;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;
import Logic.PlayerClasses.RandomPlayer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void testGetBestBoard() {
        // Create a mock board
        Piece[][] board = new Piece[10][10];

        // Create mock opponent color and player
        String opponentColor = "R";
        Player opponentPlayer = new RandomPlayer(opponentColor);

        // Call the method to test
        Piece[][] bestBoard = Node.getBestBoard(board, opponentColor, opponentPlayer);

        // Check that the returned board is not null
        assertNotNull(bestBoard, "Best board should not be null");

        // Check that the returned board has the correct dimensions
        assertEquals(10, bestBoard.length, "Best board should have 10 rows");
        for (Piece[] row : bestBoard) {
            assertEquals(10, row.length, "Each row of the best board should have 10 columns");
        }
    }
}