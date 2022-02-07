package sztuczna.inteligencja.checkers.andOrTree;

import lombok.Getter;
import lombok.Setter;
import sztuczna.inteligencja.checkers.Draughtsman;
import sztuczna.inteligencja.checkers.training.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AndOrGameTreeNode {

    List<AndOrGameTreeNode> children = new ArrayList<>();
    AndOrGameTreeNode parent;
    private double value = 0;
    private boolean win = false;
    private boolean chosen = false;

    List<Draughtsman> draughtsmen = new ArrayList<>();
    private int xPrev;
    private int yPrev;
    private int xCurrent;
    private int yCurrent;
    private int playersTurn;

    public AndOrGameTreeNode(AndOrGameTreeNode parent, List<Draughtsman> draughtsmen) {
        this.parent = parent;
        for (Draughtsman d :
                draughtsmen) {
            this.draughtsmen.add(new Draughtsman(d));
        }
    }

    public void addChild(AndOrGameTreeNode node) {
        children.add(node);
    }

    public void isItWinNode(Boolean or) {  // dodaje wartość na podstawie wartośi dzieci
        double val = 10000;
        if (or) {
            for (AndOrGameTreeNode child : children) {
                if (child.win) {
                    this.win = true;
                    this.value = child.value;
                    break;
                }
            }
        } else { //AND
            this.win = true;
            for (AndOrGameTreeNode child : children) {
                if(val > child.value){
                    val = child.value;
                }
                if (!child.win) {
                    this.win = false;
                }
            }
            this.value = val;
        }
    }


    public void countValue(AndOrGameTreeNode prevNode) {      // Oblicza wartości dla najniższego poziomu dla gracza 1
        int val1 = 0;
        int val2 = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.getPlayer() == 1) {
                if (d.isQueen() && d.getPlayer()!=2) {
                    val1 += 30; //pkt za damkę
                } else {
                    val1 += d.getY();    // na podstawie odleglosci od bazy
                }

            } else { //gracz2
                if (d.isQueen() && d.getPlayer()!=1) {
                    val2 += 30; //pkt za damkę
                } else {
                    val2 += 4 - d.getY();    // na podstawie odleglosci od bazy
                }

            }
        }
        val1 += 100*countBeatenDraughtsmen();
        val2 -= 100*countBeatenDraughtsmen();
        this.value = val1 - val2;
        int i = canBeat(prevNode);
        if(i!=0) {
            if(i>0){
                this.win = true;
            }
            else{
                this.win = false;
            }
        }
        else if (val1 >= val2) {
            this.win = true;
        }

    }



    public void countValueByQueenAndBeating(AndOrGameTreeNode prevNode) {      // Funkcja nie uwzględnia odległości od bazy
        int val1 = 0, val2 = 0;
        int counter1 = 0, counter2 = 0;
        int size = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.getPlayer() != 2) {
                size = d.getBoardSize();
                counter1++;
                if (!d.isBeaten() && d.isQueen()) {
                    val1 +=30;   // za damki
                }
            }
            else{
                counter2++;
                if (!d.isBeaten() && d.isQueen()) {
                    val2 +=30;   // za damki
                }
            }
        }
        val1 -= (size + 1)/2 - counter1 * 5; // dodatkowa kara za zbite pionki w rozgrywce
        val2 -= (size + 1)/2 - counter2 * 5;
        val1 += 100*countBeatenDraughtsmen();
        val2 -= 100*countBeatenDraughtsmen();
        this.value = val1- val2;

        int i = canBeat(prevNode);
        if(i!=0) {
            if(i>0){
                this.win = true;
            }
            else{
                this.win = false;
            }
        }
        else if (val1 >= val2) {
            this.win = true;
        }
    }

    private int canBeat(AndOrGameTreeNode prevNode){
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
        for (Draughtsman d : prevNode.draughtsmen) {
            if (d.getPlayer() == 1 && !d.isBeaten()) {
                player1--;
            }
            if (d.getPlayer() == 2 && !d.isBeaten()) {
                player2--;
            }
        }
        if(player1 < 0){// mój został zbity
            return -1;
        }
        else if(player2<0){ // ja zbijam
            return 1;
        }
        else{
            return 0;
        }


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
        int val = 5 * player2 - 5 * player1;   // 5 pkt za zbicie, -5 za stratę
        return val;
    }


    public void training() {
        double val = NeuralNetwork.Create(draughtsmen);
        this.value = val;
    }
}