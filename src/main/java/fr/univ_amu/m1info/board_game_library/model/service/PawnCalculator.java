package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PawnCalculator implements Serializable {
    /**
     * Calcule la liste des positions des pions qui seraient retournés si un pion est placé
     * à la position spécifiée par le joueur donné sur la grille spécifiée.
     *
     * @param position La position sur la grille où le pion doit être placé.
     * @param player   Le joueur tentant de placer le pion.
     * @param grid     L'état actuel de la grille de jeu.
     * @return Une liste des positions représentant les pions qui peuvent être retournés suite
     * au placement du pion à la position spécifiée.
     */
    public List<Position> calculatePawnToFlip(Position position, Player player, Grid grid) {
        if (grid.getPawnAtPosition(position) != Pawn.EMPTY) {
            return Collections.emptyList();
        }
        List<Position> pawnFlip = new ArrayList<>();
        Pawn opponent = player.getPawn().switchPawn();
        for (Direction direction : Direction.values()) {
            List<Position> pawnDirection = exploreDirection(grid, position, player, opponent, direction);
            pawnFlip.addAll(pawnDirection);
        }
        return pawnFlip;
    }

    /**
     * Explore une direction spécifique sur la grille à partir de la position donnée pour déterminer
     * la liste des pions adverses qui peuvent être retournés si le pion du joueur est placé à la position de départ.
     *
     * @param grid      La grille de jeu utilisée pour déterminer l'état du plateau et les positions des pions.
     * @param depart    La position de départ sur la grille à partir de laquelle l'exploration commence.
     * @param player    Le joueur dont le pion est placé sur la grille.
     * @param opponent  Le pion appartenant au joueur adverse.
     * @param direction La direction dans laquelle explorer les pions adverses.
     * @return Une liste des positions des pions adverses qui peuvent être retournés dans la direction
     * spécifiée. Retourne une liste vide si aucun pion ne peut être retourné.
     */

    private List<Position> exploreDirection(Grid grid, Position depart, Player player, Pawn opponent, Direction direction) {
        List<Position> pawnDirection = new ArrayList<>();
        int dx = direction.getX();
        int dy = direction.getY();
        int x = depart.x() + dx;
        int y = depart.y() + dy;

        Position newPosition = new Position(x, y);
        while (grid.isWithinBounds(newPosition)
                && grid.getPawnAtPosition(newPosition) == opponent
        ) {
            pawnDirection.add(newPosition);
            x += dx;
            y += dy;
            newPosition = new Position(x, y);
        }
        if (grid.isWithinBounds(newPosition) &&
            grid.getPawnAtPosition(newPosition) == player.getPawn() &&
            !pawnDirection.isEmpty()
        ) {
            return pawnDirection;
        }
        return Collections.emptyList();
    }

    /**
     * Compte le nombre de pions sur la grille qui appartiennent au joueur spécifié.
     *
     * @param grid   La grille de jeu où se trouvent les pions.
     * @param player Le joueur dont on compte les pions.
     * @return Le nombre total de pions appartenant au joueur spécifié sur la grille.
     */
    public int countPawn(Grid grid, Player player) {
        int count = 0;
        for (int i = 0; i< grid.getColumns();i++){
            for (int j = 0; j< grid.getRows(); j++){
                if (grid.getPawnAtPosition(new Position(i,j)) == player.getPawn()){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calcule toutes les positions valides sur la grille où le joueur spécifié peut placer un pion.
     * Une position est considérée comme valide si le placement d'un pion à cet endroit entraîne
     * le retournement d'au moins un pion adverse selon les règles du jeu.
     *
     * @param grid   La grille de jeu actuelle où le joueur tente de placer un pion.
     * @param player Le joueur qui tente de faire un coup.
     * @return Une liste des positions sur la grille qui sont valides pour le prochain coup du joueur.
     */
    public List<Position> calculateValidBlow(Grid grid, Player player) {
        List<Position> validBlows = new ArrayList<>();
        for (int i = 0; i< grid.getColumns();i++){
            for (int j = 0; j< grid.getRows(); j++){
                if (!calculatePawnToFlip(new Position(i,j),player,grid).isEmpty()){
                    validBlows.add(new Position(i,j));
                }
            }
        }
        return validBlows;
    }
}
