package PieceLogic;

import java.util.Arrays;

public class Piece {

    private final int RANK;
    private int[] position = new int[2];
    private boolean isDead;
    private final String COLOR;

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
     * 
     * @param newPosition sets the position to the new position of the piece  
     */
    public void setPosition(int[] newPosition) {
        this.position = newPosition;
    }


    /**
     * 
     * A setter method that sets a piece to be dead 
     */
    public void setDead() {
        this.isDead = true;
    }

    /**
     * Method the tells us the status of the piece including rank, position, if its dead and its colour
     * @returns string of the status of the piece  
     */
    public String toString() {
        return "Piece [RANK=" + RANK + ", position=" + Arrays.toString(position) + ", isDead=" + isDead + ", COLOR="
        + COLOR + "]";
    }
}