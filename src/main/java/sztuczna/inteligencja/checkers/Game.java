package sztuczna.inteligencja.checkers;

import lombok.Getter;
import lombok.Setter;
import sztuczna.inteligencja.checkers.andOrTree.AndOrGameTreeNode;
import sztuczna.inteligencja.checkers.gametree.GameTreeNode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Game {
    private Board board;
    private List<Draughtsman> draughtsmen = new ArrayList<>();
    private int size;
    private int playersTurn = 1;
    private boolean gameOver = false;
    int playerWon;

    public Game(int size) {
        board = new Board(size);
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == 0 && i % 2 == 0) {
                    draughtsmen.add(new Draughtsman(i, j, 1, size));
                }
                if (j == size-1 && i % 2 == 0 && size%2==1 || j == size-1 && size%2==0 && i % 2 == 1 ) {
                    draughtsmen.add(new Draughtsman(i, j, 2, size));
                }
            }
        }
    }

    public Game(Game game) {
        this.board = new Board(game.getBoard());
        for (Draughtsman d :
                game.getDraughtsmen()) {
            draughtsmen.add(new Draughtsman(d));
        }
        this.size = game.getSize();
        this.playersTurn = game.getPlayersTurn();
        this.gameOver = game.isGameOver();
    }

    public Draughtsman findByPosition(int x, int y){
        for(Draughtsman d : draughtsmen){
            if(d.getX() == x && d.getY() == y){
                return d;
            }
        }
        return null;
    }

    public void reset(){
        draughtsmen.clear();
        board = new Board(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == 0 && i % 2 == 0) {
                    draughtsmen.add(new Draughtsman(i, j, 1, size));
                }
                if (j == size-1 && i % 2 == 0 && size%2==1 || j == size-1 && size%2==0 && i % 2 == 1 ) {
                    draughtsmen.add(new Draughtsman(i, j, 2, size));
                }
                draughtsmen.get(draughtsmen.size()-1).setQueen(false);
            }
        }
        gameOver = false;
    }

    public void printBoard() {
        board.setFields(draughtsmen);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getPlayer(j, i) != 0) {
                    System.out.print(board.getPlayer(j, i));
                    if (findByPosition(j,i).isQueen()) {
                        System.out.print("D ");
                    }
                    else System.out.print("_ ");

                } else {
                    System.out.print("__ ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public Board getBoard() {
        board.setFields(draughtsmen);
        return  board;
    }

    public void moveDraughtsman(int x, int y, int xDest, int yDest) {
        Draughtsman d = findByPosition(x, y);
        if (d != null) {
            if (d.getPlayer() == playersTurn) {
                d.checkOptions(board);
                if (d.canMove(xDest, yDest)) {
                    d.move(xDest, yDest, this);
                    playersTurn = playersTurn == 1 ? 2 : 1;
                    board.setFields(draughtsmen);
                }
                else {
                   // System.out.println("Nie możesz zrobic ruchu z (" +x+", "+ y+ ") na (" +xDest+", "+ yDest+ ")" );
                }
            } else {
               // System.out.println("To nie jest twój pionek");
            }
        }
        else {
          //  System.out.println("Wybrany pionek nie istnieje");
        }
        removeBeatenDraughtsmen();
        board.setFields(draughtsmen);

    }

    private void removeBeatenDraughtsmen() {
        List<Draughtsman> toDelete = new ArrayList<>();
        for (Draughtsman d : draughtsmen) {
            if (d.isBeaten()) {
                toDelete.add(d);
            }
        }

        for(Draughtsman d : toDelete){
            draughtsmen.remove(d);
        }
    }

//    private boolean didCurrentPlayerWin() {
//        int opponentsDraughtsmen = 0;
//        for (Draughtsman d :
//                draughtsmen) {
//            if (d.getPlayer() != playersTurn) {
//                opponentsDraughtsmen++;
//            }
//        }
//        return opponentsDraughtsmen == 0;
//    }

    public void makeMoveFromNode(GameTreeNode node) {
        this.draughtsmen = new ArrayList<>();
        for (Draughtsman draughtsman:
                node.getDraughtsmen() ) {
            draughtsmen.add(new Draughtsman(draughtsman));
        }
        removeBeatenDraughtsmen();
        board.setFields(draughtsmen);
        playersTurn = (playersTurn)%2+1;
        for (Draughtsman d :
                draughtsmen) {
            d.checkOptions(board);
        }
    }

    public void makeMoveFromNode(AndOrGameTreeNode node) {
        this.draughtsmen = new ArrayList<>();
        for (Draughtsman draughtsman:
                node.getDraughtsmen() ) {
            draughtsmen.add(new Draughtsman(draughtsman));
        }
        removeBeatenDraughtsmen();
        board.setFields(draughtsmen);
        playersTurn = (playersTurn)%2+1;
        for (Draughtsman d :
                draughtsmen) {
            d.checkOptions(board);
        }
    }

    private boolean draughtsmanIsInList(List<Draughtsman> draughtsmenList, Draughtsman d) {
        for (Draughtsman draughtsman : draughtsmenList) {
            if (draughtsman.getX() == d.getX() && draughtsman.getY() == d.getY())
                return true;
        }
        return false;
    }

    public void setDraughtsmen(List<Draughtsman> d) {
        this.draughtsmen = new ArrayList<>();
        for (Draughtsman dr :
                d) {
            this.draughtsmen.add(new Draughtsman(dr));
        }
    }

    public boolean gameOver(){
        int player1 = 0;
        int player2 = 0;
        for(Draughtsman d:draughtsmen){
            if(d.getPlayer() == 1){

                d.checkOptions(board);
                if (d.getOptionsX().size() != 0) {
                    player1++;
                }
            }
            else if(d.getPlayer() == 2){
                d.checkOptions(board);
                if (d.getOptionsX().size() != 0) {
                    player2++;
                }
            }
        }
        if (player1 == 0|| player2 == 0){
            gameOver = true;
        }
        if (player1 == 0) {
             playerWon = 2;
        }
        if (player2 == 0) {
            playerWon = 1;
        }
        return gameOver;
    }

    public void makeRandomMovePlayer2(){
        int player2 = 0;
        ArrayList<Draughtsman> d2 = new ArrayList<>();
        for(Draughtsman d:draughtsmen){
            if(d.getPlayer() == 2){
                d.checkOptions(board);
                if(d.getOptionsX().size() > 0) {
                    d2.add(d);
                    player2++;
                }
            }
        }
        if (d2.size() !=0) {
            int num = (int) Math.floor(Math.random() * player2);
            d2.get(num).checkOptions(board);
            int numberOfOptions = d2.get(num).getOptionsX().size();
            int numOpt = (int) Math.floor(Math.random() * numberOfOptions);
            int x = d2.get(num).getOptionsX().get(numOpt);
            int y = d2.get(num).getOptionsY().get(numOpt);
            moveDraughtsman(d2.get(num).getX(), d2.get(num).getY(), x, y);
        }
    }

}
