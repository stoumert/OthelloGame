package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;

import java.util.List;

public interface Othello {
    /**
     * Calcule tous les coups valides pour un joueur
     * @param grid État actuel du plateau
     * @param player joueur
     * @return Liste des positions où le joueur peut jouer
     */
    List<Position> calculateValidMoves(Grid grid, Player player);

    /**
     * Calcule les pions qui seraient retournés pour un coup donné
     * @param grid État actuel du plateau
     * @param position Position du coup
     * @param player joueur
     * @return Liste des positions des pions à retourner (vide si invalide)

     **/
    List<Position> calculatePawnsToFlip(Grid grid, Position position, Player player);

    /**
     * Vérifie si la partie est terminée
     * @param grid État actuel du plateau.
     * @param player1 le premier joueur.
     * @param player2 le deuxième joueur.
     * @return true si aucun joueur ne peut jouer
     */
    boolean isGameOver(Grid grid, Player player1, Player player2);

    /**
     * Détermine le gagnant
     * @param grid État actuel du plateau
     * @return Le joueur gagnant (ou null si égalité)
     */
    Player determineWinner(Grid grid, Player player1, Player player2);

    /**
     * Calcule le score du joueur gagnant.
     * Le score est déterminé par le nombre de pions appartenant au joueur sur la grille.
     *
     * @param grid   L'état actuel de la grille de jeu.
     * @param winner le gagnant.
     * @return Le score du joueur.
     */
    int getScore(Grid grid, Player winner);
}
