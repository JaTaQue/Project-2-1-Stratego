package Logic.PlayerClasses;

/**
 * The BaselinePlayer class represents a baseline agent in a AI vs AI or player vs AI game
 * It is a subclass of the Player class.
 * @author Group 7
 * @version 1 
 */
public class BaselinePlayer extends Player {
    /**
     * constructs a baseline agent Player object with the specified color
     * @author Group 7
     * @version 1 
     * @param color the color associated with the human player, "B" for Blue or "R" for Red
     */
    public BaselinePlayer(String color) {
        super(color);
    }

    @Override
    public boolean getIsPlayable(){
        return false;
    }

    
    public Player copyPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyPlayer'");
    }

}