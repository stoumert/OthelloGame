package fr.univ_amu.m1info.board_game_library.graphics.javafx.app;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.*;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.controller.BoardGameCl;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXBoardGameApplication extends Application {

    @Override
    public void start(Stage stage) {
        MenuDialog menuDialog = new MenuDialog();
        menuDialog.showDialog(stage);
        String selectedMode = menuDialog.getSelectedChoice();
        if (selectedMode.equals("NEW_GAME")) {
            showNewGameDialog(stage);
        }else if (selectedMode.equals("LOAD_GAME")){
            showReloadGameDialog(stage);
        }else {
            stage.close();
        }

    }

    private BoardGameController createController(String mode, String strategy) {
        return switch (mode) {
            case "Human vs Human"-> new BoardGameCl();
            case "Human vs AI" -> new BoardGameCl(strategy);
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    private void showNewGameDialog(Stage stage) {
        GameSetupDialog setupDialog = new GameSetupDialog();
        setupDialog.showDialog(stage);
        if (setupDialog.isConfirmed()){
            show(stage, createController(
                    setupDialog.getSelectedMode(),
                    setupDialog.getSelectedStrategy()
            ));
        }else {
            stage.close();
        }

    }

    private void show(Stage stage, BoardGameController controller) {
        var launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        var configuration = launcher.getConfiguration();
        final JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, configuration);
        BoardGameControllableView view = viewBuilder.getView();
        view.setController(controller);
        controller.initializeViewOnStart(view);
        stage.show();
    }

    private void showReloadGameDialog(Stage stage) {
        ReloadGameDialog reloadDialog = new ReloadGameDialog();
        reloadDialog.showDialog(stage);
        if (reloadDialog.isConfirmed()) {
            show(stage, new BoardGameCl(reloadDialog.getSelectedSaveFile()));
        }else {
            stage.close();
        }

    }
}