package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.controller.BoardGameCl;
import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;

import java.util.List;

public class HelloApplication {
    public static void main(String[] args) {
        BoardGameConfiguration config = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration(
                                "Player : Black",
                                "currentPlayer",
                                LabeledElementKind.TEXT
                        ),
                        new LabeledElementConfiguration(
                                "",
                                "gameResult",
                                LabeledElementKind.TEXT
                        ),
                        new LabeledElementConfiguration(
                                "New game",
                                "restart",
                                LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration(
                                "Save",
                                "save",
                                LabeledElementKind.BUTTON
                        ),
                        new LabeledElementConfiguration(
                                "undo",
                                "undo",
                                LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration(
                                "redo",
                                    "redo",
                                LabeledElementKind.BUTTON
                                )

                )
        );

        BoardGameController controller = new BoardGameCl();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(config, controller);
    }

}
