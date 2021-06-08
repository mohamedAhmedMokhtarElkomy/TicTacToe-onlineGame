package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class GameBoard implements Layout , EventHandler<ActionEvent> {

    GridPane gridPane;
    Button[][] gameTable;

    public GameBoard() {
        gridPane = new GridPane();
        gameTable = new Button[3][3];

        //Loop for creating 9 button each button has
        //ID => index which will be send through web socket
        //onAction
        //Text
        for (int row = 0; row < 3; row++)
        {
            for (int column = 0; column < 3; column++)
            {
                gameTable[row][column] = new Button();
                gameTable[row][column].setText(" ");
                gameTable[row][column].setId(String.valueOf((row * 3) + column));
                gameTable[row][column].setOnAction(this);
//                gameTable[row][column].setStyle(backgroundColor);
                gameTable[row][column].setTextFill(secondaryColor);
                gameTable[row][column].setMaxSize(50,50);
                gameTable[row][column].setMinSize(50,50);


                //ADD button to 3 x 3 grid
                gridPane.add(gameTable[row][column], column, row);
            }
        }
        customizeViews();
    }

    //When button is pressed get its ID and make it non clickable
    @Override
    public void handle(ActionEvent actionEvent) {

        //Store pressed Button in button
        Button button = (Button) actionEvent.getSource();

        //Send ID of selected button to server
        try {
            Main.socket.dataOutputStream.writeInt(Integer.parseInt(button.getId()));
        }catch (IOException exception){
            exception.printStackTrace();
        }

        //make the pressed button unable to click it again
        button.setDisable(true);

        //toggle my turn
        Main.socket.myTurn = false;

        //Write my symbol
        button.setText(String.valueOf(Main.socket.mySymbol));

        gridPane.setDisable(true);
        System.out.println("Waiting for other player turn...");
    }

    //Make the opponent selected button non clickable
    void disableButton(int index){
        int row = 0;
        while (index > 2) {
            index -= 3;
            row += 1;
        }
        gameTable[row][index].setDisable(true);
        gameTable[row][index].setTextFill(secondaryColor);
        gameTable[row][index].setText(String.valueOf(Main.socket.myOpponentSymbol));
        gameTable[row][index].setStyle("");
        System.out.println("Opponent selected: "+ index);
    }

    void enableGrid(){
        gridPane.setDisable(false);
    }

    @Override
    public Scene getScene() {
        return new Scene(gridPane, width, height);
    }

    @Override
    public Node getNode() {
        return gridPane;
    }

    @Override
    public void customizeViews() {
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        gridPane.setDisable(!Main.socket.myTurn);
    }
}
