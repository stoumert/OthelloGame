package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;

/**
 * La classe PlayerManager est responsable de la gestion de deux joueurs dans une partie et du maintien du
 * joueur actuel. Elle fournit des fonctionnalités pour récupérer les détails des joueurs et switch le
 * joueur courant pendant la partie.
 */
public class PlayerManager {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    public PlayerManager(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1.getPawn() == Pawn.BLACK ? player1 : player2;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }
    public void resetPlayer() {
        currentPlayer = player1;
    }
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
}
