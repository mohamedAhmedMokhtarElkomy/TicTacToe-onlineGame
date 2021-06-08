package com.example.tictactoe;

import com.example.tictactoe.models.Player;

import java.io.IOException;


public class GameHandler extends TicTacToeUtilities implements Runnable {

    public GameHandler(Player player1, Player player2) {
        super(player1, player2);
        roundCountDown.start();
    }

    @Override
    public void run() {
        int index = 0;
        while (gameOn) {
            try {
                if (currentPlayerTurn.isOn()) {
                    //Read current player selected index
                    index = currentPlayerTurn.getDataInputStream().readInt();
                    convertIndex(index, currentPlayerTurn.getSymbol());
                    currentPlayerTurn.getDataOutputStream().writeUTF("index#" + index);
                }else {
                    gameOver("playerLeft");
                }
            } catch (IOException exception) {
                gameOver("playerLeft");
                System.out.println("game broke down");
                break;

            }
        }
        this.player1.closeStream();
        this.player2.closeStream();
    }

    void convertIndex(int index, char cell){
        int row = 0;
        while (index > 2) {
            index -= 3;
            row += 1;
        }
        if (cell == 'x')
            checkGame(row,index, Cell.X);
        if (cell == 'o')
            checkGame(row,index, Cell.O);
    }
}
