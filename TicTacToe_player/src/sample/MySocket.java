package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class MySocket {

    final static String ip = "192.168.1.21";
    final static int SERVERPORT = 1234;

    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;


    String currentPlayerName = "";
    String otherPlayerName = "";
    char mySymbol;
    char myOpponentSymbol;
    boolean myTurn;
    boolean connectionFailed;

    public MySocket(String userName) {

        //Get user name from TextField found in home page
        currentPlayerName = userName;

        // establish the connection with SERVER PORT
        try {
            socket = new Socket(ip, SERVERPORT);
            System.out.println("Connected to server");

            // obtaining input and output streams
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //Send player name to server
            dataOutputStream.writeUTF(currentPlayerName);

            //Start thread that waits for opponent name
            waitingServerResponse.start();

        } catch (IOException exception) {
            System.out.println("failed to Connect to server " + exception.getMessage());
            connectionFailed = true;
            showError();
            return;
        }
        System.out.println("Searching for player...");
    }

    //Thread that waits for response from server carry opponent name and start first or not
    private final Thread waitingServerResponse = new Thread(new Runnable() {
        @Override
        public void run() {
            //Receive the other player name
            //and get which player turn
            try {
                //String received has the shape like => opponentName#myTurnIsFirst
                String received = dataInputStream.readUTF();

                //cut string at each # to get data needed
                StringTokenizer stringTokenizer = new StringTokenizer(received, "#");
                otherPlayerName = stringTokenizer.nextToken();
                if (stringTokenizer.nextToken().equals("true")) {
                    myTurn = true;
                    mySymbol = 'X';
                    myOpponentSymbol = 'O';
                } else {
                    myTurn = false;
                    mySymbol = 'O';
                    myOpponentSymbol = 'X';
                }

                //Change program stage scene from home page => game page
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GamePage gamePage = new GamePage(currentPlayerName, otherPlayerName);
                        Main.programStage.setScene(gamePage.getScene());
                    }
                });

            } catch (IOException exception) {
                System.out.println("Failed to get Data from server");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showError();
                    }
                });
            }
        }
    });

    //Close socket and Streams
    void close() {
        try {
            if (socket != null) {
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    void showError() {

        javafx.scene.control.Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Failed to connect to server");
        alert.setHeaderText("Please try again");
        alert.showAndWait();
    }
}
