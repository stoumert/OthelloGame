package fr.univ_amu.m1info.board_game_library.model.service;


import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import java.io.Serializable;


public record GameState(Grid grid, Player player1, Player player2, Player currentPlayer) implements Serializable {
}
