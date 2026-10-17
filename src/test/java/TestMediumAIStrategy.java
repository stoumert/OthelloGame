import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.AI.MediumAIStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMediumAIStrategy {

    private MediumAIStrategy mediumAI;
    private Player blackPlayer;
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        blackPlayer = new HumanPlayer(Pawn.BLACK);
        mediumAI = new MediumAIStrategy();
    }

    @Test
    void testChooseNextPosition() {
        Position move = mediumAI.chooseNextPosition(grid, blackPlayer);

        assertNotNull(move);
        assertTrue(move.x() >= 0 && move.x() < 8);
        assertTrue(move.y() >= 0 && move.y() < 8);
    }

    @Test
    void testChooseNextPositionMaximizesFlips() {
        Grid testGrid = new Grid();

        testGrid.putPawnAtPosition(new Position(3, 1), Pawn.EMPTY);
        testGrid.putPawnAtPosition(new Position(3, 2), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(3, 3), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(3, 4), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(3, 5), Pawn.BLACK);

        testGrid.putPawnAtPosition(new Position(4, 2), Pawn.EMPTY);
        testGrid.putPawnAtPosition(new Position(4, 3), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(4, 4), Pawn.BLACK);

        Position result = mediumAI.chooseNextPosition(testGrid, blackPlayer);

        assertNotNull(result);
        assertEquals(new Position(3, 1), result);
    }
}