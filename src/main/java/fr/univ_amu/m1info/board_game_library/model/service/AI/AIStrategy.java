package fr.univ_amu.m1info.board_game_library.model.service.AI;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;

import java.io.Serializable;

public interface AIStrategy extends Serializable {
    /**
     * Cette méthode permet de calculer la prochaine position à jouer en fonction de la strategy IA choisie
     * - EASY
     * - MEDIUM
     * - HARD
     * @param grid L'état actuel du grid
     * @param player Le joueur (une IA)
     * @return La position du prochain coup
     */
    Position chooseNextPosition(Grid grid, Player player);
}
