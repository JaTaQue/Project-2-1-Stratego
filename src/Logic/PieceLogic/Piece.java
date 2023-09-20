package PieceLogic;

import java.util.Arrays;

public class Piece {
    @Override
    public String toString() {
        return "Piece [RANK=" + RANK + ", position=" + Arrays.toString(position) + ", isDead=" + isDead + ", COLOR="
                + COLOR + "]";
    }

    private final int RANK;
    private int[] position = new int[2];
    private boolean isDead;
    private final String COLOR;

    public Piece(int rank, String color) {
        this.RANK = rank;
        this.COLOR = color;
    }

    public String getColor() {
        return COLOR;
    }

    public int getRank() {
        return RANK;
    }

    public int[] getPosition() {
        return position;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setPosition(int[] newPosition) {
        this.position = newPosition;
    }

    public void setDead() {
        this.isDead = true;
    }
}