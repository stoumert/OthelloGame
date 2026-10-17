package fr.univ_amu.m1info.board_game_library.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Représente le plateau de jeu pour un jeu de plateau basé sur une grille. La grille consiste en un nombre fixe
 * de lignes et de colonnes et est initialisée avec des placements spécifiques de pions au début.
 */
public class Grid implements Serializable {
    private final int rows;
    private final int columns;
    private Pawn[][] grid;
    public Grid() {
        this.rows = 8;
        this.columns = 8;
        initializeGrid();
    }
    public void initializeGrid() {
        initializeEmptyGrid();
        initializePawnOnGrid();
    }



    /**
     * Initialise la grille en définissant toutes les positions à l'état EMPTY.
     */

    private void initializeEmptyGrid(){
        grid = new Pawn[rows][columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                grid[i][j] = Pawn.EMPTY;
            }
        }
    }

    /**
     * Initialise les positions de départ des pions sur la grille.
     * Cette méthode définit la configuration initiale de la grille en plaçant deux pions WHITE
     * et deux pions BLACK au centre de la grille en préparation du début de partie.
     * - WHITE aux positions (3,3) et (4,4)
     * - BLACK aux positions (3,4) et (4,3)
     */

    private void initializePawnOnGrid() {
        grid[3][3] = Pawn.WHITE;
        grid[3][4] = Pawn.BLACK;
        grid[4][3] = Pawn.BLACK;
        grid[4][4] = Pawn.WHITE;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }

    /**
     * Récupère le pion situé à la position spécifiée sur la grille.
     *
     * @param position La position sur la grille pour laquelle le pion doit être récupéré.
     * @return Le pion situé à la position spécifiée, qui peut être BLACK, WHITE, ou EMPTY.
     */

    public Pawn getPawnAtPosition(Position position) {
        return grid[position.x()][position.y()];
    }

    /**
     * Place un pion à la position spécifiée sur la grille.
     *
     * @param position La position sur la grille où le pion doit être placé.
     * @param pawn Le pion à placer à la position spécifiée. Il peut être BLACK, WHITE, ou EMPTY.
     */
    public void putPawnAtPosition(Position position, Pawn pawn) {
        grid[position.x()][position.y()] = pawn;
    }

    /**
     * Vérifie si une position donnée est en dehors des limites de la grille.
     *
     * @param position La position à vérifier sur la grille.
     * @return true si la position est en dehors des limites de la grille, false sinon.
     */
    public boolean isWithinBounds(Position position) {
        return position.x() >= 0 && position.x() < columns && position.y() >= 0 && position.y() < rows;
    }
    /**
     * Retourne une copie profonde de la grille actuelle.
     * @return La copie de la grille.
     */
    public Pawn[][] getMatrix() {
        Pawn[][] copy = new Pawn[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, columns);
        }
        return copy;
    }
    /**
     * Restaure une grille à partir d'une grille sauvegardée
     * @param grid la grille sauvegarder
     */
    public void restore(Pawn[][] grid) {
        this.grid = grid;
    }


    /**
     * Cree une copie de la grille
     * @return la copie de la grille
     */

    public Grid copy() {
        Grid newGrid = new Grid();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newGrid.grid[i][j] = this.grid[i][j];
            }
        }
        return newGrid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid1 = (Grid) o;
        return Arrays.deepEquals(grid, grid1.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }
}

