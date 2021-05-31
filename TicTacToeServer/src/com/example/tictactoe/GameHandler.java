package com.example.tictactoe;

import com.example.tictactoe.models.Player;
import java.io.IOException;


public class GameHandler extends TicTacToeRules implements Runnable{

    public GameHandler(Player player1, Player player2) {
        super(player1, player2);
    }

    @Override
    public void run() {
        int index = 0;

        while (gameOn){
            System.out.println("new round");
            try{
                if(currentPlayerTurn.isOn()){
                    //currentPlayerTurn.getDataOutputStream().writeBoolean(gameOn);
                    index = currentPlayerTurn.getDataInputStream().readInt();//TODO get input stream from specific player
                    gameTable[index] = currentPlayerTurn.getSymbol();
                    System.out.println("player turn: " + currentPlayerTurn + " => index: " + index);
                    togglePlayer();
                    currentPlayerTurn.getDataOutputStream().writeInt(index);
                    gameOn = checkGame();
                }
            }catch (IOException exception){
                //exception.printStackTrace();
                System.out.println("game broke down");
                break;

            }
        }
        System.out.println("game ended");
        this.player1.closeStream();
        this.player2.closeStream();
    }
}
