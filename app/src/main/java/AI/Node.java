package AI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;

public class Node{
    int visitQuantity;
    double score;
    LinkedList<Node> children = new LinkedList<Node>();
    Node parent;
    Player player;
    Player enemyPlayer;
    Piece[][] board;
    int[] currentFigurePos;
    int[] nextFigurePos;

    public Node(Piece[][] board, Node parent, int[] currentFigurePos, int[] nextFigurePos, Player player, Player enemyPlayer){
        visitQuantity = 0;
        score = 0;
        this.parent = parent;
        this.board = board;
        this.currentFigurePos = currentFigurePos;
        this.nextFigurePos = nextFigurePos;
        this.player = player;
        this.enemyPlayer = enemyPlayer;
    }

    public void incrementVisit_AddScore(double addedScore){
        this.visitQuantity++;
        this.score += addedScore;
        if(getParent() != null) {
            getParent().incrementVisit_AddScore(addedScore);
        }
    }

    public void addChildren(LinkedList<Node> children){
        this.children = children;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setParent(Node parent){
        this.parent=parent;
    }
    
    public double getScore(){
        return this.score;
    }
    
    public int getVisitQuantity(){
        return this.visitQuantity;
    }

    public LinkedList<Node> getChildren(){
        return this.children;
    }

    public Node getChild(int i){
        return this.children.get(i);
    }

    public int[] getCurrentPosition(){
        return this.currentFigurePos;
    }

    public int[] getNextPosition(){
        return this.nextFigurePos;
    }

    public Piece[][] getBoard(){
        return this.board;
    }

    LinkedList<Node> expand(){

        LinkedList<Node> nextNodes = new LinkedList<>();

        ArrayList<Piece> pieces = player.getPieces();

        for (Piece piece : pieces){

                // System.out.println("piece"+piece);
                if(piece != null && !piece.isDead()){
                    int[] piecePos = piece.getPosition().clone();
                    // System.out.println(Arrays.toString(piecePos));
                    ArrayList<int[]> moves = MoveLogic.returnPossiblePositions(piecePos, board);
                    
                    for (int i = 0; i < moves.size(); i++) {
                        Piece[][] nextBoard = copyBoard(board);

                        if(nextBoard[moves.get(i)[0]][moves.get(i)[1]]==null){
                            MoveLogic.move(nextBoard[piecePos[0]][piecePos[1]], moves.get(i), nextBoard);
                        }
                        else{
                            AttackLogic.battle(nextBoard, piecePos, moves.get(i), player, enemyPlayer);
                        }
                        nextNodes.add(new Node(nextBoard, this, piecePos, moves.get(i), enemyPlayer, player));
                    }
                }
                
            
        }
        return nextNodes;
    }

    // helper for node expansion
    public static Piece[][] copyBoard(Piece[][] board){
        Piece[][] boardCopy = new Piece[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == null){
                    boardCopy[i][j] = null;
                } else {
                    int[] positionCopy = {board[i][j].getPosition()[0], board[i][j].getPosition()[1]};
                    boardCopy[i][j] = new Piece(board[i][j].getRank(), board[i][j].getColor(), positionCopy);
                }
            }
        }
        return boardCopy;
    }

    public Node copyNode() {
        int[] currentPosCopy = currentFigurePos.clone();
        int[] targetPosCopy = nextFigurePos.clone();
        return new Node(copyBoard(board), null, currentPosCopy, targetPosCopy, player, enemyPlayer);
        //players need copies
    }

}
