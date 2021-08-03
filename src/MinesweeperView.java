import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

/**
 * Minesweeper class.
 * @author nifemibolu
 * @version 1
 */
public class MinesweeperView extends Application {
    /**
     * main method.
     * @param args argument
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Stage secondary;

    /**
     * start the stage.
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) {
        secondary = primaryStage;
        primaryStage.setTitle("Minesweeper");
        Button btn = new Button();
        btn.setText("Start");
        //drop down
        ComboBox<Difficulty> choices = new ComboBox<Difficulty>();
        choices.getItems().addAll(Difficulty.values());
        choices.setPromptText("Select Difficulty...");


        //text input
        TextField name = new TextField();
        name.setPromptText("Name...");
        name.setMaxWidth(200);
        //name.setPrefColumnCount(10);
        //button action
        btn.setOnAction((e) -> {
            if (!(name.getText().isEmpty()) && choices.getValue() != null) {
                startGame(choices.getValue(), primaryStage, name.getText());
            } else {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("Both fields are required :(");
                a.show();
            }
        });
        VBox root = new VBox();
        root.getChildren().addAll(choices, name, btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();


    }
    // end of start method


    private MinesweeperGame dd;

    /**
     * starts the game.
     * @param d difficulty
     * @param s stage
     * @param name name input
     */
    public void startGame(Difficulty d, Stage s, String name) {
        dd = new MinesweeperGame(d);
        //creating layout
        GridPane grid = new GridPane();
        Button[][] buttons = new Button[15][15];
        for (int i = 0; i < 15; i++) {
            for (int k = 0; k < 15; k++) {
                Button tbutton = new Button("x");
                buttons[k][i] = tbutton;
                grid.add(tbutton, k, i);
                // had 1,1
                Object z;
                tbutton.setUserData(new int[] {k, i});
                Button newg = new Button("New Game");
                tbutton.setOnAction(new EventHandler<ActionEvent>() {
                    /**
                     * handler for button click on tile.
                     * @param actionEvent action event for tile
                     */
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int xt = ((int[]) tbutton.getUserData())[0];
                        int yt = ((int[]) tbutton.getUserData())[1];
                        Tile[] tileobj = dd.check(yt, xt);

                        if (tileobj.length == 1 && tileobj[0].isMine()) {
                            //display losing screen
                            VBox vb = new VBox();
                            vb.setSpacing(10);
                            Label lb = new Label("You lost," + name);
                            newg.setOnAction(new NewStart(secondary));
                            Scene lscene = new Scene(vb);
                            vb.getChildren().addAll(lb, newg);
                            s.setScene(lscene);
                            s.show();
                            //same inner class button to return to start new game


                        } else {
                            for (int i = 0; i < tileobj.length; i++) {
                                int x = tileobj[i].getX();
                                int y = tileobj[i].getY();
                                int gbm = tileobj[i].getBorderingMines();
                                buttons[x][y].setText(String.format("%s", gbm));
                                //buttons[x][y].setText(String.format("%s", gbm));
                                //display integers on surronding buttons
                                /**buttons.setText(gbm);*/
                                /**what do I do with x and y*/
                                if (dd.isWon()) {
                                    //display congrats screen
                                    VBox vbw = new VBox();
                                    vbw.setSpacing(10);
                                    Label lbw = new Label("You Won!," + name);
                                    newg.setOnAction(new NewStart(secondary));
                                    Scene wscene = new Scene(vbw);
                                    vbw.getChildren().addAll(lbw, newg);
                                    s.setScene(wscene);
                                    s.show();
                                    // same inner method for button to go to the new game screen
                                }


                            }

                        }

                    }
                });
                //end of button action

            }
        }
        //button click

        grid.getChildren();
        s.setScene(new Scene(grid, 500, 450));
        s.show();




    }
    //end of starGame helper method
    //new game button starter

    /**
     * newstart class.
     * @author nifemibolu
     * @version 2
     */
    private class NewStart implements EventHandler<ActionEvent> {
        NewStart(Stage secondary) {
            start(secondary);

        }

        /**
         * handler for new start.
         * @param e param for actionevent
         */
        public void handle(ActionEvent e) {
            NewStart newy = new NewStart(secondary);


        }

    }


}
