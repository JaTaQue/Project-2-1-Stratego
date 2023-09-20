package PieceClasses;

public class Piece {
    private final int RANK;
    private int[] position = new int[2];
    private boolean isDead;
    private final String COLOR;

    public Piece(int rank, String color) {
        this.RANK = rank;
        this.COLOR = COLOR;
    }


    public String getColor() {
        return COLOR;
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