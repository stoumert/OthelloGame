package fr.univ_amu.m1info.board_game_library.model;

/**
 * Représente les trois états possibles d'un pion dans une partie.
 * - BLACK: Indique que le pion appartient au joueur ayant choisi la couleur noire
 * - WHITE: Indique que le pion appartient au joueur ayant choisi la couleur blanche
 * - EMPTY: Indique une case vide sur le plateau
 */
public enum Pawn {
    BLACK, WHITE, EMPTY;

    /**
     * Bascule le pion entre BLACK et WHITE.
     * Si le pion actuel est BLACK, retourne WHITE.
     * Si le pion actuel est WHITE, retourne BLACK.
     *
     * @return Le pion basculé (BLACK ou WHITE).
     */

    public Pawn switchPawn() {
        return switch(this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            default -> EMPTY;
        };
    }

}
