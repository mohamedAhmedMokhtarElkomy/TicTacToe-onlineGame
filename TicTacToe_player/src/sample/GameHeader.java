package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameHeader implements Layout{

    final private Label player1Name;
    final private Label player2Name;
    private Label timer;//Count down round timer

    //Horizontal container that contain, player 1 name, round count down, player 2 name
    HBox hBox;

    public GameHeader(String player1Name, String player2Name) {
        timer = new Label("0");
        hBox = new HBox();
        this.player1Name = new Label(player1Name);
        this.player2Name = new Label(player2Name);

        //Add the 3 Nodes to the horizontal box
        hBox.getChildren().addAll(this.player1Name, this.timer, this.player2Name);

        customizeViews();
    }

    public void setTimer(String time) {
        this.timer.setText(time);
    }

    void playerColorControl(){
        if (Main.socket.myTurn) {
            player1Name.setTextFill(primaryColor);
            player2Name.setTextFill(secondaryColor);
        }else{
            player2Name.setTextFill(primaryColor);
            player1Name.setTextFill(secondaryColor);
        }
    }

    @Override
    public Scene getScene() {
        return new Scene(hBox, width, height);
    }

    @Override
    public Node getNode() {
        return hBox;
    }

    @Override
    public void customizeViews() {
        //style player1 label
        player1Name.setTextFill(secondaryColor);
        player1Name.setStyle(font);

        //style player1 label
        player2Name.setTextFill(secondaryColor);
        player2Name.setStyle(font);

        //style player1 label
        timer.setTextFill(secondaryColor);
        timer.setStyle(font);

        //Customize the horizontal box
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(120);
        hBox.setPadding(new Insets(50));

        //Initial color for PlayerNames
        if(Main.socket.myTurn == true){
            player1Name.setTextFill(Color.GREEN);
            player2Name.setTextFill(Color.BLACK);
        }else{
            player2Name.setTextFill(Color.GREEN);
            player1Name.setTextFill(Color.BLACK);
        }
    }
}
