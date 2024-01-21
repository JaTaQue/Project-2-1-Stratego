package AI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import Logic.GameLogic.AttackLogic;
import Logic.GameLogic.MoveLogic;
import Logic.PieceLogic.Piece;
import Logic.PlayerClasses.AIPlayer;
import Logic.PlayerClasses.Player;
import Logic.Tester.Test;

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

            if(piece != null && !piece.isDead() && piece.getRank()!=-1){

                    int[] piecePos = piece.getPosition().clone();
                    ArrayList<int[]> moves = MoveLogic.returnPossiblePositions(piecePos, board);

                    for (int i = 0; i < moves.size(); i++) {
                        Piece[][] nextBoard = copyBoard(board);
                        AIPlayer copyPlayer = player.copyPlayer();
                        Player copyPlayer2 = enemyPlayer.copyPlayer();
                        if(nextBoard[moves.get(i)[0]][moves.get(i)[1]]==null){
                            MoveLogic.move(nextBoard[piecePos[0]][piecePos[1]], moves.get(i), nextBoard);
                        }
                        else{
                            AttackLogic.battle(nextBoard, piecePos, moves.get(i), copyPlayer, copyPlayer2);
                        }
                        nextNodes.add(new Node(nextBoard, this, piecePos, moves.get(i), copyPlayer, copyPlayer2));
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
                    int[] innitPosCopy = new int[2];
                    if(board[i][j].getRank()!=-1){
                        innitPosCopy[0] = board[i][j].getInnitPos()[0];
                        innitPosCopy[1] = board[i][j].getInnitPos()[1];
                    }
                    else{
                        innitPosCopy=null;
                    }
                    boardCopy[i][j] = new Piece(board[i][j].getRank(), board[i][j].getColor(), positionCopy, board[i][j].isDead(), board[i][j].isVisible(), board[i][j].isScout(), board[i][j].hasMoved(), innitPosCopy);
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

    //INFORMATION SET
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


                if(!(((board[i][j]==null)) || (board[i][j].getRank()==-1))){
                    if(board[i][j].getColor().equals(opponentColor)){
                        if(board[i][j].isVisible()){
                            // System.out.println(board[i][j].toString());
                            int currentValue = availablePiecesAmount.get(board[i][j].getRank()-1);
                            availablePiecesAmount.set(board[i][j].getRank()-1, currentValue-1);
                            newBoard[i][j] = board[i][j].copyPiece();
                        }
                        else if(board[i][j].isScout()) {
                            newBoard[i][j] = board[i][j].copyPiece();
                            VisibleScoutCount++;
                        }
                        else if(board[i][j].hasMoved()){
                            Moved.add(board[i][j].getPosition());
                        }
                        else{
                            notMovedYet.add(board[i][j].getPosition());
                        }
                    } else if(!board[i][j].getColor().equals(opponentColor)) {
                        newBoard[i][j] = board[i][j].copyPiece();
                    }
                }
            }
        }
        // int shifter = 1;
        // if(availablePiecesAmount.get(11) > 0) {
        //     shifter += 1;
        // }


        int currentValue = availablePiecesAmount.get(1);
        availablePiecesAmount.set(1, currentValue - VisibleScoutCount);

        int non_zero_count = Integer.MAX_VALUE;
        while(non_zero_count > 0) {
            if(Moved.size() > 0) {
                int randRank = (int) ((availablePiecesAmount.size() - 2)*Math.random() + 1);
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

        System.out.println("-----RANDO BOARD----");
        Test.boardToASCIIArt(newBoard);
        System.out.println("-----------------------");

        return newBoard;
    }
    
    public static Piece[][] getBestBoard(Piece[][] board, String opponentColor, Player opponenPlayer){
        // create a random board and send it to guess setup, then convert the result to CSV
        // then send the CSV to the ANN
        // ANN will return a board evaluation, then we will compare it to the best board evaluation
        // if it is better, we will save it as the best board
        // repeat this process 100 times
        // return the best board
        Piece[][] bestBoard = null;
        Model ANN = new Model();

        double bestBoardEval = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 3; i++) {
            Piece[][] randoBoard = getRandoBoard(board, opponentColor, opponenPlayer);
            Piece[][] guessBoard = guessSetup(randoBoard, opponentColor, opponenPlayer);
            createAndWriteCSV(guessBoard);
            
            double boardEval = ANN.predict();
            System.out.println("Board Evaluation: " + boardEval); // Print board evaluation for testing
            if(boardEval > bestBoardEval) {
                bestBoardEval = boardEval;
                bestBoard = guessBoard;
            }
        }
        System.out.println("Best Board Evaluation: " + bestBoardEval); // Print best board evaluation for testing
        return bestBoard;
    }
    
    //ANN
    public static Piece[][] guessSetup(Piece[][] board, String opponentColor, Player opponenPlayer){
        board = copyBoard(board);
        List<Integer> availablePiecesAmount = new ArrayList<>(Arrays.asList(1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 1, 6));
        // for (int i = 1; i < 13; i++) {
        //     int amount = opponenPlayer.howManyDeadOfRank(i);
        //     int currentValue = availablePiecesAmount.get(i-1);
        //     availablePiecesAmount.set(i-1, currentValue-amount);
        // }

        ArrayList<int[]> notMovedYet = new ArrayList<int[]>();
        ArrayList<int[]> Moved = new ArrayList<int[]>();

        int VisibleScoutCount = 0;
        Piece[][] newBoard = new Piece[10][10];
        ArrayList<Piece> opponentPieces = opponenPlayer.getAvailablePieces();

        for (Piece deadPieces : opponentPieces){
            // System.out.println(deadPieces.toString());
            if(deadPieces.isDead()){
                
                int[] deadPiecePos = deadPieces.getInnitPos();
                int rankDeadPiece = deadPieces.getRank();
                newBoard[deadPiecePos[0]][deadPiecePos[1]]=deadPieces.copyPiece();

                int currentValue = availablePiecesAmount.get(rankDeadPiece-1);
                availablePiecesAmount.set(rankDeadPiece-1, currentValue-1);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                
                if(!(((board[i][j]==null)) || (board[i][j].getRank()==-1))){
                    if(board[i][j].getColor().equals(opponentColor)){
                        if(board[i][j].isVisible()){
                            // System.out.println(board[i][j].toString());
                            int currentValue = availablePiecesAmount.get(board[i][j].getRank()-1);
                            availablePiecesAmount.set(board[i][j].getRank()-1, currentValue-1);
                            int[]startPos = board[i][j].getInnitPos();
                            newBoard[startPos[0]][startPos[1]] = board[i][j].copyPiece();
                        }
                        else if(board[i][j].isScout()) {
                            int[]startPos = board[i][j].getInnitPos();
                            newBoard[startPos[0]][startPos[1]] = board[i][j].copyPiece();
                            VisibleScoutCount++;
                        }
                        else if(board[i][j].hasMoved()){
                            Moved.add(board[i][j].getPosition());
                        }
                        else{
                            notMovedYet.add(board[i][j].getPosition());
                        }
                    }
                }
            }
        }
        // int shifter = 1;
        // if(availablePiecesAmount.get(11) > 0) {
        //     shifter += 1;
        // }

        //deleting scouts 
        int currentValue = availablePiecesAmount.get(1);
        availablePiecesAmount.set(1, currentValue - VisibleScoutCount);

        int non_zero_count = Integer.MAX_VALUE;
        while(non_zero_count > 0) {
            if(Moved.size() > 0) {
                int randRank = (int) ((availablePiecesAmount.size() - 2)*Math.random() + 1);
                //(availablePiecesAmount.size() - shifter)
                if(availablePiecesAmount.get(randRank - 1) > 0) {
                    Piece copiedPiece = board[Moved.get(0)[0]][Moved.get(0)[1]].copyPiece();
                    copiedPiece.setRank(randRank);
                    
                    newBoard[copiedPiece.getInnitPos()[0]][copiedPiece.getInnitPos()[1]] = copiedPiece;
                    Moved.remove(0);
                    availablePiecesAmount.set(randRank - 1, availablePiecesAmount.get(randRank - 1) - 1);
                }
            }  else if (notMovedYet.size() > 0) {
                int randRank = (int) (availablePiecesAmount.size()*Math.random() + 1);
                if(availablePiecesAmount.get(randRank - 1) > 0) {
                    Piece copiedPiece = board[notMovedYet.get(0)[0]][notMovedYet.get(0)[1]].copyPiece();
                    copiedPiece.setRank(randRank);
                    
                    newBoard[copiedPiece.getInnitPos()[0]][copiedPiece.getInnitPos()[1]] = copiedPiece;
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

        // System.out.println("-----SETUP BOARD----");
        // Test.boardToASCIIArt(newBoard, opponenPlayer);
        // System.out.println("-----------------------");
        return newBoard;
    }

    public static void createAndWriteCSV(Piece[][] boarder){

        //ONLY FOR BOARDS WITH 40 PIECES

        String[] field = new String[481];
        for (int i = 0; i < 480; i++) {
            field[i] = "Col" + (i + 1);
        }
        field[480] = "Class";
        
        String[] position = new String[481];
        int count = 0;
        for (int i = 9; i>5; i--){ //or just do for(int i = 9; i>6; i--)
            for (int j = 9; j>=0; j--) {
                switch (boarder[i][j].getRank()) {
                    case 1:
                        for(int z = 0; z<12;z++){
                            if(z==0){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                            
                        }
                        
                        break;
                    case 2:
                        for(int z = 0; z<12;z++){
                            if(z==1){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                    
                        break;
                    case 3:
                        for(int z = 0; z<12;z++){
                            if(z==2){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                    
                        
                        break;
                    case 4:

                        for(int z = 0; z<12;z++){
                            if(z==3){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 5:    

                        for(int z = 0; z<12;z++){
                            if(z==4){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }                    
                        break;
                    case 6:
                        for(int z = 0; z<12;z++){
                            if(z==5){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 7:    
                        
                        for(int z = 0; z<12;z++){
                            if(z==6){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 8:
                        
                        for(int z = 0; z<12;z++){
                            if(z==7){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 9:
                    
                        for(int z = 0; z<12;z++){
                            if(z==8){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 10:
                    
                        for(int z = 0; z<12;z++){
                            if(z==9){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 11:
                    
                        for(int z = 0; z<12;z++){
                            if(z==10){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;
                    case 12:
            
                        for(int z = 0; z<12;z++){
                            if(z==11){
                                position[count] = "1";
                            }else{
                                position[count] = "0";
                            }
                            count = count+1;
                        }
                        break;

                    
                }
            }
        }
        String csvFile = "RandomGuess.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Writing headers
            FileWriter fileWriter = new FileWriter(csvFile); 
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                // Write header row 
                bufferedWriter.write(String.join(",", field)); 
                bufferedWriter.newLine(); 
 
                // Write data rows 
                bufferedWriter.write(String.join(",", position));
            } 
 
            System.out.println("CSV written created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


