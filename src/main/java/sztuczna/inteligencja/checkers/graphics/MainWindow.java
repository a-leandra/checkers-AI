package sztuczna.inteligencja.checkers.graphics;
import sztuczna.inteligencja.checkers.Game;

import javax.swing.*;

public class MainWindow extends JFrame {

    public static BoardPanel panel;

    public MainWindow(int size, Game game) {
        super("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        panel = new BoardPanel(size, game);
        add(panel);
        setBounds(100,100,512,535);
    }

    public static void refresh() {
        panel.refresh();
        try {
        Thread.sleep(0);}
        catch (Exception e) {}
    }

    public static boolean chosen() {
        return panel.chosen;
    }

}

