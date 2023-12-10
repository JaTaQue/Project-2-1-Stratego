package AI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.Player;
import Logic.Tester.Game;
import Logic.Tester.Test;

public class MCTS {
    
    public int[][] returnNextMove(Piece[][] board, Player currentPlayer, Player oppentPlayer) {
        //note: do we have reference issues with board?
        Player copyCurrent = currentPlayer.copyPlayer();
        Player copyOpponent = oppentPlayer.copyPlayer();
        int[][] nextMove = new int[2][2];
        Node s0 = new Node(board, null, null, null, copyCurrent, copyOpponent);
        Node currentNode = s0;
        // currentNode.addChildren(s0.getChildren());
        // System.out.println(s0.expand());
        currentNode.addChildren(s0.expand());
        // System.out.println(currentNode.getChildren());
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (elapsedTime < 10000) {
            currentNode = treeTraversal(currentNode);
            if(currentNode.getVisitQuantity() == 0) {
                //rollout method needs to be placed here
                currentNode.incrementVisit_AddScore(rollout(currentNode, currentPlayer, oppentPlayer));
            } else {
                currentNode.addChildren(s0.getChildren());
                currentNode = currentNode.getChild(0);
                //rollout needs to be implemented here
                currentNode.incrementVisit_AddScore(rollout(currentNode, currentPlayer, oppentPlayer));
            }
            currentNode = s0;
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        int posBestMove = -1;
        double bestValue = Integer.MIN_VALUE;
        for(int i = 0; i < s0.getChildren().size(); i++) {
            double currentValue = s0.getChild(i).getScore() / s0.getChild(i).getVisitQuantity();
            if(currentValue > bestValue) {
                posBestMove = i;
                bestValue = currentValue;
            }
        }
        Node bestMove = s0.getChild(posBestMove);
        nextMove[0] = bestMove.getCurrentPosition();
        nextMove[1] = bestMove.getNextPosition();
        return nextMove;
    }

    private double rollout(Node currentNode, Player currentPlayer, Player opponentPlayer) {
        while((!currentPlayer.isWinner()) && !opponentPlayer.isWinner()) {
            LinkedList<Node> children = currentNode.getChildren();
            int child_amount = children.size();
            int rand = (int) (child_amount*Math.random());
            currentNode = currentNode.getChild(rand);
            currentNode.setParent(null); //parent field is set null in order to save memory
        }
        double evaluation_value = evalFunction(currentNode,currentPlayer.getColor(), currentPlayer);
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

    private double evalFunction(Node node, String color, Player AIplayer)
    {
        double evalScore = 0;
        int TakenByAI = 0;
        int TakenByPlayer = 0;
        int TotRankAI = 0;
        int TotRankPlayer = 0;
        int rankDifference = 0;
        
        for (int i = 0; i < node.getBoard().length; i++) 
        {
            for (int j = 0; j < node.getBoard().length; j++) 
            {
                // this goes through the Pieces the AI took from the player 
                if((node.getBoard()[i][j].getColor()!= color) && node.getBoard()[i][j].isDead())
                {
                    TakenByAI++;
                    TotRankPlayer += node.getBoard()[i][j].getRank(); // records the score of the Pieces that was taken 
                }
                
                // this goes through the Pieces the Player took from the AI 
                if((node.getBoard()[i][j].getColor()== color) && node.getBoard()[i][j].isDead())
                {
                    TakenByPlayer++;
                    TotRankAI += node.getBoard()[i][j].getRank(); // records the score of the Pieces that was taken 
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
        for (int i = 0; i < node.getBoard().length; i++) 
        {
            for (int j = 0; j < node.getBoard().length; j++) 
            {
                // rank 11 is the flag and the colour of the AI == colour
                if(node.getBoard()[i][j].getRank() == 11 && node.getBoard()[i][j].getColor()== color) 
                {

                    // Check all 8 directions around the flag
                    for (int dx = -1; dx <= 1; dx++) 
                    {
                        for (int dy = -1; dy <= 1; dy++) 
                        {
                            // Skip the flag
                            if (dx == 0 && dy == 0) continue;
                            // this checks if the possition are within the board
                            if (i+dx >= 0 && i+dx < node.getBoard().length && j+dy >= 0 && j+dy < node.getBoard().length) 
                            {
                                // If there's an enemy piece next to the flag
                                if (node.getBoard()[i+dx][j+dy].getColor() != color) 
                                {
                                    evalScore -= 100;
                                }
                            }
                        }
                    }
                }
            }
        }

            return evalScore;
    }
    
    public static void main(String[] args) {
        // Game game = new Game();
        // while(!game.hasStarted()){ 
        //     System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");   

        //     //game.placePiecesBlackBox(game.getCurrentPlayer());
        //     game.placePiecesSimulation(game.getCurrentPlayer());
            
        //     System.out.println();
        //     game.switchCurrentPlayer(); 

        // }
        // generateNextStates(game);

        Game game = new Game();
        while(!game.hasStarted()){ 
            System.out.println("Current player: " + game.getCurrentPlayer().getColor()+"\n");   

            //game.placePiecesBlackBox(game.getCurrentPlayer());
            game.placePiecesSimulation(game.getCurrentPlayer());
            
            System.out.println();
            game.switchCurrentPlayer(); 

        }

        Test.boardToASCIIArt(game.getBoard(), game.getCurrentPlayer());

        // Piece[][] board = game.getBoard();
        // Piece[][] boardCopy = copyBoard(board);

        // System.out.println(game.getBoard());
        // System.out.println(board);
        // System.out.println(boardCopy);

        Node start = new Node(game.getBoard(), null, null, null, game.getCurrentPlayer(),game.getEnemyPlayer());

        start.expand();

        int count=0;
        for(Node n : start.children){
            Piece[][] board = n.board;
            System.out.println(count);
            System.out.println(n.player.getColor());
            Test.boardToASCIIArt(board, n.player);
            count++;
            System.out.println();
        }
    }
}

