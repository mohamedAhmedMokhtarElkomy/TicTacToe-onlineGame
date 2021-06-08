package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class HomePage implements Layout{

    Label title;
    TextField nameField;
    Button searchButton;
//    BackgroundImage background;
    ImageView searchIcon;
    Label createdBy;
    ProgressIndicator progressIndicator;
    VBox vBox;
    boolean isLoading = false;

    //Initialize Views
    public HomePage() {

        searchIcon = new ImageView(loadImage("search.png"));
        title = new Label("TicTacToe");
        nameField = new TextField();
        searchButton = new Button("Search for player",searchIcon);
        progressIndicator = new ProgressIndicator();
        StackPane stackPane = new StackPane();
        VBox group = new VBox(nameField, stackPane);
        group.setSpacing(20);

        stackPane.getChildren().addAll(searchButton, progressIndicator);
        vBox = new VBox(title, group);

        customizeViews();
    }

    //Show or hide loading indicator and search button
    void toggleProgressIndicator(){
        isLoading = !isLoading;
        progressIndicator.setVisible(isLoading);
        searchButton.setVisible(!isLoading);

    }

    Image loadImage(String imageName){

        FileInputStream input = null;
        Image image = null;
        try {
            input = new FileInputStream("assets/images/" + imageName);
            image = new Image(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void searchForPlayer() {
        if(!nameField.getText().isEmpty())
        {
            toggleProgressIndicator();
            Main.socket = new MySocket(nameField.getText());
        }

        //TODO else show error => name field is empty

    }


    @Override
    public Scene getScene() {
        return new Scene(vBox, width, height);
    }

    @Override
    public Node getNode() {
        return vBox;
    }

    @Override
    public void customizeViews() {

        //style title
        title.setStyle("-fx-font: 54 arial");
        title.setTextFill(Color.rgb(255,255,255));

        //style name field
        nameField.setPromptText("Please Enter your name");
        nameField.setStyle(font);

        //style searchButton
        searchButton.setStyle(backgroundColor);
//        searchButton.setTextFill(secondaryColor);
        searchButton.setOnAction(e-> searchForPlayer());

        //style search icon
        searchIcon.setFitWidth(24);
        searchIcon.setFitHeight(24);

        //style indicator
        progressIndicator.setVisible(isLoading);

        //style vbox
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(200);
        vBox.setPadding(new Insets(20));

        //Background image
        BackgroundImage myBI= new BackgroundImage(loadImage("ticTacToe-3.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(myBI));

    }
}
