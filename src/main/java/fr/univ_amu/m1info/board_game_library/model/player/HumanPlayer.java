package fr.univ_amu.m1info.board_game_library.model.player;

import fr.univ_amu.m1info.board_game_library.model.*;

public class HumanPlayer extends Player {

    public HumanPlayer(Pawn pawn) {
        super(pawn);
    }

    @Override
    public boolean isAutomated() {
        return false;
    }
}
