package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.IAPlayer;

import java.io.File;
import java.util.List;

/**
 * GameManager gère la logique d'une partie d'Othello pour deux joueurs
 * en déléguant la gestion des pions à {@link PlayerManager} et la logique métier a {@link Othello}
 *
 */
public class GameManager {
    private PlayerManager playerManager;
    private Grid grid;
    private final Othello othello;
    private final GameHistory gameHistory;
    public GameManager(Player player1, Player player2) {
        this.grid = new Grid();
        this.playerManager = new PlayerManager(player1, player2);
        this.othello = new OthelloGame();
        this.gameHistory = new GameHistory();
    }

    public GameManager(File file) {
        this.othello = new OthelloGame();
        this.gameHistory = new GameHistory();
        restoreAll(file);
    }

    public void restartGame() {
        this.gameHistory.clearAll();
        this.grid.initializeGrid();
        this.playerManager.resetPlayer();
    }

    public Player getCurrentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean playHumanMove(Position position) {
        Player currentPlayer = getCurrentPlayer();

        List<Position> validMoves = othello.calculateValidMoves(
                grid,
                currentPlayer
        );
        if (!validMoves.contains(position)) {
            return false;
        }
        pushInGameHistory();
        putPawnAndFlipOpponentPawns(position, currentPlayer);
        return true;
    }

    public void playIAMove(){
        pushInGameHistory();
        IAPlayer currentPlayer = (IAPlayer) getCurrentPlayer();
        Position position = currentPlayer.choosePosition(grid);
        putPawnAndFlipOpponentPawns(position, currentPlayer);
    }

    private void pushInGameHistory() {
        gameHistory.push(createHistoryState());
    }

    private void putPawnAndFlipOpponentPawns(Position position, Player player) {
        List<Position> toFlip = othello.calculatePawnsToFlip(
                grid,
                position,
                player
        );
        grid.putPawnAtPosition(position, player.getPawn());

        for (Position pos : toFlip) {
            grid.putPawnAtPosition(pos, player.getPawn());
        }

        playerManager.switchPlayer();
    }

    public boolean isGameOver() {
        return othello.isGameOver(
                grid,
                playerManager.getPlayer1(),
                playerManager.getPlayer2()
        );
    }

    public Player determineWinner() {
        return othello.determineWinner(
                grid,
                playerManager.getPlayer1(),
                playerManager.getPlayer2()
        );
    }

    public int getWinnerScore() {
        Player winner = othello.determineWinner(
                grid,
                playerManager.getPlayer1(),
                playerManager.getPlayer2()
        );
        return othello.getScore(grid, winner);
    }

    public List<Position> getPossibleMoves() {
        return othello.calculateValidMoves(
                grid,
                getCurrentPlayer()
        );
    }

    public boolean canPlayMove(){
        if(getPossibleMoves().isEmpty()) {
            playerManager.switchPlayer();
            return false;
        }
        return true;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private void restoreAll(File file) {
        try {
            GameState gameState = SaveFile.loadGameFromFile("sauvegarde/"+file.getName());
            this.playerManager = new PlayerManager(gameState.player1(), gameState.player2());
            this.grid = gameState.grid();
            this.playerManager.setCurrentPlayer(gameState.currentPlayer());
        } catch (Exception e) {
            System.out.println("the file "+file.getName()+" isn't a valid file or is corrupted");
        }

    }

    private void restoreFromGameState(HistoryState stateGrid) {
        grid.restore(stateGrid.grid());
        playerManager.setCurrentPlayer(stateGrid.currentPlayer());
    }

    public void undo() {
        HistoryState previousState = gameHistory.undo(createHistoryState());
        if (previousState == null) {
            return;
        }
        restoreFromGameState(previousState);
    }

    private HistoryState createHistoryState() {
        return new HistoryState(grid.getMatrix(), playerManager.getCurrentPlayer());
    }

    public void redo() {
        HistoryState nextState = gameHistory.redo(createHistoryState());
        if (nextState == null) {
            return;
        }
        restoreFromGameState(nextState);
    }

    public boolean canUndo() {
        return gameHistory.canUndo();
    }

    public boolean canRedo() {
        return gameHistory.canRedo();
    }
}
