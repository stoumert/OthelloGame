import fr.univ_amu.m1info.board_game_library.model.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPawn {
    private Pawn pawn;
    @BeforeEach
    void setUp() {
        this.pawn= Pawn.BLACK;
    }
    @Test
    void testPawn() {
        Pawn newPawn = pawn.switchPawn();
        assertEquals(Pawn.WHITE, newPawn);
    }
}
