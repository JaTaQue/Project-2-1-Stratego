import java.util.ArrayList;

import PieceLogic.*;
import PlayerClasses.*;
import GameLogic.*;

public class Board {
    public Piece[][] board;
    private static PlayerInterface Attacker;
    private static PlayerInterface Defender;
    private PlayerInterface isPlaying;
    private int[][] placingBorders = {{0,0}, {9,3}};
    private String colorOfPlayer1;


    public Board() {
        this.board = new Piece[10][10];
        PiecesCreator.createLakes(board);
    }

    public Piece getPiece(int[] position) {
        return board[position[0]][position[1]];
    }

    public String getPieceColor(int[] position) {
        return board[position[0]][position[1]].getColor();
    }

    public int getPieceRank(int[] position) {
        return board[position[0]][position[1]].getRank();
    }

    public boolean isPieceDead(int[] position) {
        return board[position[0]][position[1]].isDead();
    }

    public void createHumanPlayer(String color) {
        HumanPlayer a = new HumanPlayer(color);
        if(this.Attacker == null) {
            this.Attacker = a;
            AttackLogic.setAttacker(this.Attacker);
            this.colorOfPlayer1 = color;
            this.isPlaying = Attacker;
        } else {
            this.Defender = a;
            AttackLogic.setDefender(this.Defender);
        }
    }

    public void switchPlayers() {
        AttackLogic.switchRoles();
        if(isPlaying.getColor().equals(colorOfPlayer1)) {
            placingBorders[0][0] = 0;
            placingBorders[0][1] = 6;
            placingBorders[1][0] = 9;
            placingBorders[1][1] = 9;
        } else {
            placingBorders[0][0] = 0;
            placingBorders[0][1] = 0;
            placingBorders[1][0] = 9;
            placingBorders[1][1] = 3;
        }
        isPlaying = Defender;
    }

    public boolean canMoveWhileBuildUp(int[] targetPosition) {
        if(board[targetPosition[0]][targetPosition[1]] != null) {
            return false;
        } else if(placingBorders[0][1] < targetPosition[0] || targetPosition[0] < placingBorders[0][0]) {
            return false;
        } else if(placingBorders[1][1] < targetPosition[1] || targetPosition[1] < placingBorders[1][0]) {
            return false;
        }
        return true;
    }

    public int hasManysLeft(int rank) {
        int figuresLeft = 0;
        try {
            figuresLeft = Attacker.getPiecesAtBeginning().get(rank - 1).size();
        } catch (Exception e) {
            System.out.println("Sth went wrong with the ranks");
        }
        return figuresLeft;
    }

    public void setPiece(int rank, int[] targetPosition) {
        board[targetPosition[0]][targetPosition[0]] = Attacker.getPiecesAtBeginning().get(rank - 1).get(Attacker.getPiecesAtBeginning().get(rank - 1).size());
        Attacker.getPiecesAtBeginning().get(rank - 1).remove(Attacker.getPiecesAtBeginning().get(rank - 1).size() - 1);
    }

    public boolean isEveryPieceAtBeginningOnBoard() {
        int count = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null) {
                    count++; 
                }
            }
        }
        if(count != 48 || count != 88) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canMove(int[] currentPosition, int[] targetPosition) {
        return MoveLogic.canMove(getPiece(currentPosition), targetPosition, board);
    }

    public void move(int[] currentPosition, int[] targetPosition) {
        MoveLogic.move(getPiece(currentPosition), targetPosition, board);
    }

    public boolean canAttack(int[] attackerPosition, int[] defenderPosition) {
        AttackLogic.setAttackerPiece(getPiece(attackerPosition));
        AttackLogic.setDefenderPiece(getPiece(defenderPosition));
        return AttackLogic.canAttack();
    }

    public void battle(int[] attackerPosition, int[] defenderPosition) {
        AttackLogic.setAttackerPiece(getPiece(attackerPosition));
        AttackLogic.setDefenderPiece(getPiece(defenderPosition));
        AttackLogic.battle();
    }

    public ArrayList<Piece> getDeadPieces() {
        return isPlaying.getDeadPieces();
    }

    public ArrayList<Piece> getAvailablePieces() {
        return isPlaying.getAvailablePieces();
    }

    public ArrayList<Integer[]> returnAvailablePosition(int[] position) {
        return MoveLogic.returnPossiblePositions(position, board);
    }

    public PlayerInterface getAttacker(){
        return Attacker;
    }

    public PlayerInterface getDefender(){
        return Defender;
    }

    public PlayerInterface getIsPlaying(){
        return isPlaying;
    }



}

