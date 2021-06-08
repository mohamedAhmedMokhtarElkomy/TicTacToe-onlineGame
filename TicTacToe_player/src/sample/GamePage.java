package sample;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.StringTokenizer;

public class GamePage implements Layout{

    final String player1Name;
    final String player2Name;
    final GameHeader gameHeader;
    final GameBoard gameBoard;
    VBox vBox;
    boolean gameOn = true;

    //Constructor
    public GamePage(String player1Name, String player2Name) {
        vBox = new VBox();
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        gameHeader = new GameHeader(player1Name, player2Name);
        gameBoard = new GameBoard();
        vBox.getChildren().addAll(gameHeader.getNode(), gameBoard.getNode());

        customizeViews();
//        waitingMyTurn.start();
        communicateServer.start();
    }

    //Send and receive => roundTimer, opponent selection and when game is over
    Thread communicateServer = new Thread(new Runnable() {
        @Override
        public void run() {
            //Store message coming from server
            String message = "";
            while(gameOn){
                gameHeader.playerColorControl();
                try {
                    //get message from server at anytime while game is on
                    message = Main.socket.dataInputStream.readUTF();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    //TODO game is over
                    gameOn = false;
                }

                //message pattern => "KeyWord#Value"
                StringTokenizer stringTokenizer = new StringTokenizer(message, "#");
                switch (stringTokenizer.nextToken()){
                    case "index":
                        //game received index selected by the opponent
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                gameBoard.disableButton(Integer.parseInt(stringTokenizer.nextToken()));
                            }
                        });
                        Main.socket.myTurn = true;
                        gameBoard.enableGrid();
                        break;
                    case "gameOver":
                        //if game is over it receive the winner from server and why game is end
                        String s = stringTokenizer.nextToken();//s carry (true or false or draw)
                        gameOver(s, stringTokenizer.nextToken());
                        break;
                    case "roundTimer":
                        //From internet
                        Platform.runLater(new Runnable() {
                        @Override public void run() {
                            gameHeader.setTimer(stringTokenizer.nextToken());
                        }
                    });
                        break;
                    default:
                        System.out.println("Wrong pattern received from server");

                }
            }
        }
    });

    //TODO
    void disconnected(){}

    void gameOver(String iWin, String wayOfWin){
        gameOn = false;
        String text;
        switch (iWin) {
            case "true":
                text = "You won";
                break;
            case "false":
                text = "You lose";
                break;
            case "draw":
                text = "Draw";
                break;
            default:
                text = "Failed to get result from server";
        }

        //Show Alert dialog
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(text);
                alert.setHeaderText(wayOfWin);
                alert.setContentText("Click OK to go to home page");

                alert.showAndWait();

                //Go to home page
                HomePage homePage = new HomePage();
                Main.programStage.setScene(homePage.getScene());

                //Close socket
                Main.socket.close();
                Main.socket = null;
            }
        });
    }



    @Override
    public Scene getScene() {
        return new Scene(vBox, width,height);

    }

    @Override
    public Node getNode() {
        return vBox;
    }

    @Override
    public void customizeViews() {
//        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setSpacing(120);
//        vBox.setPadding(new Insets(50));
    }
}
