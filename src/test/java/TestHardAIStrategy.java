import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.AI.HardAIStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHardAIStrategy {


    private HardAIStrategy hardAI;
    private Player blackPlayer;
    private Player whitePlayer;
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        blackPlayer = new HumanPlayer(Pawn.BLACK);
        whitePlayer = new HumanPlayer(Pawn.WHITE);
        hardAI = new HardAIStrategy();
        hardAI.setOpponent(whitePlayer);
    }

    @Test
    void testSetOpponent() {
        HardAIStrategy ai = new HardAIStrategy();
        Player opponent = new HumanPlayer(Pawn.WHITE);

        ai.setOpponent(opponent);

        assertNotNull(ai);
    }

    @Test
    void testChooseNextPosition() {
        Position result = hardAI.chooseNextPosition(grid, blackPlayer);

        assertNotNull(result);
        assertTrue(result.x() >= 0 && result.x() < 8);
        assertTrue(result.y() >= 0 && result.y() < 8);
    }

    @Test
    void testChooseNextPositionPrefersCorners() {
        Grid testGrid = new Grid();

        testGrid.putPawnAtPosition(new Position(0, 0), Pawn.EMPTY);
        testGrid.putPawnAtPosition(new Position(0, 1), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(0, 2), Pawn.BLACK);

        testGrid.putPawnAtPosition(new Position(3, 3), Pawn.BLACK);
        testGrid.putPawnAtPosition(new Position(3, 4), Pawn.WHITE);
        testGrid.putPawnAtPosition(new Position(3, 5), Pawn.EMPTY);

        hardAI.setOpponent(whitePlayer);
        Position result = hardAI.chooseNextPosition(testGrid, blackPlayer);

        assertNotNull(result);
        assertEquals(new Position(0, 0), result);
    }
}
