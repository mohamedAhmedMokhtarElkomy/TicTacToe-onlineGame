package com.example.tictactoe;

import com.example.tictactoe.models.Player;

public class TicTacToeRules {

    protected char[] gameTable = new char[9];
    //Arrays.fill(arr, 0, 8, '');
    protected boolean gameOn;

    protected Player player1;
    protected Player player2;
    protected Player currentPlayerTurn;

    public TicTacToeRules(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayerTurn = player1;
        gameOn = true;
    }

    boolean checkGame() {

        if (gameTable[0] == gameTable[1] && gameTable[1] == gameTable[2] && (gameTable[0] == 'x' || gameTable[0] == 'o'))
            return false;
        else if (gameTable[3] == gameTable[4] && gameTable[4] == gameTable[5] && (gameTable[3] == 'x' || gameTable[3] == 'o'))
            return false;
        else if (gameTable[6] == gameTable[7] && gameTable[7] == gameTable[8] && (gameTable[6] == 'x' || gameTable[6] == 'o'))
            return false;
        else if (gameTable[0] == gameTable[4] && gameTable[4] == gameTable[8] && (gameTable[0] == 'x' || gameTable[0] == 'o'))
            return false;
        else if (gameTable[2] == gameTable[4] && gameTable[4] == gameTable[6] && (gameTable[2] == 'x' || gameTable[2] == 'o'))
            return false;
        else if (gameTable[0] == gameTable[3] && gameTable[3] == gameTable[6] && (gameTable[0] == 'x' || gameTable[0] == 'o'))
            return false;
        else if (gameTable[1] == gameTable[4] && gameTable[4] == gameTable[7] && (gameTable[1] == 'x' || gameTable[1] == 'o'))
            return false;
        else if (gameTable[2] == gameTable[5] && gameTable[5] == gameTable[8] && (gameTable[2] == 'x' || gameTable[2] == 'o'))
            return false;

        //togglePlayer();
        return true;
    }

    void togglePlayer() {
        if (currentPlayerTurn == player1)
            currentPlayerTurn = player2;
        else if (currentPlayerTurn == player2)
            currentPlayerTurn = player1;
    }
}
