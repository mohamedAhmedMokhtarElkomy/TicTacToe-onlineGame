package com.example.tictactoe.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    static int number_of_players = 0;
    private int id;
    private String name;
    private boolean isOn;
    private char symbol;
    Socket socket = null;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public Player(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, String name, char symbol) {
        number_of_players++;
        this.id = number_of_players;
        this.name = name;
        this.isOn = true;
        this.symbol = symbol;
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    //only get as it won't be change
    public char getSymbol() {
        return symbol;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void closeStream(){
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
