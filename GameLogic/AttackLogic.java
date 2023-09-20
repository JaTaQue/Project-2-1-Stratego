package GameLogic;
import PieceClasses.Piece;

public class AttackLogic {
    private Piece AttackFigure;
    private Piece DefenderFigure;

    public void setAttackerPiece(Piece Attacker) {
        this.AttackFigure = Attacker;
    }

    public void setAttackerPiece(Piece Defender) {
        this.DefenderFigure = Defender;
    }

    public Piece getAttackFigure() {
        return AttackFigure;
    }

    public Piece getDefendFigure() {
        return AttackFigure;
    }

    public void battle() {
        if (AttackFigure.getRank() == 1 && DefenderFigure.getRank() == 10) {
            DefenderFigure.setDead();
        } else if (AttackFigure.getRank() == 3 && DefenderFigure.getRank() == 12) {
            DefenderFigure.setDead();
        } else if (Def.getRank() == 12) {
            AttackFigure.setDead();
        } else if (Def.getRank() == 11) {
            //Attacker wins
        } else if(AttackerFigure.getRank() == DefenderFigure.getRank()) {
            AttackerFigure.setDead();
            DefenderFigure.setDead();
        } else if(AttackerFigure.getRank() > DefenderFigure.getRank()) {
            DefenderFigure.setDead();
        } else if(AttackerFigure.getRank() < DefenderFigure.getRank()) {
            AttackFigure.setDead();
        } else {
            System.out.println("Error sth is wrong in the battle method")
        }
    }



}