package Logic.GameLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;

public class AttackLogic {

    /**
     * Checks whether an atttack is legal for a non scout and a scout 
     * @author Group 7
     * @version 1 
     * @param attackerFigure is the attacker figure
     * @param defenderFigure is the defender figure 
     * @param positionArray shows all the pices on the board and where they are
     * @returns boolean if the move can be done  
     */
    public static boolean canAttack(Piece attackerFigure, Piece defenderFigure, Piece[][] positionArray, String currentPlayerColor) {
        if(attackerFigure==null||defenderFigure==null||attackerFigure.getRank()==-1||defenderFigure.getRank()==-1){
            //System.out.println("one of the pieces is null or lake");
            return false;
        }
        if(attackerFigure.getColor()==null||defenderFigure.getColor()==null){
            return false;
        }
        if(!currentPlayerColor.equals(attackerFigure.getColor())){
            return false;
        }


        int [] attackerPos = attackerFigure.getPosition();
        int AttackerXPos = attackerPos[0];
        int AttackerYPos = attackerPos[1];
    
        int [] defenderPos = defenderFigure.getPosition();
        int DefenderXPos = defenderPos[0];
        int DefenderYPos = defenderPos[1];
    
        int boardHeight = 10;
        int boardWidth = 10;

        if(attackerFigure.getRank()==12||attackerFigure.getRank()==11||attackerFigure.getRank()==0||attackerFigure.getRank()==-1){
            return false;
        }
    
        // all other pieces
        if (attackerFigure.getRank() != 2 && !attackerFigure.getColor().equals(defenderFigure.getColor())) 
        {
            if (AttackerXPos == DefenderXPos) {
                if (Math.abs(DefenderYPos - AttackerYPos) == 1) 
                {
                    return true;
                }
            } 
            else if (AttackerYPos == DefenderYPos) 
            {
                if (Math.abs(DefenderXPos - AttackerXPos) == 1) 
                {
                    return true;
                }
            }
        }
    
        // Scout
        if (attackerFigure.getRank() == 2 && !attackerFigure.getColor().equals(defenderFigure.getColor())) 
        {
            if (AttackerXPos == DefenderXPos) 
            {
                int yDirection = Integer.compare(DefenderYPos, AttackerYPos); // this is better that doing -- and ++
    
                int newYPos = AttackerYPos + yDirection;
    
                while (newYPos != DefenderYPos) 
                {
                    if (newYPos >= 0 && newYPos < boardHeight && positionArray[AttackerXPos][newYPos] == null) 
                    {
                        newYPos += yDirection;
                    } 
                    else 
                    {
                        return false;
                    }
                }
                return true;
            } 
            else if (AttackerYPos == DefenderYPos) 
            {
                int xDirection = Integer.compare(DefenderXPos, AttackerXPos); // This is better that doing -- and ++
    
                int newXPos = AttackerXPos + xDirection;
    
                while (newXPos != DefenderXPos) 
                {
                    if (newXPos >= 0 && newXPos < boardWidth && positionArray[newXPos][AttackerYPos] == null) 
                    {
                        newXPos += xDirection;
                    } 
                    else 
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * sets the state of the piece either it dies (and adds it to the dead pieces arraylist) or wins
     * @author Group 7
     * @version 1   
     */
    public static void battle(Piece[][] board, int[] attackerPosition, int[] defenderPosition, Player Attacker, Player Defender) {
        Piece attackerFigure = board[attackerPosition[0]][attackerPosition[1]];
        Piece defenderFigure = board[defenderPosition[0]][defenderPosition[1]];
        if (attackerFigure.getRank() == 1 && defenderFigure.getRank() == 10) {
            defenderFigure.setDead();
            attackerFigure.setVisible();
            Defender.addDeadPiece(defenderFigure.getRank());
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if (attackerFigure.getRank() == 3 && defenderFigure.getRank() == 12) {
            defenderFigure.setDead();
            attackerFigure.setVisible();
            Defender.addDeadPiece(defenderFigure.getRank());
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if (defenderFigure.getRank() == 12) {
            attackerFigure.setDead();
            defenderFigure.setVisible();
            Attacker.addDeadPiece(attackerFigure.getRank());
            board[attackerPosition[0]][attackerPosition[1]] = null;
            board[defenderPosition[0]][defenderPosition[1]] = defenderFigure;
            // defenderFigure.setPosition(attackerPosition); what does this do
        } else if (defenderFigure.getRank() == 11) {
            defenderFigure.setDead();
            attackerFigure.setVisible();
            Defender.addDeadPiece(defenderFigure.getRank());
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            Attacker.setWinner();
            System.out.println(Attacker.getColor()+" wins by capturing the flag!");
        } else if(attackerFigure.getRank() == defenderFigure.getRank()) {
            attackerFigure.setDead();
            Attacker.addDeadPiece(attackerFigure.getRank());
            defenderFigure.setDead();
            Defender.addDeadPiece(defenderFigure.getRank());
            board[defenderPosition[0]][defenderPosition[1]] = null;
            board[attackerPosition[0]][attackerPosition[1]] = null;
        } else if(attackerFigure.getRank() > defenderFigure.getRank()) {
            defenderFigure.setDead();
            attackerFigure.setVisible();
            Defender.addDeadPiece(defenderFigure.getRank());
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if(attackerFigure.getRank() < defenderFigure.getRank()) {
            attackerFigure.setDead();
            defenderFigure.setVisible();
            Attacker.addDeadPiece(attackerFigure.getRank());
            board[attackerPosition[0]][attackerPosition[1]] = null;
            board[defenderPosition[0]][defenderPosition[1]] = defenderFigure;
            defenderFigure.setPosition(defenderPosition);
        } else {
            System.out.println("Error, something is wrong in the battle method");
        }
        if(Player.isSomeoneStuck(board).equals("Blue")){
            System.out.println("\nBlue is stuck");
            if(Attacker.getColor().equals("Blue")){
                Defender.setWinner();
            }
            else if(Attacker.getColor().equals("Red")){
                Attacker.setWinner();
            }
            else{
                System.out.println("\nError, something is wrong bottom battle method");
            }
        }
        if(Player.isSomeoneStuck(board).equals("Red")){
            System.out.println("\nRed is stuck");
            if(Attacker.getColor().equals("Red")){
                Defender.setWinner();
            }
            else if(Attacker.getColor().equals("Blue")){
                Attacker.setWinner();
            }
            else{
                System.out.println("\nError, something is wrong bottom battle method");
            }
        }
        if(Player.isSomeoneStuck(board).equals("Both")){
            System.out.println("\nReally weird, both are stuck!");
        }
    }
}
