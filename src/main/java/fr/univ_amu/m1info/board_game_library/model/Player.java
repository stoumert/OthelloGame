package fr.univ_amu.m1info.board_game_library.model;

import java.io.Serializable;

public abstract class Player implements Serializable {
    protected final Pawn pawn;
    public Player(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return pawn;
    }
    public abstract boolean isAutomated();
}
