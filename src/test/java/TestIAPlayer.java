import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.IAPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.AI.AIStrategy;
import fr.univ_amu.m1info.board_game_library.model.service.AI.EasyAIStrategy;
import fr.univ_amu.m1info.board_game_library.model.service.AI.MediumAIStrategy;
import fr.univ_amu.m1info.board_game_library.model.service.PawnCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIAPlayer {
    IAPlayer player;
    AIStrategy strategy;
    Grid grid;
    PawnCalculator pawnCalculator;
    @BeforeEach
    public void setUp() {
        strategy = new EasyAIStrategy();
        player = new IAPlayer(Pawn.BLACK, strategy);
        grid = new Grid();
        pawnCalculator = new PawnCalculator();
    }
    @Test
    public void testAutomated(){
        assertTrue(player.isAutomated());
    }
    @Test
    public void testEasyStrategy(){
        Position position = player.choosePosition(grid);
        assertTrue(pawnCalculator.calculateValidBlow(grid, player).contains(position));
    }
    @Test
    public void testMediumStrategy(){
        this.strategy = new MediumAIStrategy();
        Position position = player.choosePosition(grid);
        assertTrue(pawnCalculator.calculateValidBlow(grid, player).contains(position));
    }

}
