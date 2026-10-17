package fr.univ_amu.m1info.board_game_library.model.service.AI;

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.service.PawnCalculator;

import java.util.List;

public class HardAIStrategy implements AIStrategy {

    private static final int MAX_DEPTH = 3;
    private final PawnCalculator calculator = new PawnCalculator();

    private Player opponent;

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public Position chooseNextPosition(Grid grid, Player me) {
        List<Position> moves = calculator.calculateValidBlow(grid, me);
        if (moves.isEmpty()) return null;

        int bestScore = Integer.MIN_VALUE;
        Position bestMove = null;

        for (Position move : moves) {
            Grid sim = grid.copy();
            simulateMove(sim, move, me);
            int score = minimax(sim, MAX_DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, me, opponent);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private void simulateMove(Grid grid, Position move, Player player) {
        grid.putPawnAtPosition(move, player.getPawn());
        List<Position> flipped = calculator.calculatePawnToFlip(move, player, grid);
        for (Position p : flipped) {
            grid.putPawnAtPosition(p, player.getPawn());
        }
    }

    private int minimax(Grid grid, int depth, boolean max, int alpha, int beta, Player me, Player opp) {
        if (depth == 0) return evaluate(grid, me, opp);

        Player current = max ? me : opp;
        List<Position> moves = calculator.calculateValidBlow(grid, current);

        if (moves.isEmpty()) {
            Player other = max ? opp : me;
            if (calculator.calculateValidBlow(grid, other).isEmpty()) {
                int myCount = calculator.countPawn(grid, me);
                int oppCount = calculator.countPawn(grid, opp);
                return (myCount > oppCount) ? 100000 : (myCount < oppCount) ? -100000 : 0;
            }
            return minimax(grid, depth - 1, !max, alpha, beta, me, opp);
        }

        if (max) {
            int best = Integer.MIN_VALUE;
            for (Position move : moves) {
                Grid sim = grid.copy();
                simulateMove(sim, move, me);
                int val = minimax(sim, depth - 1, false, alpha, beta, me, opp);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Position move : moves) {
                Grid sim = grid.copy();
                simulateMove(sim, move, opp);
                int val = minimax(sim, depth - 1, true, alpha, beta, me, opp);
                best = Math.min(best, val);
                beta = Math.min(beta, best);
                if (beta <= alpha) break;
            }
            return best;
        }
    }

    private int evaluate(Grid grid, Player me, Player opp) {
        int score = 0;
        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Position pos = new Position(i, j);
                int weight = getWeight(i, j, grid);

                if (grid.getPawnAtPosition(pos) == me.getPawn()) {
                    score += weight;
                } else if (grid.getPawnAtPosition(pos) == opp.getPawn()) {
                    score -= weight;
                }
            }
        }

        int myMoves = calculator.calculateValidBlow(grid, me).size();
        int oppMoves = calculator.calculateValidBlow(grid, opp).size();
        score += (myMoves - oppMoves) * 10;

        return score;
    }

    private int getWeight(int x, int y, Grid grid) {
        int rows = grid.getRows();
        int cols = grid.getColumns();

        boolean corner = (x == 0 || x == cols - 1) && (y == 0 || y == rows - 1);
        if (corner) return 100;

        boolean nearCorner = (x <= 1 || x >= cols - 2) && (y <= 1 || y >= rows - 2);
        if (nearCorner) return -50;

        boolean edge = x == 0 || x == cols - 1 || y == 0 || y == rows - 1;
        if (edge) return 50;

        return 0;
    }
}