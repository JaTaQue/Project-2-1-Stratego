import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;

import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventTarget;

import PieceLogic.Piece;
import PieceLogic.PiecesCreator;
import PlayerClasses.HumanPlayer;
import PlayerClasses.PlayerInterface;
import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;

public class Game {
    private boolean isOver = false;
    private boolean hasStarted = false;
    private Piece[][] board;
    private PlayerInterface player1;
    private PlayerInterface player2;
    private PlayerInterface currentPlayer;
    private int[][] placingBordersPlayer1 = {{0, 9}, {0, 3}}; //{start x, end x}, {start y, end y}
    private int[][] placingBordersPlayer2 = {{0, 9}, {6, 9}}; //{start x, end x}, {start y, end y}

    public void createBoard(){
        this.board = new Piece[10][10];
        PiecesCreator.createLakes(this.board);
        //System.out.println(Arrays.deepToString(this.board));
    }

    public Piece[][] getBoard(){
        return this.board;
    }

    public HumanPlayer createHumanPlayer(String color){
        return new HumanPlayer(color);
    }

    public void addPlayers(PlayerInterface player1, PlayerInterface player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isOver(){
        return this.isOver;
    }

    public void setOver(){
        this.isOver = true;
    }

    public boolean hasStarted(){
        return this.hasStarted;
    }

    public void setStarted(){
        this.hasStarted = true;
    }

    public PlayerInterface getCurrentPlayer(){
        return this.currentPlayer;
    }

    public void setCurrentPlayer(PlayerInterface player){
        this.currentPlayer = player;
    }

    public void switchCurrentPlayer(){
        if(this.currentPlayer.equals(player1)) this.currentPlayer = player2;
        else this.currentPlayer = player1;
    }

    public void placePieces(PlayerInterface player){
        int counter = 0;
        ArrayList<Piece> availablePiece = player.getAvailablePieces();
    
        if(player.equals(player1)){
            for (int y = placingBordersPlayer1[1][0]; y <= placingBordersPlayer1[1][1]; y++) {
                for (int x = placingBordersPlayer1[0][0]; x <= placingBordersPlayer1[0][1]; x++) {
                    board[y][x]=availablePiece.get(counter);
                    availablePiece.get(counter).setPosition(new int[]{y, x});
                    counter++;
                }
            }
        }else{
            for (int y = placingBordersPlayer2[1][1]; y >= placingBordersPlayer2[1][0]; y--) {
                for (int x = placingBordersPlayer2[0][0]; x <= placingBordersPlayer2[0][1]; x++) {
                    board[y][x]=availablePiece.get(counter);
                    availablePiece.get(counter).setPosition(new int[]{y, x});
                    counter++;
                }
            }
            setStarted(); //start game after placing player2's pieces
        }
    }



}
