package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface Layout {

    int width = 600;
    int height = 600;
    String font = "-fx-font: 24 arial";
    Color primaryColor = Color.web("#00a6ff");
    Color secondaryColor = Color.BLACK;
    String backgroundColor = "-fx-font: 24 arial;-fx-text-fill: black;-fx-border-color: black; -fx-background-color:#00a6ff;";
    // -fx-border-color: grey; -fx-border-radius: 5;


    ////Return Scene from current class
    Scene getScene();

    //Return views inside current class as node to be reusable
    Node getNode();

    //Gather all views customization for readability
    void customizeViews();
}
