package AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;

import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.Player;
import Logic.PlayerClasses.RandomPlayer;
import Logic.Tester.Game;

public class MCTS {

    private static final int ROLLOUT_TIME_MILLIS = 500;
    final int SIMULATION_COUNT = 20;
    private int[][] lastMove;
    private final int[] SCORES_PER_RANK_ALIVE = new int[]{0, 100, 2, 50, 5, 10, 20, 50, 100, 250, 500, 1000, 750}; // piece ranks go from 1 to 12, id 0 = 0 forconvenience in eval
    private final int[] SCORES_PER_RANK_VISIBLE = new int[]{0, 0, 0, 20, 5, 10, 15, 20, 25, 50, 100, 0, 200};
    // Rank -1 = Lake
    // Rank 1 = Spy
    // Rank 2 = Scout
    // Rank 3 = Miner
    // Rank 4 = Sergeant
    // Rank 5 = Lieutenant
    // Rank 6 = Captain
    // Rank 7 = Major
    // Rank 8 = Colonel
    // Rank 9 = General
    // Rank 10 = Marshal
    // Rank 11 = Flag
    // Rank 12 = Bomb

    public int[][] findBestMove(Game game){
        //added type cast
        AIPlayer copyCurrent = (AIPlayer)game.getCurrentPlayer().copyPlayer();
        RandomPlayer copyOpponent = (RandomPlayer)game.getEnemyPlayer().copyPlayer();
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
            // printTree(root, 0);
        }

        Node bestChild = root.getChild(0);
        double bestScore = - (Integer.MAX_VALUE);

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

        lastMove = bestMove;

        return bestMove;

    }

    public int[][] findBestMove2 (Game game) {
        AIPlayer copyCurrent = (AIPlayer)game.getCurrentPlayer().copyPlayer();
        // RandomPlayer copyOpponent = (RandomPlayer)game.getEnemyPlayer().copyPlayer();
        Player copyOpponent = game.getEnemyPlayer().copyPlayer();
        Node root = new Node(Node.getRandoBoard(game.getBoard(), game.getEnemyPlayer().getColor(), game.getEnemyPlayer()), null, null, null, copyCurrent, copyOpponent);
        root.addChildren(root.expand());
        long startTime = System.currentTimeMillis();
        long duration = 2000;

        while ((System.currentTimeMillis() - startTime) < duration) {
            Node currentNode = treeTraversal(root);
            if (currentNode.getVisitQuantity() == 0){
                currentNode.incrementVisit_AddScore(rollout(currentNode));
            }else{
                if(currentNode.expand()!=null){
                    currentNode.addChildren(currentNode.expand());
                    Node childOfCurrent = currentNode.getChild(0);
                    if(childOfCurrent!=null){
                        currentNode = currentNode.getChild(0);
                        currentNode.incrementVisit_AddScore(rollout(currentNode));
                    }
                }
            }
        }

        Node bestChild = root.getChild(0);
        double bestScore = - (Integer.MAX_VALUE);

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

        lastMove = bestMove;

        return bestMove;

    }

    private double rollout(Node currentNode) {
        AIPlayer copyCurrent = currentNode.player.copyPlayer();
        // RandomPlayer copyOpponent = (RandomPlayer) currentNode.enemyPlayer.copyPlayer();
        Player copyOpponent = currentNode.enemyPlayer.copyPlayer();
        Piece[][] currBoard = Node.copyBoard(currentNode.board);
        Game currGame = new Game(copyCurrent, copyOpponent);
        currGame.setBoard(currBoard);
        currGame.setStarted();
        // System.out.print("rollout: ");   //print for mcts rollout
        long startTime = System.currentTimeMillis();
        int moveCounter = 0;
        int lookahead = 4;
        while((!currGame.isOver() && (System.currentTimeMillis()-startTime) < ROLLOUT_TIME_MILLIS ) || moveCounter<=lookahead) {
            // int[] movablePosition = currGame.getCurrentPlayer().getRandomMovablePosition(currGame);
            // int[] nextMove = currGame.getCurrentPlayer().getRandomMove(currGame, movablePosition);
            int[] movablePosition = copyCurrent.getRandomMovablePosition(currGame);
            int[] nextMove = copyOpponent.getRandomMove(currGame, movablePosition);
            currGame.makeAMove(movablePosition, currBoard[movablePosition[0]][movablePosition[1]], nextMove);
            moveCounter++;
        }
        // Test.boardToASCIIArt(currBoard, currentPlayer);
        
        double evaluation_value = evalFunction(currGame, copyCurrent, copyOpponent, currentNode);
        return evaluation_value;
    }



    private Node treeTraversal(Node node) {
        Node maxiNode = null;
        double maxUCB1 = Integer.MIN_VALUE; //variable got lowest value possible so that it changes for sure
        int parentVisits = node.getVisitQuantity();
        if(node.getChildren() == null||node.getChildren().isEmpty()) {
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
    
    private double evalFunction(Game game, Player aiPlayer, Player oppPlayer, Node currNode) {

        Piece[][] board = game.getBoard();
        String color = aiPlayer.getColor();
    
        double evalScore = 0;
        boolean capturedFlag = true;

    
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null && board[i][j].getRank() != -1) {
                    int pieceRank = board[i][j].getRank();
    
                    if (board[i][j].getColor() != color) {
                        evalScore -= SCORES_PER_RANK_ALIVE[pieceRank]; // Deduct points for not capturing opponent pieces
                        if(board[i][j].isVisible()){
                            evalScore += SCORES_PER_RANK_VISIBLE[pieceRank];
                        }
                        if (pieceRank == 11) capturedFlag = false;
                    } else if (board[i][j].getColor() == color) {     
                        evalScore += SCORES_PER_RANK_ALIVE[pieceRank]; // Award points for saving own pieces
                    }

                    double repetitionPenalty = computeRepetitionPenalty(currNode);
                    evalScore += repetitionPenalty;
                }
            }
        }

        // win / lose
        if (aiPlayer.isWinner()) {
            evalScore += 2000; 
            if(capturedFlag) evalScore += 1000;
        }else if (oppPlayer.isWinner()) {
            evalScore -= 2000; 
        }
        
        return evalScore;
    }    

    private double computeRepetitionPenalty(Node currentNode) {
        if (lastMove != null && lastMove[0].equals(currentNode.nextFigurePos) && lastMove[1].equals(currentNode.currentFigurePos)) {
            return -300; 
        } else {
            return 0; 
        }
    }
    
}

