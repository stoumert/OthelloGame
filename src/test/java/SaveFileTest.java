

import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.GameManager;
import fr.univ_amu.m1info.board_game_library.model.service.GameState;
import fr.univ_amu.m1info.board_game_library.model.service.PlayerManager;
import fr.univ_amu.m1info.board_game_library.model.service.SaveFile;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SaveFileTest {

    private GameManager manager;
    private PlayerManager playerManager;
    private static final String FILENAME = "sauvegarde_test.dat";
    private GameState loaded;

    @BeforeEach
    void setup() {
        Player player1 = new HumanPlayer(Pawn.BLACK);
        Player player2 = new HumanPlayer(Pawn.WHITE);
        playerManager=new PlayerManager(player1,player2);
        manager = new GameManager(player1, player2);
    }

    void coup(){
        manager.playHumanMove(new Position(2, 3));
        manager.playHumanMove(new Position(4, 5));

    }

    void save(){
        SaveFile.saveGameToFile(manager, FILENAME);
        File file = new File(FILENAME);
        assertTrue(file.exists());


    }
    void load(){
        loaded = SaveFile.loadGameFromFile(FILENAME);
        assertNotNull(loaded);
    }

    @Test
    void testSaveAndLoadGame() {


        coup();
        save();
        load();



        assertEquals(playerManager.getPlayer1().getPawn(), loaded.player1().getPawn());
        assertEquals(playerManager.getPlayer2().getPawn(), loaded.player2().getPawn());

        assertEquals(
                manager.getCurrentPlayer().getPawn(),
                loaded.currentPlayer().getPawn()
        );

        Grid restoredGrid = loaded.grid();

        assertNotNull(restoredGrid);
        assertEquals(
                manager.getGrid(),
                restoredGrid
        );


    }
}

