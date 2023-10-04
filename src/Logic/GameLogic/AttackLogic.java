package GameLogic;
import PieceLogic.Piece;
import PlayerClasses.*;

public class AttackLogic {

    public static boolean canAttack(int[] attackerPos,int[] defenderPos, Piece[][] positionArray) {
        attackerPos = attackerFigure.getPosition();
        int AttackerXPos = attackerPos[0];
        int AttackerYPos = attackerPos[1];

        defenderPos = defenderFigure.getPosition();
        int DefenderXPos = defenderPos[0];
        int DefenderYPos = defenderPos[1];

        
        //all other pieces 
        if (attackerFigure.getRank() != 2 && attackerFigure.getColor() != defenderFigure.getColor()) 
        {
            
            if(AttackerXPos == DefenderXPos)
            {
                    if(DefenderYPos - AttackerYPos > 0)
                    {
                        AttackerYPos++; // if the X cordiante are the same then it will go towards the piece in the Y axis
                    }
                    else {
                        AttackerYPos--;
                    }
                    if(AttackerYPos == DefenderYPos)
                        {
                             return true;
                        }   
            }
            else if(AttackerYPos == DefenderYPos)
            {
                    if(DefenderYPos - AttackerYPos > 0)
                    {
                        AttackerXPos++; // if the X cordiante are the same then it will go towards the piece in the Y axis
                    }
                    else {
                        AttackerXPos--;
                    }
                    if(AttackerXPos == DefenderXPos)
                        {
                            return true;
                        }
            }
            
        }

        //Scout 
        if (attackerFigure.getRank() == 2 && attackerFigure.getColor() != defenderFigure.getColor())
        {
            if(AttackerXPos == DefenderXPos)
            {
                for (int i=0; i < Math.abs(AttackerYPos-DefenderYPos); i++) // the loop is for the number od steps it takes
                {
                    if(DefenderYPos - AttackerYPos > 0)
                    {
                        AttackerYPos++; // if the X cordiante are the same then it will go towards the piece in the Y axis
                    }
                    else {
                        AttackerYPos--;
                    }
                   
                    if(positionArray[AttackerXPos][AttackerYPos] == null)
                        {
                            continue; // moves in the direction where there is no pice (null) until it reaches the piece 
                        }
                    else if(positionArray[AttackerXPos][AttackerYPos] != null)
                        {
                            break;
                        }
                    else if(AttackerYPos == DefenderYPos){
                        return true;
                    }
                } 
            }
            else if(AttackerYPos == DefenderYPos)
            {
                for (int i=0; i < Math.abs(AttackerXPos-DefenderXPos); i++)
                {
                    if(DefenderXPos - AttackerXPos > 0)
                    {
                        AttackerYPos++; // if the X cordiante are the same then it will go towards the piece in the Y axis
                    }
                    else {
                        AttackerXPos--;
                    } 
                    if(positionArray[AttackerXPos][AttackerYPos] == null)
                        {
                            continue;
                        }
                    else if(positionArray[AttackerXPos][AttackerYPos] != null)
                        {
                            break;
                        }
                    else if(AttackerXPos == DefenderXPos){
                        return true;
                    }
                } 
            }
        } 
        return false;
    }

    public static void battle(Piece[][] board, int[] attackerPosition, int[] defenderPosition, PlayerInterface Attacker, PlayerInterface Defender) {
        Piece attackerFigure = board[attackerPosition[0]][attackerPosition[1]];
        Piece defenderFigure = board[defenderPosition[0]][defenderPosition[1]];
        if (attackerFigure.getRank() == 1 && defenderFigure.getRank() == 10) {
            defenderFigure.setDead();
            Defender.addDeadPiece(defenderFigure);
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if (attackerFigure.getRank() == 3 && defenderFigure.getRank() == 12) {
            defenderFigure.setDead();
            Defender.addDeadPiece(defenderFigure);
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if (defenderFigure.getRank() == 12) {
            attackerFigure.setDead();
            Attacker.addDeadPiece(attackerFigure);
            board[attackerPosition[0]][attackerPosition[1]] = defenderFigure;
            board[defenderPosition[0]][defenderPosition[1]] = null;
            defenderFigure.setPosition(attackerPosition);
        } else if (defenderFigure.getRank() == 11) {
            Attacker.setWinner();
        } else if(attackerFigure.getRank() == defenderFigure.getRank()) {
            attackerFigure.setDead();
            Attacker.addDeadPiece(attackerFigure);
            defenderFigure.setDead();
            Defender.addDeadPiece(defenderFigure);
            board[defenderPosition[0]][defenderPosition[1]] = null;
            board[attackerPosition[0]][attackerPosition[1]] = null;
        } else if(attackerFigure.getRank() > defenderFigure.getRank()) {
            defenderFigure.setDead();
            Defender.addDeadPiece(defenderFigure);
            board[defenderPosition[0]][defenderPosition[1]] = attackerFigure;
            board[attackerPosition[0]][attackerPosition[1]] = null;
            attackerFigure.setPosition(defenderPosition);
        } else if(attackerFigure.getRank() < defenderFigure.getRank()) {
            attackerFigure.setDead();
            Attacker.addDeadPiece(attackerFigure);
            board[attackerPosition[0]][attackerPosition[1]] = defenderFigure;
            board[defenderPosition[0]][defenderPosition[1]] = null;
            defenderFigure.setPosition(attackerPosition);
        } else {
            System.out.println("Error sth is wrong in the battle method");
        }
        if(!Defender.hasPieces()) {
            Attacker.setWinner();
        } else if(!Attacker.hasPieces()) {
            Defender.setWinner();
        }
    }
}
