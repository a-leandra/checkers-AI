package sztuczna.inteligencja.checkers.andOrTree;

import lombok.Getter;
import lombok.Setter;
import sztuczna.inteligencja.checkers.Draughtsman;
import sztuczna.inteligencja.checkers.Game;


import static java.lang.System.gc;

@Getter
@Setter
public class AndOrGameTree {
    int levels=8;
    AndOrGameTreeNode parentNode;
    int algorithm = 0;
    int grading = 0;

    public AndOrGameTree(Game game) {
        Game g = new Game(game);
        levels = 11-g.getSize();
        this.parentNode = new AndOrGameTreeNode(null, g.getDraughtsmen());
        parentNode.setPlayersTurn(game.getPlayersTurn());
        createTree(game, 1, parentNode, levels, 0);
        markNodes(parentNode, true);
    }

    private void createTree(Game game, int playersTurn, AndOrGameTreeNode parent, int levels, int i) {
        levels--;

        for (Draughtsman d : game.getDraughtsmen()) {
            if (d.getPlayer() == game.getPlayersTurn() && !d.isBeaten()) {
                d.checkOptions(game.getBoard());
                for (int j = 0; j < d.getOptionsX().size(); j++) {
                    Game g = new Game(game);
                    int turn = parent.getPlayersTurn() == 1 ? 2 : 1;
                    g.moveDraughtsman(d.getX(), d.getY(), d.getOptionsX().get(j).intValue(), d.getOptionsY().get(j).intValue());
                    AndOrGameTreeNode node = new AndOrGameTreeNode(parent, g.getDraughtsmen());
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
        AndOrGameTreeNode parent = new AndOrGameTreeNode(null, game.getDraughtsmen());
        setParentNode(parent);
        createTree(game, game.getPlayersTurn(), parent, levels, 0);
        markNodes(parent, playerWhite);
        if (!parentNode.getChildren().isEmpty()) {
            parentNode.isItWinNode(!playerWhite); // dodanie wartosci do korzenia
        }
        gc();
    }

    private void setParentNode(AndOrGameTreeNode node) {
        this.parentNode = node;
    }


    private void markNodes(AndOrGameTreeNode node, boolean or) {
        for (AndOrGameTreeNode gameTreeNode : node.getChildren()) {

            if (gameTreeNode.getChildren().isEmpty()) { // liść

                if(grading == 0){
                    gameTreeNode.countValue(node);
                    //gameTreeNode.countValueByQueenAndBeating(node);
                }
                else{
                    gameTreeNode.training();
                }

            } else {
                markNodes(gameTreeNode, !or);
                gameTreeNode.isItWinNode(or);
            }
        }
    }

    public void printTree(AndOrGameTreeNode node, int index) {
        int i = 0;
        if (index < 10) {
            for (AndOrGameTreeNode g : node.children) {
                i++;
                for (int j = 0; j <= index; j++) {
                    System.out.print("-");
                }
                System.out.println(" win = " + g.isWin());
                printTree(g, index + 1);
            }
        }
    }
    public void makeNextMove(Game game) {
        int b = 0;
        double maxV = -1000  ;
        AndOrGameTreeNode chosenNode = parentNode.children.get(0) ;
        AndOrGameTreeNode chosenWinNode = parentNode.children.get(0) ;
        for (AndOrGameTreeNode n : parentNode.children) {
            if (n.isWin()) {
                if(n.getValue()>maxV){
                    chosenWinNode = n;
                    maxV = n.getValue();
                }
                b++;
            }
        }
        if(b ==0 ){
            if(!parentNode.children.isEmpty()){
                for(AndOrGameTreeNode n : parentNode.children){
                    if(n.getValue()>chosenNode.getValue()){
                        chosenNode = n;
                    }
                }
                game.makeMoveFromNode(chosenNode);  // pierwszy ruch z rzędu
                game.printBoard();
            }
        }
        else{
            game.makeMoveFromNode(chosenWinNode);
            game.printBoard();
        }
    }

}
