package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class GameSetupDialog{
    private String selectedMode;
    private String selectedStrategy;
    private boolean confirmed = false;

    public void showDialog(Stage parent) {
        Stage dialog = new Stage();
        dialog.initOwner(parent);
        dialog.setTitle("Game configuration");
        dialog.setResizable(false);

        VBox layout = new VBox(10);
        List<String> gameModes = Arrays.asList("Human vs Human", "Human vs AI");

        Label modeLabel = new Label("choose mode game");
        ComboBox<String> modeCombo = new ComboBox<>();
        modeCombo.getItems().addAll(gameModes);
        modeCombo.setValue(gameModes.getFirst());

        List<String> strategies = Arrays.asList("Easy", "Medium", "Hard");
        Label strategyLabel = new Label("Difficulty:");
        ComboBox<String> strategyCombo = new ComboBox<>();
        strategyCombo.getItems().addAll(strategies);
        strategyCombo.setValue(strategies.getFirst());
        strategyCombo.setDisable(true);

        modeCombo.valueProperty().addListener((obs, oldVal, newVal) ->
                strategyCombo.setDisable(newVal.equals(gameModes.getFirst())));
        HBox modeHBox = new HBox(10);

        Button confirmButton = new Button("Play");

        Button cancelButton = new Button("Cancel");


        confirmButton.setOnAction(e -> {
            selectedMode = modeCombo.getValue();
            selectedStrategy = strategyCombo.getValue();
            confirmed = true;
            dialog.close();
        });

        cancelButton.setOnAction(e -> dialog.close());
        modeHBox.getChildren().addAll(confirmButton, cancelButton);
        modeHBox.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                modeLabel,
                modeCombo,
                new Separator(),
                strategyLabel,
                strategyCombo,
                new Separator(),
                modeHBox
        );
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public String getSelectedMode() { return selectedMode; }
    public String getSelectedStrategy() { return selectedStrategy; }
    public boolean isConfirmed() { return confirmed; }
}