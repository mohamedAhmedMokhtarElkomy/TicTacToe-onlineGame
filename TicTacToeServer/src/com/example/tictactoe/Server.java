package com.example.tictactoe;

import com.example.tictactoe.models.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    final static int serverPort = 1234;
    final static int RoundTimer = 20;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(serverPort);
        Socket socket;
        Player player1;
        Player player2;
        String name;

        while (true) {
            System.out.println("Server is waiting for player1...");

            //Waiting for user to connect
            socket = serverSocket.accept();
            player1 = new Player(socket, new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream()),
                    "player1",
                    'x');
            System.out.println("connected");
            //Read name of player joined
            name = player1.getDataInputStream().readUTF();
            player1.setName(name);
            System.out.println("Player 1 connected his name is:  " + player1.getName() + "     " + socket);

            System.out.println("Server is waiting for player2...");

            //Waiting for user 2 to connect
            socket = serverSocket.accept();
            player2 = new Player(socket, new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream()),
                    "player2",
                    'o');
            //Reading player 2 name
            name = player2.getDataInputStream().readUTF();
            player2.setName(name);
            System.out.println("Player 2 connected his name is:  " + player2.getName() + "     " + socket);

            //Send opponent name for each player
            //Notify user if he starts first or not
            player1.getDataOutputStream().writeUTF(player2.getName() + "#true");
            player2.getDataOutputStream().writeUTF(player1.getName() + "#false");
            System.out.println("=======================================================");

            //Create game for each 2 players
            GameHandler gameHandler = new GameHandler(player1, player2);
            Thread thread = new Thread(gameHandler);
            thread.start();
        }
    }
}
