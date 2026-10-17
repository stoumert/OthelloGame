package fr.univ_amu.m1info.board_game_library.model.service.AI;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.service.PawnCalculator;

import java.util.List;

public class MediumAIStrategy implements AIStrategy {
    private final PawnCalculator pawnCalculator = new PawnCalculator();

    @Override
    public Position chooseNextPosition(Grid grid, Player player) {

        List<Position> validMoves = pawnCalculator.calculateValidBlow(grid, player);
        if (validMoves.isEmpty()) return null;

        Position bestMove = null;
        int maxFlipped = -1;

        for (Position move : validMoves) {
            List<Position> flipped = pawnCalculator.calculatePawnToFlip(move, player, grid);
            if (flipped.size() > maxFlipped) {
                maxFlipped = flipped.size();
                bestMove = move;
            }
        }

        return bestMove;
    }
}
