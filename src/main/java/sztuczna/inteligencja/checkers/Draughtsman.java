package sztuczna.inteligencja.checkers;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class Draughtsman {
    private int x;
    private int y;
    private int player;
    private boolean beaten = false;
    private int boardSize;
    private boolean queen;
    private ArrayList<Integer> optionsX;
    private ArrayList<Integer> optionsY;

    public Draughtsman(int x, int y, int player, int boardSize) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.boardSize = boardSize;
    }

    public Draughtsman(Draughtsman draughtsman) {
        this.x = draughtsman.getX();
        this.y = draughtsman.getY();
        this.player = draughtsman.player;
        this.boardSize = draughtsman.boardSize;
        this.queen = draughtsman.queen;
        this.beaten = draughtsman.beaten;
    }

    public void move(int x, int y, Game game) {
        int defaultDirectionY = player == 1 ? 1 : -1;

        if (!isQueen() && Math.abs(this.x - x) == 2) {
            game.findByPosition((this.x + x)/2, this.y+defaultDirectionY).setBeaten(true);
        }
        // bicie pionków damą
        if (isQueen()) {
            for (Draughtsman d : game.getDraughtsmen()) {
                // czy pionek został zbity gdy damka ruszała się w lewo
                if (d.getX() < this.x && d.getX() > x) {
                    // w górę
                    if (d.getY() < this.y && d.getY() > y) {
                        if (Math.abs(this.x-d.getX()) == Math.abs(this.y-d.getY())) {
                            d.setBeaten(true);
                        }
                    }
                    // w dół
                    if (d.getY() > this.y && d.getY() < y) {
                        if (Math.abs(this.x-d.getX()) == Math.abs(this.y-d.getY())) {
                            d.setBeaten(true);
                        }
                    }
                }
                // czy pionek został zbity gdy damka ruszała się w prawo
                if (d.getX() > this.x && d.getX() < x) {
                    // w górę
                    if (d.getY() < this.y && d.getY() > y) {
                        if (Math.abs(this.x-d.getX()) == Math.abs(this.y-d.getY())) {
                            d.setBeaten(true);
                        }
                    }
                    // w dół
                    if (d.getY() > this.y && d.getY() < y) {
                        if (Math.abs(this.x-d.getX()) == Math.abs(this.y-d.getY())) {
                            d.setBeaten(true);
                        }
                    }
                }
            }
        }
        this.x = x;
        this.y = y;
        if (this.y == 0 || this.y == boardSize - 1) {
            queen = true;
        }
    }

    public void checkOptions(Board board) {
        optionsX = new ArrayList<>();
        optionsY = new ArrayList<>();

        // jeśli gracz 1, to poruszamy się w dół -> y się zwiększa,
        // a jeśli 2, to w górę -> y zmniejsza się o 1
        int defaultDirectionY = player == 1 ? 1 : -1;

        if (isQueen()) {
            checkOptionsForQueen(board);
        }
        // sprawdzamy, czy można się poruszyć (dla zwyklego pionka)
        if (!isQueen() && y + defaultDirectionY >= 0 && y + defaultDirectionY < boardSize) {

            // ruch w lewo
            if (isWithinBorders(x-1, y+defaultDirectionY)) {
                // czy pole nie jest zajęte
                if (!board.isTaken(x-1, y+defaultDirectionY)) {
                    optionsX.add(x-1);
                    optionsY.add(y+defaultDirectionY);
                }
                // czy pionek przeciwnika
                else if (board.getPlayer(x-1, y+defaultDirectionY) != player) {
                    // czy może zbić
                    if (isWithinBorders(x - 2, y + 2 * defaultDirectionY) && !board.isTaken(x - 2, y + 2 * defaultDirectionY)) {
                        optionsX.add(x-2);
                        optionsY.add(y+2*defaultDirectionY);
                    }
                }
            }

            //ruch w prawo
            if (isWithinBorders(x+1, y+defaultDirectionY)) {
                // czy pole nie jest zajęte
                if (!board.isTaken(x+1, y+defaultDirectionY)) {
                    optionsX.add(x+1);
                    optionsY.add(y+defaultDirectionY);
                }
                // czy pionek przeciwnika
                else if (board.getPlayer(x+1, y+defaultDirectionY) != player) {
                    // czy może zbić
                    if (isWithinBorders(x + 2, y + 2 * defaultDirectionY) && !board.isTaken(x + 2, y + 2 * defaultDirectionY)) {
                        optionsX.add(x+2);
                        optionsY.add(y+2*defaultDirectionY);
                    }
                }
            }
        }

    }

    // sprawdza czy wybrane pole mieści się na planszy
    private boolean isWithinBorders(int x, int y) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }

    // sprawdza czy wybrane współrzędne są w dostępnych opcjach ruchów
    public boolean canMove(int x, int y) {
        for (int i = 0; i < optionsX.size(); i++) {
            if (x == optionsX.get(i) && y == optionsY.get(i)) {
                return true;
            }
        }
        return false;
    }

    // szukanie opcji ruchów dla damy
    private void checkOptionsForQueen(Board board) {

        // ruchy w prawo w dół
        boolean beats = false;
        for(int i = 1; i < boardSize; i++) {
            if (isWithinBorders(x+i, y+i)) {
                if (board.isTaken(x+i, y+i) && !beats) {
                    if (board.getPlayer(x+i, y+i) != player) {
                        beats = true;
                    }
                    else break;
                }
                else {
                    if (isWithinBorders(x+i, y+i) && board.isTaken(x+i, y+i) && beats){ break;}
                    if(board.getPlayer(x+i,y+i)!= player) {
                        optionsX.add(x + i);
                        optionsY.add(y + i);
                    }
                    else if(board.isTaken(x+i, y+i)){break;}

                }
            } else break;
        }

        // ruchy w prawo w górę
        beats = false;
        for(int i = 1; i < boardSize; i++) {
            if (isWithinBorders(x+i, y-i)) {
                if (board.isTaken(x+i, y-i) && !beats) {
                    if (board.getPlayer(x+i, y-i) != player) {
                        beats = true;
                    }
                    else break;
                }
                else {
                    if (isWithinBorders(x+i, y-i) && board.isTaken(x+i, y-i) && beats) break;
                    if(board.getPlayer(x+i,y-i)!= player) {
                        optionsX.add(x + i);
                        optionsY.add(y - i);
                    }else if(board.isTaken(x+i, y-i)){break;}

                }
            } else break;
        }

        // ruchy w lewo w górę
        beats = false;
        for(int i = 1; i < boardSize; i++) {
            if (isWithinBorders(x-i, y-i)) {
                if (board.isTaken(x-i, y-i) && !beats) {
                    if (board.getPlayer(x-i, y-i) != player) {
                        beats = true;
                    }
                    else break;
                }
                else {
                    if (isWithinBorders(x-i, y-i) && board.isTaken(x-i, y-i) && beats) break;
                    if(board.getPlayer(x-i,y-i)!= player) {
                    optionsX.add(x - i);
                    optionsY.add(y - i);
                    }else if(board.isTaken(x-i, y-i)){break;}

                }
            } else break;
        }

        // ruchy w lewo w dół
        beats = false;
        for(int i = 1; i < boardSize; i++) {
            if (isWithinBorders(x-i, y+i)) {
                if (board.isTaken(x-i, y+i) && !beats) {
                    if (board.getPlayer(x-i, y+i) != player) {
                        beats = true;
                    }
                    else break;
                }
                else {
                    if (isWithinBorders(x-i, y+i) && board.isTaken(x-i, y+i) && beats) break;
                    if(board.getPlayer(x-i,y+i)!= player){
                    optionsX.add(x - i);
                    optionsY.add(y + i);
                    }else if(board.isTaken(x-i, y+i)){break;}
                }
            } else break;
        }
    }

}
