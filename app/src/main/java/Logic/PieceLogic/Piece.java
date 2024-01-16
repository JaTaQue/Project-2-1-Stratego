package Logic.PieceLogic;

import java.util.Arrays;

public class Piece {

    private int RANK;
    private int[] position = new int[2];
    private boolean isDead;
    private final String COLOR;
    private boolean isVisible;
    private boolean isScout;
    private boolean hasMoved;
    private int[] innitPos = new int[2];
    
    /**
     * Constructor for the Piece
     * @param rank
     * @param color
     * @param position
     * @param isDead
     * @param isVisible
     * @param isScout
     * @param hasMoved
     */


    public Piece(int rank, String color, int[] position, boolean isDead, boolean isVisible, boolean isScout, boolean hasMoved, int[] initialPos) {
        this.RANK = rank;
        this.COLOR = color;
        this.position = position;
        this.isDead = isDead;
        this.isVisible = isVisible;
        this.isScout = isScout;
        this.hasMoved = hasMoved;
        this.innitPos = initialPos;
    }

    public int[] getInnitPos(){
        return innitPos;
    }
    public void setInnitPos(int[] pos){
        this.innitPos = pos;
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

    public void setRank(int rank) {
        this.RANK = rank;
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
                + COLOR + ", isVisible="+ isVisible + ", isScout="+ isScout + ", isMoved=" + hasMoved + ", initialPos=" + Arrays.toString(innitPos)+"]";
    }

    public int[] getTile() {
        int[] tile = new int[2];
        tile[0] = position[0];
        tile[1] = position[1];
        return tile;
    }

    public Piece copyPiece() {
        int[] positionCopy = position.clone();
        return new Piece(this.RANK, this.COLOR, positionCopy, this.isDead, this.isVisible, isScout, hasMoved, innitPos);
    }

    public void setVisible() {
        this.isVisible = true;
    }

    public void setScout() {
        this.isScout = true;
    }

    public void setMoved() {
        this.hasMoved = true;
    }

    public boolean isScout() {
        return this.isScout;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

   

}