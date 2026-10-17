import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.GameHistory;
import fr.univ_amu.m1info.board_game_library.model.service.HistoryState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGameHistory {
    private GameHistory history;
    private HistoryState state1;
    private HistoryState state2;



    private Pawn[][] createEmptyGrid() {
        Pawn[][] grid = new Pawn[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = Pawn.EMPTY;
            }
        }
        return grid;
    }

    private Player createPlayerBlack() {
        return new HumanPlayer(Pawn.BLACK);
    }

    private Player createPlayerWhite() {
        return new HumanPlayer(Pawn.WHITE);
    }

    @BeforeEach
    void setUp() {
        history = new GameHistory();

        Pawn[][] grid1 = createEmptyGrid();
        state1 = new HistoryState(grid1, createPlayerBlack());

        Pawn[][] grid2 = createEmptyGrid();
        state2 = new HistoryState(grid2, createPlayerWhite());
    }

    @Test
    void testPush() {
        history.push(state1);
        assertTrue(history.canUndo());
    }

    @Test
    void testUndo() {
        history.push(state1);
        HistoryState result = history.undo(state2);

        assertEquals(state1, result);
        assertTrue(history.canRedo());
    }

    @Test
    void testRedo() {
        history.push(state1);
        history.undo(state2);

        HistoryState result = history.redo(state1);

        assertEquals(state2, result);
    }

    @Test
    void testCanUndo() {
        assertFalse(history.canUndo());

        history.push(state1);
        assertTrue(history.canUndo());
    }

    @Test
    void testCanRedo() {
        assertFalse(history.canRedo());

        history.push(state1);
        history.undo(state2);
        assertTrue(history.canRedo());
    }

    @Test
    void testClearAll() {
        history.push(state1);
        history.push(state2);

        history.clearAll();

        assertFalse(history.canUndo());
        assertFalse(history.canRedo());
    }


}