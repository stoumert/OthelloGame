package fr.univ_amu.m1info.board_game_library.model.service.AI;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.IAPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.PawnCalculator;

import java.util.List;
import java.util.Random;

public class EasyAIStrategy implements AIStrategy {
    private final Random random = new Random();
    private final PawnCalculator pawnCalculator = new PawnCalculator();

    @Override
    public Position chooseNextPosition(Grid grid, Player player) {

        List<Position> validMoves = pawnCalculator.calculateValidBlow(grid, player);
        if (validMoves.isEmpty()) return null;

        return validMoves.get(random.nextInt(validMoves.size()));
    }
}
