package AI;

import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.Player;
import Logic.Tester.Game;

public class MCTS {
    private int[][] lastMove;
    
    private static final double C = 0.15;
    private static final int ROLLOUT_TIME_MILLIS = 20;
    private static final long DURATION = 500;
    private final int LOOKAHEAD = 2;

    public int[][] findBestMove2 (Game game) {
        AIPlayer copyCurrent = (AIPlayer)game.getCurrentPlayer().copyPlayer();
        // RandomPlayer copyOpponent = (RandomPlayer)game.getEnemyPlayer().copyPlayer();
        Player copyOpponent = game.getEnemyPlayer().copyPlayer();
        //getBestBoard is ANN, getRandoBoard is not
        //Node root = new Node(Node.getRandoBoard(game.getBoard(), game.getEnemyPlayer().getColor(), copyOpponent), null, null, null, copyCurrent, copyOpponent);
        Node root = new Node(Node.getBestBoard(game.getBoard(), game.getEnemyPlayer().getColor(), copyOpponent), null, null, null, copyCurrent, copyOpponent);
        root.addChildren(root.expand());
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < DURATION) {
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
        Player copyOpponent = currentNode.enemyPlayer.copyPlayer();
        Piece[][] currBoard = Node.copyBoard(currentNode.board);
        Game currGame = new Game(copyCurrent, copyOpponent);
        currGame.setBoard(currBoard);
        currGame.setStarted();
        // System.out.print("rollout: ");   //print for mcts rollout
        long staxrtTime = System.currentTimeMillis();
        int moveCounter = 0;
        while((!currGame.isOver() && (System.currentTimeMillis()-staxrtTime) < ROLLOUT_TIME_MILLIS ) || moveCounter<=LOOKAHEAD) {
            int[] movablePosition = copyCurrent.getRandomMovablePosition(currGame);
            if(movablePosition!=null){
                int[] nextMove = copyOpponent.getRandomMove(currGame, movablePosition);
                currGame.makeAMove(movablePosition, currBoard[movablePosition[0]][movablePosition[1]], nextMove);
            }
            moveCounter++;
        }
        // Test.boardToASCIIArt(currBoard, currentPlayer);
        
        double evaluation_value = evalFunction(currGame, copyCurrent, copyOpponent, currentNode);
        return evaluation_value;
    }



    private Node treeTraversal(Node node) {
        try{
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
                        double uCB1 = score / (double)visits + C*Math.pow((Math.log(parentVisits)/(double)visits),0.5);
                        if(uCB1 > maxUCB1) {
                            maxUCB1 = uCB1;
                            maxiNode = currentNode;
                        }
                    }
                }
                if(maxiNode == null) for(int i = 0; i < 100; i++) 
                    System.out.println("null node wtf...");
                return treeTraversal(maxiNode);
            }
        }catch (NullPointerException npe){
            for(int i = 0; i < 100; i++) System.out.println("npe wtf...");
            return node;
        }
            
    }
    
    private double evalFunction(Game game, Player aiPlayer, Player oppPlayer, Node currNode) {

        // Piece[][] board = game.getBoard();
        // String color = aiPlayer.getColor();

        int friendlies = 0;
        int enemies = 0;

        for(int i = 1; i < 12; i++){
            friendlies += aiPlayer.getAvailablePieceAmount(i);
        }

        for(int i = 1; i < 12; i++){
            enemies += oppPlayer.getAvailablePieceAmount(i);
        }
    
        double evalScore = 1 - Math.pow(1 - ((double)friendlies / (double)enemies / 40.0), 8);
        
        double repetitionPenalty = computeRepetitionPenalty(currNode);
        evalScore += repetitionPenalty;

        if(evalScore <= -0.7)
            System.out.println("score too low");
        
        return evalScore;
    }    

    private double computeRepetitionPenalty(Node currentNode) {
        if (lastMove != null && lastMove[0].equals(currentNode.nextFigurePos) && lastMove[1].equals(currentNode.currentFigurePos)) {
            return -0.7;  
        } else {
            return 0; 
        }
    }
    
} 

