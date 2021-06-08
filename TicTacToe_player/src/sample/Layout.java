package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface Layout {

    int width = 600;
    int height = 600;
    String font = "-fx-font: 24 arial";
    Color primaryColor = Color.web("#4169E1");
    Color secondaryColor = Color.BLACK;
    String backgroundColor = "-fx-background-color: #4169E1; -fx-font: 24 arial";


    ////Return Scene from current class
    Scene getScene();

    //Return views inside current class as node to be reusable
    Node getNode();

    //Gather all views customization for readability
    void customizeViews();
}
