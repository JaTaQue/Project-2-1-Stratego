package Logic.PieceLogic;

import java.util.Arrays;

public class Piece {

    private final int RANK;
    private int[] position = new int[2];
    private boolean isDead;
    private final String COLOR;
    private boolean isVisible;

    /**
     * Constructor for the Piece
     * @param rank
     * @param color
     */
    public Piece(int rank, String color) {
        this.RANK = rank;
        this.COLOR = color;
    }

    /**
     * Constructor for the Piece
     * @param rank
     * @param color
     * @param position
     */
    public Piece(int rank, String color, int[] position) {
        this.RANK = rank;
        this.COLOR = color;
        this.position = position;
    }

    public Piece(int rank, String color, int[] position, boolean isDead) {
        this.RANK = rank;
        this.COLOR = color;
        this.position = position;
        this.isDead = isDead;
    }

    /**
     * Getter method to get the colour of a piece 
     * @param rank
     * @param color
     * @return Strong Colour 
     */
    public String getColor() {
        return COLOR;
    }

     /**
     * Getter method to get the rank of a piece 
     * @return int rank 
     */
    public int getRank() {
        return RANK;
    }

    /**
     * Getter method that return the possition or coordinate of a piece 
     * @return int array of the position of the piece
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * A method that can set a piece to dead or alive 
     * @return boolean if a piece is dead or not
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * A method that can set a piece to visible or not
     * @return boolean if a piece is visible or not
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 
     * @param integers sets the position to the new position of the piece  
     */
    public void setPosition(int[] integers) {
        this.position = integers;
    }


    /**
     * 
     * A setter method that sets a piece to be dead 
     */
    public void setDead() {
        this.isDead = true;
    }

    @Override
    public String toString() {
        return "Piece [RANK=" + RANK + ", position=" + Arrays.toString(position) + ", isDead=" + isDead + ", COLOR="
                + COLOR + "]";
    }

    public int[] getTile() {
        int[] tile = new int[2];
        tile[0] = position[0];
        tile[1] = position[1];
        return tile;
    }

    public Piece copyPiece() {
        int[] positionCopy = position.clone();
        return new Piece(this.RANK, this.COLOR, positionCopy, isDead);
    }

    public void setVisible() {
        this.isVisible = true;
    }
}