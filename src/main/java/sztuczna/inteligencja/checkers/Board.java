package sztuczna.inteligencja.checkers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Board {
    private int[][] fields;
    private int size;

    public Board(int size) {
        this.size = size;
        fields = new int[size][size];
    }
    public Board(Board board) {
        this.size = board.getSize();
        fields = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fields[i][j] = board.getFields()[i][j];
            }
        }
    }

    public void setFields(List<Draughtsman> draughtsmen) {
        resetFields();
        for (Draughtsman d :
                draughtsmen) {
            if (!d.isBeaten()) {
                fields[d.getX()][d.getY()] = d.getPlayer();
            }
        }
    }

    private void resetFields() {
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields.length; y++) {
                fields[x][y] = 0;
            }
        }
    }
    public boolean isTaken(int x, int y) {
        return fields[x][y] != 0;
    }

    public int getPlayer(int x, int y) {
        return fields[x][y];
    }

}
