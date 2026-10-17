package fr.univ_amu.m1info.board_game_library.graphics.javafx.alert;

import fr.univ_amu.m1info.board_game_library.model.service.GameManager;
import fr.univ_amu.m1info.board_game_library.model.service.SaveFile;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AlertBar {

    /**
     * Affiche une fenêtre de confirmation permettant de quitter ou sauvegarder la partie.
     * @param gameManager le gestionnaire de la partie
     */
    public static void showQuitDialog(GameManager gameManager) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit game");
            alert.setHeaderText("Do you want to save the game ?");

            ButtonType close = new ButtonType("Exit");
            ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(close, save, cancel);

            alert.showAndWait().ifPresent(response -> {
                if (response == close) {
                    System.exit(0);
                } else if (response == save) {

                    showDialogBox(gameManager);
                }

            });
        });
    }

    private static void showDialogBox(GameManager gameManager) {
        TextInputDialog dialog = new TextInputDialog("....");
        dialog.setTitle("Save game");
        dialog.setHeaderText("Enter the file name :");
        dialog.setContentText("File name :");

        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(save, cancel);


        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String filename = result.get().trim();

            if (filename.isEmpty()) {

                Alert error = new Alert(Alert.AlertType.ERROR, "Name cannot be empty!");
                error.showAndWait();
                return;
            }
            SaveFile.saveGameToFile(gameManager, "sauvegarde/"+filename);
            Alert ok = new Alert(Alert.AlertType.INFORMATION, "Game is save: " + filename);
            ok.showAndWait();

            System.exit(0);
        }
    }
}
