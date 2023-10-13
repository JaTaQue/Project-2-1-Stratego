package GameLogic;

import java.util.ArrayList;

import PieceLogic.Piece;

public class MoveLogic {

    public static boolean canMove(Piece piece, int[] targetPosition, Piece[][] positionArray, String currentPlayerColor) {
        if(piece == null || piece.getRank() == -1){
            return false;
        }
        if(!piece.getColor().equals(currentPlayerColor)){
            return false;
        }
        if(piece.getPosition()[0] == targetPosition[0] && piece.getPosition()[1] == targetPosition[1]) {
            return false;
        } else if(piece.getRank() == 12 || piece.getRank() == 11 || piece.getRank() == -1) {
            return false;
        } else if (positionArray[targetPosition[0]][targetPosition[1]] != null) {
            return false;
        } else if (piece.getPosition()[0] != targetPosition[0] && piece.getPosition()[1] != targetPosition[1]) {
            return false;
        } else if (piece.getRank() == 2 && !canScoutMove(piece, targetPosition, positionArray)) {
            return false;
        } else if (piece.getRank() != 2 &&((Math.abs(piece.getPosition()[1] - targetPosition[1]) > 1) || Math.abs(piece.getPosition()[0] - targetPosition[0]) > 1)) {
            return false;
        }
        return true;
    }

    private static boolean canScoutMove(Piece piece, int[] targetPosition, Piece[][] positionArray) {
        int currX = piece.getPosition()[0];
        int currY = piece.getPosition()[1];
        //just for you habibti <3

        int targX = targetPosition[0];
        int targY = targetPosition[1];

        if(currX == targX) {
            if(targY > currY) {
                for (int i = currY+1; i <= targY; i++) {
                    if(positionArray[currX][i] != null) {
                        return false;
                    }
                }
                return true;
            } else if(targY < currY) {
                for (int i = currY-1; i >= targY; i--) {
                    if(positionArray[currX][i] != null) {
                        return false;
                    }
                }
                return true;
            } else {
                System.out.println("Sth went wrong in CanScoutMove");
                return false;
            }
        } else if(currY == targY) {
           if(targX > currX) {
                for (int i = currX+1; i <= targX; i++) {
                    if(positionArray[i][currY] != null) {
                        return false;
                    }
                }
                return true;
            } else if(targX < currX) {
                for (int i = currX-1; i >= targX; i--) {
                    if(positionArray[i][currY] != null) {
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
        int i = currentPosition[0];
        int j = currentPosition[1];
        Piece currentPiece = positionArray[i][j];

        if(currentPiece == null || currentPiece.getRank() == -1 || currentPiece.getRank() > 10){
            return possiblePositions;
        }

        int[][] movesForPieces = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        //Scout pieace
        if (currentPiece.getRank() == 2) {
    
            for (int[] movement_Scout : movesForPieces) {
                int nextI_Scout = i + movement_Scout[0];
                int nextJ_Scout = j + movement_Scout[1];
    
                while ((0<=nextI_Scout && 10>nextJ_Scout) && (0<=nextJ_Scout && 10>nextI_Scout) && (positionArray[nextI_Scout][nextJ_Scout]==null || (positionArray[nextI_Scout][nextJ_Scout].getRank() != -1 && !positionArray[nextI_Scout][nextJ_Scout].getColor().equals(currentPiece.getColor()) ) ) ) {
                    
                    Integer[] newPositionOfScout = {nextI_Scout, nextJ_Scout};
                    possiblePositions.add(newPositionOfScout);
    
                    if(positionArray[nextI_Scout][nextJ_Scout]!=null){
                        break;
                    }

                    nextI_Scout += movement_Scout[0];
                    nextJ_Scout += movement_Scout[1];
                }
            }
        } else if(currentPiece.getRank() != 2) {
            for (int[] movement_Other : movesForPieces) {
                int nextI = i + movement_Other[0];
                int nextJ = j + movement_Other[1];
                if ((0<=nextI && 10>nextJ) && (0<=nextJ && 10>nextI) && (positionArray[nextI][nextJ]==null || (positionArray[nextI][nextJ].getRank() != -1 && !positionArray[nextI][nextJ].getColor().equals(currentPiece.getColor()) ) ) ) {
                    Integer[] newPosition_ofpiece = { nextI, nextJ };
                    possiblePositions.add(newPosition_ofpiece);
                }
            }
        }
        return possiblePositions;
    }

}
