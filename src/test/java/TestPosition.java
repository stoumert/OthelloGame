import fr.univ_amu.m1info.board_game_library.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPosition {
    private Position position;

    @BeforeEach
    void setUp() {
        this.position = new Position(0, 0);
    }
    @Test
    public void testGetPosition() {
        assertEquals(0, position.x());
        assertEquals(0, position.y());
        this.position = new Position(1, 0);
        assertEquals(1, position.x());
        assertNotEquals(1, position.y());
    }
}
