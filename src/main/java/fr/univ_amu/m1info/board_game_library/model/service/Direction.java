package fr.univ_amu.m1info.board_game_library.model.service;

/**
 * Représente les directions possibles dans une grille bidimensionnelle, chaque direction étant définie
 * par ses décalages de mouvement horizontal (x) et vertical (y). Ces directions peuvent être utilisées
 * dans les jeux de plateau basés sur une grille ou toute application nécessitant un mouvement directionnel.
 */
public enum Direction {
    NORTH(0, 1),
    EAST(1,0),
    SOUTH(0,-1),
    WEST(-1,0),
    NORTH_EAST(1,1),
    NORTH_WEST(-1,1),
    SOUTH_EAST(1,-1),
    SOUTH_WEST(-1,-1);
    private final int x;
    private final int y;
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
}
