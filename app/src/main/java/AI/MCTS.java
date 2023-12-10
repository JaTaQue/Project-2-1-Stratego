package AI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;
import Logic.Tester.Game;
import Logic.Tester.Test;

public class MCTS {

    private static final int ROLLOUT_TIME_MILLIS = 500;
    final int SIMULATION_COUNT = 50;

    public int[][] findBestMove(Game game){
        Player copyCurrent = game.getCurrentPlayer().copyPlayer();
        Player copyOpponent = game.getEnemyPlayer().copyPlayer();
        Node root = new Node(game.getBoard(), null, null, null, copyCurrent, copyOpponent);
        root.addChildren(root.expand());

        for(int i = 0; i < SIMULATION_COUNT; i++){
            Node currentNode = treeTraversal(root);
            if (currentNode.getVisitQuantity() == 0){
                currentNode.incrementVisit_AddScore(rollout(currentNode));
            }else{
                currentNode.addChildren(currentNode.expand());
                currentNode = currentNode.getChild(0);
                currentNode.incrementVisit_AddScore(rollout(currentNode));
            }
        }
// init: expand root
// 1) traverse: leaf w highest ucb1 (if not visited inf)
// 2) rollout if visit 0 and backprop, 
//     else expand and rollout first

        Node bestChild = root.getChild(0);
        double bestScore = -1;

        for (int i = 0; i < root.getChildren().size(); i++){
            Node currChild = root.getChild(i);
            double avgScore = currChild.score / currChild.visitQuantity;
            if (avgScore > bestScore){
                bestChild = currChild;
                bestScore = avgScore;
            }
        }        

        int[][] bestMove = new int[2][2];
        bestMove[0] = bestChild.getCurrentPosition();
        bestMove[1] = bestChild.getNextPosition();
        return bestMove;

    }

    private double rollout(Node currentNode) {
        Player copyCurrent = currentNode.player.copyPlayer();
        Player copyOpponent = currentNode.enemyPlayer.copyPlayer();
        Piece[][] currBoard = Node.copyBoard(currentNode.board);
        Game currGame = new Game(copyCurrent, copyOpponent);
        currGame.setBoard(currBoard);
        currGame.setStarted();
        System.out.print("rollout: ");
        long startTime = System.currentTimeMillis();
        while(!currGame.isOver() && System.currentTimeMillis()-startTime < ROLLOUT_TIME_MILLIS) {
            int[] movablePosition = currGame.getCurrentPlayer().getRandomMovablePosition(currGame);
            int[] nextMove = currGame.getCurrentPlayer().getRandomMove(currGame, movablePosition);
            currGame.makeAMove(movablePosition, currBoard[movablePosition[0]][movablePosition[1]], nextMove);
            // System.out.println("p1: " + currGame.getPlayer1().isWinner());
            // System.out.println("p2: " + currGame.getPlayer2().isWinner());

            // LinkedList<Node> children = currentNode.getChildren();
            // int child_amount = children.size();
            // int rand = (int) (child_amount*Math.random());
            // currentNode = currentNode.getChild(rand);
            // currentNode.setParent(null); //parent field is set null in order to save memory

            // 
        }
        // Test.boardToASCIIArt(currBoard, currentPlayer);
        
        double evaluation_value = evalFunction(currBoard, copyCurrent.getColor(), copyCurrent);
        return evaluation_value;
    }

    private Node treeTraversal(Node node) {
        Node maxiNode = null;
        double maxUCB1 = Integer.MIN_VALUE; //variable got lowest value possible so that it changes for sure
        int parentVisits = node.getVisitQuantity();
        if(!(node.getChildren() == null)) {
            return node; //break condition if we have a leaf node
        } 
        else {
            for(int i = 0; i < node.getChildren().size(); i++) {
                Node currentNode = node.getChild(i);
                int visits = currentNode.getVisitQuantity();
                if(visits == 0) {
                    return currentNode; //if visits is 0, UCB1 would infinity therefore we return already
                } else {
                    double score = currentNode.getScore();
                    //formula is vi + c*(np/ni)^0.5, where c = 0.6
                    double uCB1 = score + 0.6*Math.pow((Math.log(parentVisits)/visits),0.5);
                    if(uCB1 > maxUCB1) {
                        maxUCB1 = uCB1;
                        maxiNode = currentNode;
                    }
                }
            }
            return treeTraversal(maxiNode);
        }
    }

    private double evalFunction(Piece[][] board, String color, Player AIplayer)
    {
        double evalScore = 0;
        int TakenByAI = 0;
        int TakenByPlayer = 0;
        int TotRankAI = 0;
        int TotRankPlayer = 0;
        int rankDifference = 0;
        
        for (int i = 0; i < board.length; i++) 
        {
            for (int j = 0; j < board.length; j++) 
            {
                if (board[i][j] != null) {
                    // this goes through the Pieces the AI took from the player 
                    if((board[i][j].getColor()!= color) && board[i][j].isDead())
                    {
                        TakenByAI++;
                        TotRankPlayer += board[i][j].getRank(); // records the score of the Pieces that was taken 
                    }
                    
                    // this goes through the Pieces the Player took from the AI 
                    if((board[i][j].getColor()== color) && board[i][j].isDead())
                    {
                        TakenByPlayer++;
                        TotRankAI += board[i][j].getRank(); // records the score of the Pieces that was taken 
                    }
                }
                
            }
        }

        rankDifference = TotRankAI - TotRankPlayer; // we see the ranks that was lost on both sides 

        evalScore = evalScore - (80 - TakenByPlayer); // we subtract the number of pieces the AI lost 
        evalScore =  evalScore + (80 - TakenByAI); // we add the number of pieces that was taken by AI 

        evalScore = evalScore + rankDifference; // we add the rank difference to the evalScore



        if(AIplayer.isWinner() && AIplayer.getColor().equals(color))
        {
            evalScore = evalScore + 1000; // if AI wins add 1000 to the score 
        }
        if(!AIplayer.isWinner() && AIplayer.getColor().equals(color))
        {
            evalScore = evalScore - 1000; // if AI looses then we substract 1000 from the score 
        }
        

        // this llop looks at if there are any enemy pieces nect to the AI flag and removes point from the 
        // evalScore if there are any 
        for (int i = 0; i < board.length; i++) 
        {
            for (int j = 0; j < board.length; j++) 
            {
                if (board[i][j] != null) {
                    // rank 11 is the flag and the colour of the AI == colour
                    if(board[i][j].getRank() == 11 && board[i][j].getColor()== color) 
                    {

                        // Check all 8 directions around the flag
                        for (int dx = -1; dx <= 1; dx++) 
                        {
                            for (int dy = -1; dy <= 1; dy++) 
                            {
                                // Skip the flag
                                if (dx == 0 && dy == 0) continue;
                                // this checks if the possition are within the board
                                if (i+dx >= 0 && i+dx < board.length && j+dy >= 0 && j+dy < board.length) 
                                {
                                    // If there's an enemy piece next to the flag
                                    if (board[i+dx][j+dy] != null && board[i+dx][j+dy].getColor() != color) 
                                    {
                                        evalScore -= 100;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return evalScore;
    }
    
    
}

