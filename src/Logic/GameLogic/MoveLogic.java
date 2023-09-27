package GameLogic;

import java.util.ArrayList;

import PieceLogic.Piece;

public class MoveLogic {

    public static boolean canMove(Piece piece, int[] targetPosition, Piece[][] positionArray) {
        if(piece.getPosition()[0] == targetPosition[0] && piece.getPosition()[1] == targetPosition[1]) {
            return false;
        }
        if(piece.getRank() == 12 || piece.getRank() == 11 || piece.getRank() == -1) {
            return false;
        } else if (positionArray[piece.getPosition()[targetPosition[0]]][piece.getPosition()[targetPosition[1]]] != null) {
            return false;
        } else if (piece.getPosition()[0] != targetPosition[0] || piece.getPosition()[1] != targetPosition[1]) {
            return false;
        } else if (piece.getRank() == 2 && !canScoutMove(piece, targetPosition, positionArray)) {
            return false;
        } else if (piece.getRank() != 2 &&(((piece.getPosition()[0] == targetPosition[0]) && Math.abs(piece.getPosition()[1] -targetPosition[1]) > 1) || ((piece.getPosition()[1] == targetPosition[1]) && Math.abs(piece.getPosition()[0] - targetPosition[0]) > 1))) {
            return false;
        }
        return true;
    }

    private static boolean canScoutMove(Piece piece, int[] targetPosition, Piece[][] positionArray) {
        if(piece.getPosition()[0] == targetPosition[0]) {
            if(targetPosition[1]  > piece.getPosition()[1]) {
                for (int i = piece.getPosition()[1]; i <= targetPosition[1]; i++) {
                    if(positionArray[0][i] != null) {
                        return false;
                    }
                }
                return true;
            } else if(targetPosition[1]  < piece.getPosition()[1]) {
                for (int i = piece.getPosition()[1]; i >= targetPosition[1]; i--) {
                    if(positionArray[0][i] != null) {
                        return false;
                    }
                }
                return true;
            } else {
                System.out.println("Sth went wrong in CanScoutMove");
                return false;
            }
        } else if(piece.getPosition()[1] == targetPosition[1]) {
           if(targetPosition[0]  > piece.getPosition()[0]) {
                for (int i = piece.getPosition()[0]; i <= targetPosition[0]; i++) {
                    if(positionArray[i][1] != null) {
                        return false;
                    }
                }
                return true;
            } else if(targetPosition[1]  < piece.getPosition()[1]) {
                for (int i = piece.getPosition()[0]; i >= targetPosition[0]; i--) {
                    if(positionArray[i][1] != null) {
                        return false;
                    }
                } 
                return true;
            } else {
                System.out.println("Sth went wrong in CanScoutMove");
                return false;
            }
        } else {
            System.out.println("Error in the Movement Logic");
            return false;
        } 
    }

    public static void move(Piece piece, int[] targetPosition, Piece[][] positionArray) {
        positionArray[targetPosition[0]][targetPosition[1]] = positionArray[piece.getPosition()[0]][piece.getPosition()[1]];
        positionArray[piece.getPosition()[0]][piece.getPosition()[1]] = null;
        piece.setPosition(targetPosition);
    }

    public static ArrayList<Integer[]> returnPossiblePositions(int[] currentPosition, Piece[][] positionArray) {
        ArrayList<Integer[]> possiblePositions = new ArrayList<>();

        return possiblePositions;
    }
}
