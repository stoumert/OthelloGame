package fr.univ_amu.m1info.board_game_library.model.service;

import fr.univ_amu.m1info.board_game_library.model.*;

import java.util.List;
/**
 * Implémente la logique du jeu d'Othello en déléguant les calculs des pions à {@link PawnCalculator}.
*/
public class OthelloGame implements Othello {

    private final PawnCalculator calculator;

    public OthelloGame() {
        this.calculator = new PawnCalculator();
    }

    @Override
    public List<Position> calculateValidMoves(Grid grid, Player player) {
        return calculator.calculateValidBlow(grid, player);
    }

    @Override
    public List<Position> calculatePawnsToFlip(Grid grid, Position position, Player player) {
        return calculator.calculatePawnToFlip(position, player, grid);
    }

    @Override
    public boolean isGameOver(Grid grid, Player player1, Player player2) {
        return !isValidMove(grid, player1) &&
                !isValidMove(grid, player2);
    }

    @Override
    public Player determineWinner(Grid grid, Player player1, Player player2) {
        int count1 = calculator.countPawn(grid, player1);
        int count2 = calculator.countPawn(grid, player2);

        if (count1 > count2) return player1;
        else if (count2 > count1) return player2;
        else return null;
    }

    @Override
    public int getScore(Grid grid, Player winner) {
        return calculator.countPawn(grid, winner);
    }

    private boolean isValidMove(Grid grid, Player player) {
        List<Position> validMoves = calculateValidMoves(grid, player);
        return !validMoves.isEmpty();
    }

}
