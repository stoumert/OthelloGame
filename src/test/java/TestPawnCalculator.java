import fr.univ_amu.m1info.board_game_library.model.Grid;
import fr.univ_amu.m1info.board_game_library.model.Pawn;
import fr.univ_amu.m1info.board_game_library.model.Player;
import fr.univ_amu.m1info.board_game_library.model.Position;
import fr.univ_amu.m1info.board_game_library.model.player.HumanPlayer;
import fr.univ_amu.m1info.board_game_library.model.service.PawnCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPawnCalculator {
    private Grid grid;
    private Player player1;
    private Player player2;
    private PawnCalculator pawnCalculator;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        player1 = new HumanPlayer(Pawn.BLACK);
        player2 = new HumanPlayer(Pawn.WHITE);
        pawnCalculator = new PawnCalculator();
    }

    @Test
    void testCalculatePawnToFlip(){
        Position position = new Position(3, 2);
        List<Position> pawnToFlip = pawnCalculator.calculatePawnToFlip(position, player1,grid);
        assertEquals(1, pawnToFlip.size());

        position = new Position(2, 3);
        pawnToFlip = pawnCalculator.calculatePawnToFlip(position, player1,grid);
        System.out.println(pawnToFlip);
        assertEquals(1, pawnToFlip.size());

        position = new Position(2, 2);
        pawnToFlip = pawnCalculator.calculatePawnToFlip(position, player1,grid);
        System.out.println(pawnToFlip);
        assertTrue(pawnToFlip.isEmpty());
    }

    @Test
    void testCountPawn(){
        int count = pawnCalculator.countPawn(grid, player1);
        assertEquals(2, count);
    }

    @Test
    void testCalculateValidBlow(){
        List<Position> validBlow = pawnCalculator.calculateValidBlow(grid, player1);
        assertEquals(4,validBlow.size());
    }
    @Test
    void play(){
        grid.putPawnAtPosition(new Position(3,2),Pawn.BLACK);
        grid.putPawnAtPosition(new Position(3,3),Pawn.BLACK);

        List<Position> validBlow = pawnCalculator.calculateValidBlow(grid, player2);
        assertEquals(3,validBlow.size());

    }
}
