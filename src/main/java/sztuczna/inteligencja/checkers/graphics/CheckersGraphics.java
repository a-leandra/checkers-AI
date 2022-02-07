package sztuczna.inteligencja.checkers.graphics;

import sztuczna.inteligencja.checkers.Game;
import java.awt.*;

public class CheckersGraphics {
    Game game;
    int size;
    public CheckersGraphics(int size, Game game) {
        this.size = size;
        this.game = game;
    }
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow(size, game);
            }
        });
    }


}
