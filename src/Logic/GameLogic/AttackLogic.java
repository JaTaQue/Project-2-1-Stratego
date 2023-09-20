package GameLogic;
import PieceLogic.Piece;

public class AttackLogic {
    private Piece attackerFigure;
    private Piece defenderFigure;

    public void setAttackerPiece(Piece Attacker) {
        this.attackerFigure = Attacker;
    }

    public void setDefenderPiece(Piece Defender) {
        this.defenderFigure = Defender;
    }

    public Piece getAttackFigure() {
        return attackerFigure;
    }

    public Piece getDefendFigure() {
        return attackerFigure;
    }

    public void battle() {
        if (attackerFigure.getRank() == 1 && defenderFigure.getRank() == 10) {
            defenderFigure.setDead();
        } else if (attackerFigure.getRank() == 3 && defenderFigure.getRank() == 12) {
            defenderFigure.setDead();
        } else if (defenderFigure.getRank() == 12) {
            attackerFigure.setDead();
        } else if (defenderFigure.getRank() == 11) {
            //Attacker wins
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
