package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuDialog {

    public static final String NEW_GAME = "NEW_GAME";
    public static final String LOAD_GAME = "LOAD_GAME";
    public static final String CANCEL = "CANCEL";

    private String selectedChoice = CANCEL;

    public void showDialog(Stage parent) {
        Stage dialog = new Stage();
        dialog.initOwner(parent);
        dialog.setResizable(false);
        dialog.setTitle("Menu");


        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label menu = new Label("Welcome To Othello Game");
        menu.setStyle("-fx-font-size: 20px;");

        Button newGameButton = new Button("New game");

        Button loadGameButton = new Button("Reload game");

        Button exitButton = new Button("Exit");
        newGameButton.setStyle("-fx-font-size: 15px;");
        loadGameButton.setStyle("-fx-font-size: 15px;");
        exitButton.setStyle("-fx-font-size: 15px;");



        newGameButton.setOnAction(e -> {
            selectedChoice = NEW_GAME;
            dialog.close();
        });

        loadGameButton.setOnAction(e -> {
            selectedChoice = LOAD_GAME;
            dialog.close();
        });

        exitButton.setOnAction(e -> {
            selectedChoice = CANCEL;
            dialog.close();
            parent.close();
        });

        layout.getChildren().addAll(
                menu,
                newGameButton,
                loadGameButton,
                exitButton);

        Scene scene = new Scene(layout, 300, 250);
        dialog.setScene(scene);
        dialog.showAndWait();

    }
    public String getSelectedChoice() {
        return selectedChoice;
    }
}