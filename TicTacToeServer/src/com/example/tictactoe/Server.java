package com.example.tictactoe;

import com.example.tictactoe.models.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    final static int serverPort = 1234;
    enum symbols {x , o};

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(serverPort);
        Socket socket;
        Player player1;
        Player player2;
        String name;

        while (true) {
            System.out.println("Server is waiting for player1...");

            socket = serverSocket.accept();

            System.out.println("Player 1 connected" + socket);
            player1 = new Player(socket, new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream()),
                    "player1",
                    'x');

            System.out.println("Server is waiting for player2...");

            socket = serverSocket.accept();

            System.out.println("Player 2 connected" + socket);
            player2 = new Player(socket, new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream()),
                    "player2",
                    'o');

            GameHandler gameHandler = new GameHandler(player1, player2);

            Thread thread = new Thread(gameHandler);
            thread.start();

        }

    }

}
