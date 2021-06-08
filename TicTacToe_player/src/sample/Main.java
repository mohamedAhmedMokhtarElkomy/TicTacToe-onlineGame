package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

public class Main extends Application {

    final static int WIDTH = 500;
    final static int HEIGHT = 500;
    static MySocket socket;
    static Stage programStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO
        primaryStage.setOnCloseRequest(event -> {
            socket.disconnect();
            System.out.println("Stage is closing");
            // Save file
        });
        HomePage homePage = new HomePage();
        programStage = primaryStage;

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(homePage.getScene());
        primaryStage.show();
    }

//Font font = Font.font("Courier New", FontWeight.BOLD, 36);
//button.setFont(font);

    public static void main(String[] args) {
        launch(args);
    }
}
