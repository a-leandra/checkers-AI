package sztuczna.inteligencja.checkers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DraughtsmanMoveTest {

    @Test
    public void findOptions_foundNone() {
        Game game = new Game(5);
        Draughtsman d1 = new Draughtsman(0,0,1,5);
        d1.setQueen(true);
        Draughtsman d2 = new Draughtsman(1,1,1,5);
        Draughtsman d3 = new Draughtsman(3,1,2,5);

        game.setDraughtsmen(List.of(d1,d2,d3));
        game.getBoard().setFields(game.getDraughtsmen());
        d1.checkOptions(game.getBoard());
        assertEquals(Collections.emptyList(), d1.getOptionsX());
        assertEquals(Collections.emptyList(), d1.getOptionsY());
    }

    @Test
    public void findOptions() {
        Game game = new Game(5);
        Draughtsman d1 = new Draughtsman(4,0,1,5);
        Draughtsman d2 = new Draughtsman(3,1,1,5);
        Draughtsman d3 = new Draughtsman(0,2,2,5);
        Draughtsman d4 = new Draughtsman(1,3,2,5);
        Draughtsman d5 = new Draughtsman(4,4,1,5);
        d5.setQueen(true);
        game.setDraughtsmen(List.of(d1,d2,d3,d4,d5));
        game.getBoard().setFields(game.getDraughtsmen());
        d5.checkOptions(game.getBoard());
        //d5.move(0,0, game);
        game.moveDraughtsman(d5.getX(), d5.getY(),0,0);
        game.printBoard();
        //assertEquals(Collections.emptyList(), d1.getOptionsX());
    }
}
