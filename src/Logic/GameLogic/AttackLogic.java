package GameLogic;
import PieceLogic.Piece;
import PlayerClasses.*;

public class AttackLogic {
    private static PlayerInterface Attacker;
    private static PlayerInterface Defender;
    private static Piece attackerFigure;
    private static Piece defenderFigure;

    public static void setAttacker(PlayerInterface Attacking) {
        Attacker = Attacking;
    }

    public static void setDefender(PlayerInterface Defending) {
        Defender = Defending;
    }

    public static PlayerInterface getAttacker() {
        return Attacker;
    }

    public static PlayerInterface getDefender() {
        return Defender;
    }

    public static void switchRoles() {
        PlayerInterface newAttacker = Defender;
        PlayerInterface newDefender = Attacker;
        Attacker = newAttacker;
        Defender = newDefender;
    }

    public static void setAttackerPiece(Piece Attacker) {
        attackerFigure = Attacker;
    }

    public static void setDefenderPiece(Piece Defender) {
        defenderFigure = Defender;
    }

    public static Piece getAttackFigure() {
        return attackerFigure;
    }

    public static Piece getDefendFigure() {
        return defenderFigure;
    }

    public static boolean canAttack() {
        if(attackerFigure.getColor().equals(defenderFigure.getColor()) && (defenderFigure == null || defenderFigure.getRank() == -1)) {
            return false;
        }
        return true;
    }

    public static void battle() {
        if (attackerFigure.getRank() == 1 && defenderFigure.getRank() == 10) {
            defenderFigure.setDead();
        } else if (attackerFigure.getRank() == 3 && defenderFigure.getRank() == 12) {
            defenderFigure.setDead();
        } else if (defenderFigure.getRank() == 12) {
            attackerFigure.setDead();
        } else if (defenderFigure.getRank() == 11) {
            Attacker.setWinner();
        } else if(attackerFigure.getRank() == defenderFigure.getRank()) {
            attackerFigure.setDead();
            defenderFigure.setDead();
        } else if(attackerFigure.getRank() > defenderFigure.getRank()) {
            defenderFigure.setDead();
        } else if(attackerFigure.getRank() < defenderFigure.getRank()) {
            attackerFigure.setDead();
        } else {
            System.out.println("Error sth is wrong in the battle method");
        }
    }
}
