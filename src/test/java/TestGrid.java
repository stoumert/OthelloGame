import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGrid {
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    @Test
    void getColumnAndRow() {
        assertEquals(8,grid.getColumns());
        assertEquals(8,grid.getRows());
    }
    @Test
    void getPawnsAtPosition() {
        assertEquals(Pawn.EMPTY,grid.getPawnAtPosition(new Position(0, 0)));


        assertEquals(Pawn.BLACK,grid.getPawnAtPosition(new Position(3,4)));
        assertEquals(Pawn.BLACK,grid.getPawnAtPosition(new Position(4,3)));

        assertEquals(Pawn.WHITE,grid.getPawnAtPosition(new Position(3,3)));
        assertEquals(Pawn.WHITE,grid.getPawnAtPosition(new Position(4,4)));
    }

    @Test
    void putPawnAtPosition() {
        Position position = new Position(0, 0);
        grid.putPawnAtPosition(position, Pawn.BLACK);
        assertNotEquals(Pawn.EMPTY,grid.getPawnAtPosition(position));
        assertEquals(Pawn.BLACK,grid.getPawnAtPosition(position));

    }

    @Test
    void testGetMatrix() {
        Pawn[][] matrix = grid.getMatrix();

        assertNotNull(matrix);
        assertEquals(8, matrix.length);
        assertEquals(8, matrix[0].length);
        assertEquals(Pawn.WHITE, matrix[3][3]);
        assertEquals(Pawn.BLACK, matrix[3][4]);
    }

    @Test
    void testRestore() {
        Pawn[][] newGrid = new Pawn[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newGrid[i][j] = Pawn.EMPTY;
            }
        }
        newGrid[0][0] = Pawn.BLACK;
        newGrid[7][7] = Pawn.WHITE;

        grid.restore(newGrid);

        assertEquals(Pawn.BLACK, grid.getPawnAtPosition(new Position(0, 0)));
        assertEquals(Pawn.WHITE, grid.getPawnAtPosition(new Position(7, 7)));
        assertEquals(Pawn.EMPTY, grid.getPawnAtPosition(new Position(3, 3)));
    }

    @Test
    void testCopy() {
        grid.putPawnAtPosition(new Position(0, 0), Pawn.BLACK);
        grid.putPawnAtPosition(new Position(5, 5), Pawn.WHITE);

        Grid copiedGrid = grid.copy();

        assertEquals(Pawn.BLACK, copiedGrid.getPawnAtPosition(new Position(0, 0)));
        assertEquals(Pawn.WHITE, copiedGrid.getPawnAtPosition(new Position(5, 5)));
        assertEquals(Pawn.WHITE, copiedGrid.getPawnAtPosition(new Position(3, 3)));

        copiedGrid.putPawnAtPosition(new Position(0, 0), Pawn.WHITE);
        assertEquals(Pawn.BLACK, grid.getPawnAtPosition(new Position(0, 0)));
    }
}
