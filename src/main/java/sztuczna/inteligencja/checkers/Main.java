package sztuczna.inteligencja.checkers;

import sztuczna.inteligencja.checkers.andOrTree.AndOrGameTree;
import sztuczna.inteligencja.checkers.gametree.GameTree;
import sztuczna.inteligencja.checkers.graphics.MainWindow;
import sztuczna.inteligencja.checkers.graphics.menu.MenuWindow;

import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        MenuWindow menu = new MenuWindow();
        int numGames=1;
        int wonGamesPlayer1 = 0;
        while (!menu.isFinished()) {
            System.out.println("");
        }

        Game game = new Game(menu.getGame());
        if (menu.getChosenTree() == 1) {
            GameTree tree = new GameTree(game);
            if (menu.getChosenAlg() == 0){
                tree.setAlgorithm(0);
            }
            else if (menu.getChosenAlg() == 1) {
                tree.setAlgorithm(1);
            }
            if (menu.getChosenGrading() == 1) {
                tree.setGrading(1);
            }
            game.printBoard();
            // Player 1 rusza się pierwszy - w liściach szukamy min,

            MainWindow mw = new MainWindow(menu.getGame(), game);

           // for (int i = 0; i < numGames; i++) {
                int turn = 1;
                boolean firstPlayerTurn = true;
               while (!game.gameOver()) {
                    if (menu.isFinished()) {

                        firstPlayerTurn = !firstPlayerTurn;
                        System.out.print("");
                        if (game.getPlayersTurn() == 1) {
                            tree.addLevel(game, true);
                            tree.makeNextMove(game);
                            mw.refresh();
                        }
//                        else {
//                            game.makeRandomMovePlayer2();
//                            mw.refresh();
//                        }
                    if (mw.chosen() && game.getPlayersTurn() == 2) {
                        game.moveDraughtsman(mw.panel.getChosenDraughtsman().x, mw.panel.getChosenDraughtsman().y, mw.panel.getChosenField().x, mw.panel.getChosenField().y);
                        mw.refresh();
                    }

                    }
                }
               if (game.getPlayerWon() == 1) {
                   wonGamesPlayer1++;
               }
            mw.dispatchEvent(new WindowEvent(mw, WindowEvent.WINDOW_CLOSING));
              // game.reset();
          //  }
        }
        else{
            AndOrGameTree tree = new AndOrGameTree(game);
            if (menu.getChosenAlg() == 0){
                tree.setAlgorithm(0);
            }
            else if (menu.getChosenAlg() == 1) {
                tree.setAlgorithm(1);
            }
            if (menu.getChosenGrading() == 1) {
                tree.setGrading(1);
            }
            game.printBoard();
            // Player 1 rusza się pierwszy - w liściach szukamy min,
            MainWindow mw = new MainWindow(menu.getGame(), game);


          //  for (int i = 0; i < numGames; i++) {

                int turn = 1;
                boolean firstPlayerTurn = true;
                while (!game.gameOver()) {
                    if (menu.isFinished()) {

                        firstPlayerTurn = !firstPlayerTurn;
                        System.out.print("");
                        if (game.getPlayersTurn() == 1) {
                            tree.addLevel(game, true);
                            tree.makeNextMove(game);
                            mw.refresh();
                        }
//                        else {
//                            game.makeRandomMovePlayer2();
//                            mw.refresh();
//                        }
                if (mw.chosen() && game.getPlayersTurn() == 2) {
                    game.moveDraughtsman(mw.panel.getChosenDraughtsman().x, mw.panel.getChosenDraughtsman().y, mw.panel.getChosenField().x, mw.panel.getChosenField().y);
                    mw.refresh();
                }

                    }
                }
                if (game.getPlayerWon() == 1) {
                    wonGamesPlayer1++;
                }
            mw.dispatchEvent(new WindowEvent(mw, WindowEvent.WINDOW_CLOSING));
               // game.reset();
          //  }
        }

            // Player 1 rusza się pierwszy - w liściach szukamy min,
            //MainWindow mw = new MainWindow(menu.getGame(), game);
        System.out.println("Komputer wygrał " + wonGamesPlayer1 + " razy");



//        long end = System.currentTimeMillis();
//        long elapsedTime = end - start;
//        System.out.println(elapsedTime + " milisec");

    }
}
