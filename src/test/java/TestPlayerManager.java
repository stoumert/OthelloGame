import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.PlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPlayerManager {
    private PlayerManager playerManager;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        player1 = new HumanPlayer(Pawn.BLACK);
        player2 = new HumanPlayer(Pawn.WHITE);
        playerManager = new PlayerManager(player1, player2);
    }

    @Test
    void testGetPlayer1() {
        assertEquals(player1, playerManager.getPlayer1());
    }

    @Test
    void testGetPlayer2() {
        assertEquals(player2, playerManager.getPlayer2());
    }

    @Test
    void testGetCurrentPlayer() {
        assertEquals(player1, playerManager.getCurrentPlayer());
    }

    @Test
    void testSwitchPlayer() {
        playerManager.switchPlayer();
        assertEquals(player2, playerManager.getCurrentPlayer());

        playerManager.switchPlayer();
        assertEquals(player1, playerManager.getCurrentPlayer());
    }

    @Test
    void testResetPlayer() {
        playerManager.switchPlayer();
        playerManager.resetPlayer();

        assertEquals(player1, playerManager.getCurrentPlayer());
    }

    @Test
    void testSetCurrentPlayer() {
        playerManager.setCurrentPlayer(player2);
        assertEquals(player2, playerManager.getCurrentPlayer());
    }
}