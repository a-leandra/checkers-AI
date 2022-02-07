package sztuczna.inteligencja.checkers.gametree;

import lombok.Getter;
import lombok.Setter;
import sztuczna.inteligencja.checkers.Draughtsman;
import sztuczna.inteligencja.checkers.training.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class GameTreeNode {
    List<GameTreeNode> children = new ArrayList<>();
    GameTreeNode parent;
    private int value;
    private boolean chosen = false;

    List<Draughtsman> draughtsmen = new ArrayList<>();
    private int xPrev;
    private int yPrev;
    private int xCurrent;
    private int yCurrent;
    private int playersTurn;

    public GameTreeNode(GameTreeNode parent, List<Draughtsman> draughtsmen) {
        this.parent = parent;
        for (Draughtsman d :
                draughtsmen) {
            this.draughtsmen.add(new Draughtsman(d));
        }
    }


    public void addChild(GameTreeNode node) {
        children.add(node);
    }

    public void addValue(Boolean max) {  // dodaje wartość na podstawie wartośi dzieci
        int tempVal;
        GameTreeNode currentNode = null;
        if (max) {
            tempVal = -10000;
            for (GameTreeNode child : children) {
                if (child.value > tempVal) {
                    currentNode = child;
                    tempVal = child.value;
                }
            }
        } else {
            tempVal = 10000;
            for (GameTreeNode child : children) {
                if (child.value < tempVal) {
                    tempVal = child.value;
                    currentNode = child;
                }
            }
        }
        this.value = tempVal;
        currentNode.chosen = true;  // oznaczenie węzła jako kawałek naszej drogi
    }

    //alpha = -10000
    //beta = 10000
    public void alphaBeta(Boolean max, int alpha, int beta) {
        int tempVal;
        GameTreeNode currentNode = null;
        if (max) {
            tempVal = -10000;
            for (GameTreeNode child : children) {
                if (child.value > tempVal) {
                    currentNode = child;
                    tempVal = child.value;
                }
                if (child.value > alpha)
                    alpha = child.value;

                if (alpha >= beta)
                    break;
            }
        } else {
            tempVal = 10000;
            for (GameTreeNode child : children) {
                if (child.value < tempVal) {
                    tempVal = child.value;
                    currentNode = child;
                }
                if (child.value < beta)
                    beta = child.value;

                if (beta <= alpha)
                    break;
            }
        }
        this.value = tempVal;
        currentNode.chosen = true;
    }

    public void countValue() {      // Oblicza wartości dla najniższego poziomu dla gracza 1 (liczy się też to czy przeciwnik jest daleko
        int val = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.getPlayer() != 2) {
                if (d.isQueen() && !d.isBeaten()) {
                    val += 30; //pkt za damkę
                } else {
                    val += 1 + d.getY();    // na podstawie odleglosci od bazy
                }
            }
        }
        val += 100*countBeatenDraughtsmen();
        this.value = val;
    }


    public void countValueByQueenAndBeating() {      // Funkcja nie uwzględnia odległości od bazy
        int val = 0;
        int counter = 0;
        int size = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.getPlayer() != 2) {
                size = d.getBoardSize();
                counter++;
                if (!d.isBeaten() && d.isQueen()) {
                    val +=30;   // za damki
                }
            }
        }
        val -= 100*((size + 1)/2 - counter * 5); // dodatkowa kara za zbite pionki w rozgrywce
        val += 100*countBeatenDraughtsmen();
        this.value = val;
    }


    private int countBeatenDraughtsmen() { // liczy czy jakieś pionki zostały zbite
        int player1 = 0;
        int player2 = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.getPlayer() == 1 && !d.isBeaten()) {
                player1++;
            }
            if (d.getPlayer() == 2 && !d.isBeaten()) {
                player2++;
            }
        }

        for (Draughtsman d : getParent().draughtsmen) {
            if (d.getPlayer() == 1 && !d.isBeaten()) {
                player1--;
            }
            if (d.getPlayer() == 2 && !d.isBeaten()) {
                player2--;
            }
        }

        int val = 6 * player2 - 5 * player1;
        return val;
    }

    public void training() {
        int val = NeuralNetwork.Create(draughtsmen);
        this.value = val;
    }

}