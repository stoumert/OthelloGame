package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReloadGameDialog {

    private File selectedSaveFile;
    private boolean confirmed = false;

    public void showDialog(Stage parent) {
        Stage dialog = new Stage();
        dialog.initOwner(parent);
        dialog.setResizable(false);
        dialog.setTitle("Reload Game");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label label = new Label("Choose a save file to reload:");

        ComboBox<String> saveList = new ComboBox<>();
        List<String> saveFiles = getSavingFileNames();

        saveList.getItems().addAll(saveFiles);

        Button loadButton = new Button("Reload");
        Button cancelButton = new Button("Cancel");
        loadButton.setDisable(true);

        // Si aucun fichier de sauvegarde
        if (saveFiles.isEmpty()) {
            saveList.setPromptText("No save found");
            loadButton.setDisable(true);
        }

        saveList.valueProperty().addListener((obs, oldSel, newSel) -> {
            selectedSaveFile = new File("sauvegarde", newSel);
            loadButton.setDisable(false);
        });

        loadButton.setOnAction(e -> {
            confirmed = true;
            dialog.close();
        });

        cancelButton.setOnAction(e -> {
            confirmed = false;
            dialog.close();
            parent.close();
        });

        HBox buttonBox = new HBox(10, loadButton, cancelButton);
        buttonBox.setStyle("-fx-alignment: center;");

        layout.getChildren().addAll(label, saveList, buttonBox);

        dialog.setScene(new Scene(layout, 300, 220));
        dialog.showAndWait();
        //return confirmed;
    }

    private List<String> getSavingFileNames() {
        List<String> output = new ArrayList<>();
        File folder = new File("sauvegarde");

        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) output.add(f.getName());
                }
            }
        }
        return output;
    }

    public File getSelectedSaveFile() {
        return selectedSaveFile;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
