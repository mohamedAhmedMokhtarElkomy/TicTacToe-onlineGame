package com.example.tictactoe;

import com.example.tictactoe.models.Player;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class TicTacToeUtilities {

    enum Cell{
        EMPTY,
        X,
        O,
    }

    protected Cell[][] GameTable = new Cell[3][3];
    protected Player player1;
    protected Player player2;
    protected Player currentPlayerTurn;
    protected float roundTimer;
    protected boolean gameOn;
    int numberOfRounds;

    public TicTacToeUtilities(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayerTurn = player1;
        roundTimer = Server.RoundTimer;
        gameOn = true;
        numberOfRounds = 0;

        //Initialize game table
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                GameTable[row][column] = Cell.EMPTY;
            }
        }
    }

    void checkGame(int row, int column, Cell cellContent){
        numberOfRounds++;
        GameTable[row][column] = cellContent;

        //Check the Same column
        for(int r = 0; r <= 2; r++){
            if (GameTable[r][column] != cellContent)
                break;
            else if(r == 2) {
                gameOver("normal");
            }
        }

        //Check the same row
        for (int c = 0; c <= 2; c++){
            if(GameTable[row][c] != cellContent)
                break;
            else if (c == 2){gameOver("normal");}
        }

        //Check diagonal
        if (row == column){
            for (int i = 0; i <= 2; i++){
                if (GameTable[i][i] != cellContent)
                    break;
                else if(i == 2){gameOver("normal");}
            }
        }
        //Check anti diagonal
        if (row + column == 2){
            for (int i = 0; i <= 2; i++){
                if (GameTable[i][2-i] != cellContent)
                    break;
                else if (i == 2){gameOver("normal");}
            }
        }

        if(numberOfRounds == 9){
            gameOver("draw");
        }

        //Print gameTable
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++){
                System.out.print(GameTable[i][j]);
            }
            System.out.println();
        }

        //Change current player turn and reset round countdown
        togglePlayer();
        resetCount();
    }

    void gameOver(String why){
        gameOn = false;
        System.out.println("game ended");

        if(why.equals("normal")){
            try {
                currentPlayerTurn.getDataOutputStream().writeUTF("gameOver#true#normal win");
                System.out.println("The winner is: " + currentPlayerTurn.getName());
                togglePlayer();
                currentPlayerTurn.getDataOutputStream().writeUTF("gameOver#false#normal win");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }else if (why.equals("draw"))
        {
            try {
                player1.getDataOutputStream().writeUTF("gameOver#draw#normal");
                player2.getDataOutputStream().writeUTF("gameOver#draw#normal");
                System.out.println("Draw");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }else if (why.equals("playerLeft")){
            togglePlayer();
            try {
                currentPlayerTurn.getDataOutputStream().writeUTF("gameOver#true#Player left");
                System.out.println("The winner is: " + currentPlayerTurn.getName());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        else if (why.equals("timeOut")){
            try {
                currentPlayerTurn.getDataOutputStream().writeUTF("gameOver#false#Time out");
                togglePlayer();
                currentPlayerTurn.getDataOutputStream().writeUTF("gameOver#true#Time out");
                System.out.println("The winner is: " + currentPlayerTurn.getName());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    //To change current player turn
    void togglePlayer() {
        if (currentPlayerTurn == player1)
            currentPlayerTurn = player2;
        else if (currentPlayerTurn == player2)
            currentPlayerTurn = player1;
    }

    //Reset countDown each round
    public void resetCount() {
        roundTimer = Server.RoundTimer;
    }

    Thread roundCountDown = new Thread(new Runnable() {
        @Override
        public void run() {
            while (roundTimer >= 0){
                if(gameOn){
                    try {
                        player1.getDataOutputStream().writeUTF("roundTimer#" + roundTimer);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        gameOver("playerLeft");
                    }
                    try {
                        player2.getDataOutputStream().writeUTF("roundTimer#" + roundTimer);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        gameOver("playerLeft");
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //decrement round timer each one second
                    roundTimer--;

                    //if Game is over make roundTimer - 1 to end loop
                }else {
                    roundTimer = -1;
                }

                if (roundTimer == 0)
                    gameOver("timeOut");

            }
        }
    });
}
