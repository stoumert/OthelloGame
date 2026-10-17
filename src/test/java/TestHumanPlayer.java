import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHumanPlayer {
    private HumanPlayer humanPlayer;
    @BeforeEach
    void setUp() {
        humanPlayer = new HumanPlayer(Pawn.BLACK);
    }

    @Test
    void getPawn(){
        assertEquals(Pawn.BLACK, humanPlayer.getPawn());
    }
}
