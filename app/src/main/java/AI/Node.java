package AI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.Player;
import Logic.PlayerClasses.RandomPlayer;

public class Node{
    int visitQuantity;
    double score;
    LinkedList<Node> children = new LinkedList<Node>();
    Node parent;
    AIPlayer player;
    Player enemyPlayer;
    Piece[][] board;
    int[] currentFigurePos;
    int[] nextFigurePos;

    public Node(Piece[][] board, Node parent, int[] currentFigurePos, int[] nextFigurePos, AIPlayer player, Player enemyPlayer){
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
        if(this.children.isEmpty()){
            return null;
        }
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
                if(piece != null && !piece.isDead() && piece.getRank()!=-1){
                    int[] piecePos = piece.getPosition().clone();
                    // System.out.println(Arrays.toString(piecePos));
                    ArrayList<int[]> moves = MoveLogic.returnPossiblePositions(piecePos, board);
                    // if(piece.getRank()==2 && !moves.isEmpty()){
                    //     for (int i = 0; i < moves.size(); i++) {
                    //         System.out.println(moves.get(i)[0]+" "+moves.get(i)[1]);
                    //     }
                     
                    // }
                    for (int i = 0; i < moves.size(); i++) {
                        Piece[][] nextBoard = copyBoard(board);

                        if(nextBoard[moves.get(i)[0]][moves.get(i)[1]]==null){
                            MoveLogic.move(nextBoard[piecePos[0]][piecePos[1]], moves.get(i), nextBoard);
                        }
                        else{
                            AttackLogic.battle(nextBoard, piecePos, moves.get(i), player, enemyPlayer);
                        }
                        nextNodes.add(new Node(nextBoard, this, piecePos, moves.get(i), player, enemyPlayer));
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

    public static Piece[][] getRandoBoard(Piece[][] board, String opponentColor, Player opponenPlayer){
        board = copyBoard(board);
        List<Integer> availablePiecesAmount = new ArrayList<>(Arrays.asList(1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 1, 6));
        for (int i = 1; i < 13; i++) {
            int amount = opponenPlayer.howManyDeadOfRank(i);
            int currentValue = availablePiecesAmount.get(i-1);
            availablePiecesAmount.set(i-1, currentValue-amount);
        }

        ArrayList<int[]> notMovedYet = new ArrayList<int[]>();
        ArrayList<int[]> Moved = new ArrayList<int[]>();

        int VisibleScoutCount = 0;
        Piece[][] newBoard = new Piece[10][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if(board[i][j]!=null){
                    if(board[i][j].getRank()==-1){
                        newBoard[i][j]=board[i][j].copyPiece();
                    }
                }


                if(!(((board[i][j]==null)) || (board[i][j].getRank()==-1))){//Color().equals(null))){
                    if(board[i][j].getColor().equals(opponentColor)){
                        if(board[i][j].isScout()) {
                            newBoard[i][j] = board[i][j].copyPiece();
                            VisibleScoutCount++;
                        }
                        else if(board[i][j].hasMoved()){
                            Moved.add(board[i][j].getPosition());
                        }
                        else{
                            notMovedYet.add(board[i][j].getPosition());
                        }
                    } else if(!board[i][j].getColor().equals(opponentColor) || board[i][j].isVisible()) {
                        newBoard[i][j] = board[i][j].copyPiece();
                        if(board[i][j].getColor().equals(opponentColor) && board[i][j].isVisible()) {
                            int currentValue = availablePiecesAmount.get(board[i][j].getRank()-1);
                            availablePiecesAmount.set(board[i][j].getRank()-1, currentValue-1);
                        }
                    }
                }
            }
        }
        int shifter = 0;
        if(availablePiecesAmount.get(11) > 0) {
            shifter += 1;
        }


        int currentValue = availablePiecesAmount.get(1);
        availablePiecesAmount.set(1, currentValue - VisibleScoutCount);

        int non_zero_count = Integer.MAX_VALUE;
        while(non_zero_count > 0) {
            if(Moved.size() > 0) {
                int randRank = (int) ((availablePiecesAmount.size() - shifter)*Math.random() + 1);
                if(availablePiecesAmount.get(randRank - 1) > 0) {
                    Piece copiedPiece = board[Moved.get(0)[0]][Moved.get(0)[1]].copyPiece();
                    copiedPiece.setRank(randRank);
                    
                    newBoard[Moved.get(0)[0]][Moved.get(0)[1]] = copiedPiece;
                    Moved.remove(0);
                    availablePiecesAmount.set(randRank - 1, availablePiecesAmount.get(randRank - 1) - 1);
                }
            }  else if (notMovedYet.size() > 0) {
                int randRank = (int) (availablePiecesAmount.size()*Math.random() + 1);
                if(availablePiecesAmount.get(randRank - 1) > 0) {
                    Piece copiedPiece = board[notMovedYet.get(0)[0]][notMovedYet.get(0)[1]].copyPiece();
                    copiedPiece.setRank(randRank);
                    
                    newBoard[notMovedYet.get(0)[0]][notMovedYet.get(0)[1]] = copiedPiece;
                    notMovedYet.remove(0);
                    availablePiecesAmount.set(randRank - 1, availablePiecesAmount.get(randRank - 1) - 1);
                }
            } else {
                System.out.println("Colonizer");
                for (int i = 0; i < availablePiecesAmount.size(); i++) {
                    System.out.print(availablePiecesAmount.get(i));
                }
            }

            non_zero_count = 0;
            for (int i = 0; i < availablePiecesAmount.size(); i++) {
                if(availablePiecesAmount.get(i) > 0) {
                    non_zero_count += 1;

                }
            }
        }
        return newBoard;
    } 

}


