import java.util.ArrayList;

import PieceLogic.*;
import PlayerClasses.*;
import GameLogic.*;

public class Board {
    public Piece[][] board;
    private static PlayerInterface Attacker;
    private static PlayerInterface Defender;
    private int[][] placingBorders = {{0,0}, {9,3}};
    private String colorOfPlayer1;


    public Board() {
        this.board = new Piece[10][10];
        PiecesCreator.createLakes(board);
    }

    public boolean isWinnerAttacker() {
        return Attacker.isWinner();
    }

    public boolean isWinnerDefender() {
        return Defender.isWinner();
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
        if(Attacker != null && Defender != null) {
            return;
        } else {
            HumanPlayer a = new HumanPlayer(color);
            if(this.Attacker == null) {
                this.Attacker = a;
                this.colorOfPlayer1 = color;
            } else {
                this.Defender = a;
            }
        }
    }

    public void switchPlayers() {
        if(Attacker.getColor().equals(colorOfPlayer1)) {
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
        PlayerInterface isPlaying = Attacker;
        Attacker = Defender;
        Defender = isPlaying;
    }

    public boolean canMoveWhileBuildUp(int[] targetPosition) {
        return MoveLogic.canMoveWhileBuildUp(targetPosition, board, placingBorders);
    }

    public int hasManysLeftWhileBuildUp(int rank) {
        return Attacker.hasManysLeft(rank);
    }

    public void setPiece(int rank, int[] targetPosition) {
        MoveLogic.setPiece(rank, targetPosition, Attacker, board);
    }

    public boolean isEveryPieceAtBeginningOnBoard() {
        return Attacker.isEveryPieceAtBeginningOnBoard(board);
    }

    public boolean canMove(int[] currentPosition, int[] targetPosition) {
        return MoveLogic.canMove(getPiece(currentPosition), targetPosition, board);
    }

    public void move(int[] currentPosition, int[] targetPosition) {
        MoveLogic.move(getPiece(currentPosition), targetPosition, board);
    }

    public boolean canAttack(int[] attackerPosition, int[] defenderPosition) {
        return AttackLogic.canAttack(getPiece(attackerPosition), getPiece(defenderPosition), board);
        //needs to be fixed
    }

    public void battle(int[] attackerPosition, int[] defenderPosition) {
        AttackLogic.battle(board, attackerPosition, defenderPosition, Attacker, Defender);
    }

    public int deadPiecesCountAttacker(int rank) {
        return Attacker.getDeadPiece(rank);
    }

    public int deadPiecesCountDefender(int rank) {
        return Defender.getDeadPiece(rank);
    }

    public int piecesLeftofRankAttacker(int rank) {
        return Attacker.getAvailablePieceAmount(rank);
    }

    public int piecesLeftofRankDefender(int rank) {
        return Attacker.getAvailablePieceAmount(rank);
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
}

