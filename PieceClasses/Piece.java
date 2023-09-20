package PieceClasses;

public class Piece {
    private final int rank;
    private int[] position = new int[2];
    private boolean isDead;

    public Piece(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
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