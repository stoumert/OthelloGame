package fr.univ_amu.m1info.board_game_library.model.player;

import fr.univ_amu.m1info.board_game_library.model.*;
import fr.univ_amu.m1info.board_game_library.model.service.AI.AIStrategy;

public class IAPlayer extends Player{
    private final AIStrategy strategy;

    public IAPlayer(Pawn pawn, AIStrategy strategy) {
        super(pawn);
        this.strategy = strategy;
    }
    public Position choosePosition(Grid grid) {
        return strategy.chooseNextPosition(grid, this);
    }

    @Override
    public boolean isAutomated() {
        return true;
    }
}
