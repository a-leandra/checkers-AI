package sztuczna.inteligencja.checkers.gametree;

import lombok.Getter;
import lombok.Setter;

import sztuczna.inteligencja.checkers.Draughtsman;
import sztuczna.inteligencja.checkers.Game;


import static java.lang.System.gc;

@Getter
@Setter
public class GameTree {
    GameTreeNode parentNode;
    int levels=8;
    int algorithm = 0;
    int grading = 0;

    public GameTree(Game game) {
        Game g = new Game(game);
        this.parentNode = new GameTreeNode(null, g.getDraughtsmen());
        parentNode.setPlayersTurn(game.getPlayersTurn());
        levels = 11-g.getSize();
        createTree(game, 1, parentNode, levels, 0);
        addTreeValues(parentNode, true);
    }

    private void createTree(Game game, int playersTurn, GameTreeNode parent, int levels, int i) {
        levels--;

        for (Draughtsman d : game.getDraughtsmen()) {
            if (d.getPlayer() == game.getPlayersTurn() && !d.isBeaten()) {
                d.checkOptions(game.getBoard());
                for (int j = 0; j < d.getOptionsX().size(); j++) {
                    Game g = new Game(game);
                    int turn = parent.getPlayersTurn() == 1 ? 2:1;
                    g.moveDraughtsman(d.getX(), d.getY(), d.getOptionsX().get(j).intValue(), d.getOptionsY().get(j).intValue());
                    GameTreeNode node = new GameTreeNode(parent, g.getDraughtsmen());
                    i++;
                    parent.addChild(node);

                    if (levels == 0)
                        return;
                    createTree(g, turn, node, levels, i);
                }
            }
        }
    }

    public void addLevel(Game game, boolean playerWhite) {
        GameTreeNode parent = new GameTreeNode(null, game.getDraughtsmen());
        setParentNode(parent);
        createTree(game, game.getPlayersTurn(), parent, levels, 0);
        addTreeValues(parent, playerWhite);
        if (parentNode.getChildren().isEmpty()){
            game.setGameOver(true);
        }
        else {
            if (algorithm == 0) {
                parentNode.addValue(!playerWhite); // dodanie wartosci do korzenia
            } else {
                parentNode.alphaBeta(true, -10000, 10000);
            }
        }
        gc();
    }

    private void setParentNode(GameTreeNode node) {
        this.parentNode = node;
    }


    private void addTreeValues(GameTreeNode node, boolean max) {
        for (GameTreeNode gameTreeNode : node.getChildren()) {

            if (gameTreeNode.getChildren().isEmpty()) { // liść

                if(grading == 1) {
                    gameTreeNode.training();
                }
                else {
                    //gameTreeNode.countValue();
                    gameTreeNode.countValueByQueenAndBeating();
                }


            } else {
                gameTreeNode.addValue(max);
                if (algorithm == 0) {
                    gameTreeNode.addValue(max);
                } else {
                    gameTreeNode.alphaBeta(max, -10000, 10000);
                }
            }
        }
    }

    public void printTree(GameTreeNode node, int index) {
        int i = 0;
        if (index < 10) {
            for (GameTreeNode g : node.children) {
                i++;
                for (int j = 0; j <= index; j++) {
                    System.out.print("-");
                }
                System.out.println(" value " + i + " " + g.getValue() + " " + g.isChosen());
                printTree(g, index + 1);
            }
        }
    }

    public void makeNextMove(Game game){
        for (GameTreeNode n : parentNode.children) {
            if(n.isChosen()){
                game.makeMoveFromNode(n);
                game.printBoard();
            }
        }
    }
}
