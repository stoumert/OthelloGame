package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;

import fr.univ_amu.m1info.board_game_library.graphics.javafx.alert.AlertBar;
import fr.univ_amu.m1info.board_game_library.model.*;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.player.IAPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.AI.AI;
import fr.univ_amu.m1info.board_game_library.model.service.AI.AIStrategy;
import fr.univ_amu.m1info.board_game_library.model.service.AI.HardAIStrategy;
import fr.univ_amu.m1info.board_game_library.model.service.GameManager;
import javafx.application.Platform;


import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BoardGameCl implements BoardGameController {

    private BoardGameView view;
    private final GameManager game;
    private boolean isProcessing = false;
    private boolean gameToSave = false;

    private final Timer timer = new Timer(true);
    private static final long DELAY = 2000;

    public BoardGameCl() {
        Player player1 = new HumanPlayer(Pawn.BLACK);
        Player player2 = new HumanPlayer(Pawn.WHITE);
        this.game = new GameManager(player1, player2);
    }

    public BoardGameCl(String strategy) {
        Player player1 = new HumanPlayer(Pawn.BLACK);
        AIStrategy strategyStrategy = AI.valueOf(strategy.toUpperCase()).getStrategy();
        if (strategyStrategy instanceof HardAIStrategy) {
            ((HardAIStrategy) strategyStrategy).setOpponent(player1);
        }
        Player player2 = new IAPlayer(Pawn.WHITE, strategyStrategy);
        this.game = new GameManager(player1, player2);
    }
    public BoardGameCl(File file) {
        this.game = new GameManager(file);
    }

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.view = view;
        for (int i = 0; i < game.getGrid().getRows(); i++) {
            for (int j = 0; j < game.getGrid().getColumns(); j++) {
                Color color;
                if ((i + j) % 2 == 0) {
                    color = Color.DARKGREEN;
                } else {
                    color =Color.LIGHTGREEN;
                }
                view.setCellColor(i, j, color);
            }
        }
        updateAll();
    }

    @Override
    public void boardActionOnClick(int row, int column) {
        if (!game.getCurrentPlayer().isAutomated() && !isProcessing) {
            Position pos = new Position(row, column);
            if (game.playHumanMove(pos)) updateAll();
            gameCheck();
        }
    }
    private void gameCheck() {
        if (game.isGameOver()) popupWiner();
        if (!game.canPlayMove()) updateAll();
        tryScheduleAI();
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        if ("restart".equals(buttonId)) {
            game.restartGame();
            initializeViewOnStart(view);
            view.updateLabeledElement("gameResult", "");
        }
        if ("save".equals(buttonId)) {
            AlertBar.showQuitDialog(game);
            gameToSave = true;
        }
        if("undo".equals(buttonId)) {
            if(game.canUndo()){
                game.undo();
                updateAll();
                view.updateLabeledElement("gameResult", "");
            }
        }
        if("redo".equals(buttonId)) {
            if(game.canRedo()){
                game.redo();
                updateAll();
            }
        }
    }
    private void updateAll() {
        updateLabelPlayer();
        updateView();
        markPossibleMoves();
    }

    private void tryScheduleAI() {
        if (!game.isGameOver() && !isProcessing && game.getCurrentPlayer().isAutomated() && !gameToSave) scheduleMoveAI();
    }

    private void scheduleMoveAI () {
        if (isProcessing || game.isGameOver()) return;

        isProcessing = true;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                game.playIAMove();
                Platform.runLater(() -> {
                    updateAll();
                    gameCheck();
                    isProcessing = false;
                    tryScheduleAI();
                });
            }
        },
                DELAY);
        updateAll();
    }


    private void updateView() {
        for (int i = 0; i < game.getGrid().getRows(); i++) {
            for (int j = 0; j < game.getGrid().getColumns(); j++) {
                Position pos = new Position(i, j);
                Pawn pawn = game.getGrid().getPawnAtPosition(pos);

                view.removeShapesAtCell(i, j);

                if (pawn == Pawn.BLACK) {
                    view.addShapeAtCell(i, j, Shape.CIRCLE, Color.BLACK);
                } else if (pawn == Pawn.WHITE) {
                    view.addShapeAtCell(i, j, Shape.CIRCLE, Color.WHITE);
                }
            }
        }
    }


    private void updateLabelPlayer() {
        String currentPlayerText = "Player : " +
                (game.getCurrentPlayer().getPawn() == Pawn.BLACK ? "Black" : "White");
        view.updateLabeledElement("currentPlayer", currentPlayerText);
    }
    private void popupWiner(){
        Player winner = game.determineWinner();
        int count = game.getWinnerScore();
        view.updateLabeledElement("gameResult", "The winner is: "+ (winner.getPawn() == Pawn.BLACK ? "Black" : "White") + " with " + count);
    }
    private void markPossibleMoves() {
        clearPossibleMovesMarkers();
        List<Position> possibleMoves = game.getPossibleMoves();

        for (Position position : possibleMoves) {
            view.addShapeAtCell(
                    position.x(),
                    position.y(),
                    Shape.STAR,
                    Color.DARKBLUE
            );
        }
    }
    private void clearPossibleMovesMarkers() {
        for (int row = 0; row < game.getGrid().getRows(); row++) {
            for (int col = 0; col < game.getGrid().getColumns(); col++) {
                view.removeShapesAtCell(row, col);
                Pawn pawn = game.getGrid()
                        .getPawnAtPosition(new Position(row, col));
                if (pawn != Pawn.EMPTY) {
                    Color color = (pawn == Pawn.BLACK) ? Color.BLACK : Color.WHITE;
                    view.addShapeAtCell(
                            row,
                            col,
                            Shape.CIRCLE,
                            color
                    );
                }
            }
        }
    }
}
