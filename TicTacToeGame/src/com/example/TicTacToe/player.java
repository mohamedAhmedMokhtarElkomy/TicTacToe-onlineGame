package com.example.TicTacToe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class player {
    final static int serverPort = 1234;
    static boolean myTurn = true;//TODO make it dynamic


    void waitingForTurn() {
        //TODO unclickable

    }


    public static void main(String[] args) throws IOException {
        Socket socket = null;
        String name = "tata";
        char[][] gameTable = new char[3][3];
        Scanner scanner = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection with server port 5056
        try {
            socket = new Socket(ip, serverPort);

            System.out.println("Connected to server");
        } catch (IOException exception) {
            //exception.getMessage();
            System.out.println("failed to Connect to server");
            return;
        }

        // obtaining input and output streams
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

//        try {
//            dataOutputStream.writeUTF(name);
//        } catch (IOException exception) {
//            exception.printStackTrace();

        Thread waitingMyTurn = new Thread(new Runnable() {
            //TODO unclickable buttons
            int index;

            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("waiting for other player turn...");
                        index = dataInputStream.readInt();
                        myTurn = true;
                        System.out.println("message received index:" + index);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        Thread sendMessage = new Thread(new Runnable() {
            boolean gameOn = true;
            String result = "";
            int index = 0;

            @Override
            public void run() {
                while (gameOn) {
                    //                    try {
//                        //index = dataInputStream.readInt();
//                        //System.out.println(index + " from");
//                        gameOn = dataInputStream.readBoolean();
//                    } catch (IOException exception) {
//                        exception.printStackTrace();
//                    }

//                    try {
//                        if (!gameOn)
//                            result = dataInputStream.readUTF();
//
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (myTurn) {
                        System.out.println(myTurn);
                        int message;
                        System.out.println("Enter number of row");
                        int row = scanner.nextInt();
                        System.out.println("Enter number of column");
                        int column = scanner.nextInt();
                        if (gameTable[row][column] != 0) {
                            System.out.println("it is already in use");
                        }
                        else {

                            message = (row * 3) + column;
                            try {
                                dataOutputStream.writeInt(message);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }

                            System.out.println("Waiting for other player turn...");
                            myTurn = false;

                        }
                    }
                }
            }
        });


        waitingMyTurn.start();
        sendMessage.start();

    }
}