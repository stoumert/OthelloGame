package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;

public record HistoryState(Pawn[][] grid, Player currentPlayer) {
}
