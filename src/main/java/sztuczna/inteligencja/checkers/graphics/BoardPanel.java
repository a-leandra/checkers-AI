package sztuczna.inteligencja.checkers.graphics;

import lombok.Getter;
import lombok.Setter;
import sztuczna.inteligencja.checkers.Board;
import sztuczna.inteligencja.checkers.Draughtsman;
import sztuczna.inteligencja.checkers.Game;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

@Setter
public class BoardPanel extends JPanel implements MouseListener {
    Dimension dimension = new Dimension(500,500);
    List<FieldButton> fields = new ArrayList<FieldButton>();
    int size;
    Game game;
    @Getter
    Point chosenDraughtsman = new Point();
    @Getter
    Point chosenField = new Point();
    public boolean chosen = false;
    public BoardPanel(int size, Game game) {
        setSize(dimension);
        this.size = size;
        this.game = game;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        paintFields(graphics2D);
        paintDraughtsmen(graphics2D);
        if (getMouseListeners().length == 0)
            addMouseListener(this);

    }



    private void paintFields(Graphics2D g) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j % 2 == 0 && i % 2 == 0 || j %2  == 1 &&  i % 2 == 1) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(i*dimension.width/size, j*dimension.width/size, dimension.width/size, dimension.width/size);
                }
                else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(i*dimension.width/size, j*dimension.width/size, dimension.width/size, dimension.width/size);
                }
            }
        }
    }
    private void paintDraughtsmen(Graphics2D g) {
        Board b = game.getBoard();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (b.getPlayer(x,y) != 0) {

                    g.setColor(b.getPlayer(x,y) == 1 ? Color.WHITE : Color.BLACK);
                    g.fillOval(x*dimension.width/size, y*dimension.width/size, dimension.width/size, dimension.width/size);
                }
            }
        }
    }

    public void refresh() {
        removeAll();
        revalidate();
        repaint();
        chosen = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        chosenDraughtsman.x = Math.floorDiv(e.getX(), (dimension.width / size));
        chosenDraughtsman.y = Math.floorDiv(e.getY(), (dimension.width / size));
        System.out.println("Ruch z " + chosenDraughtsman.x + ", " + chosenDraughtsman.y);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
            chosenField.x = Math.floorDiv(e.getX(), (dimension.width / size));
            chosenField.y = Math.floorDiv(e.getY(), (dimension.width / size));
            System.out.println("Na " + chosenField.x + ", " + chosenField.y);
            chosen = true;
            //paintComponent(graphics2D);
//            if (game.getPlayersTurn() == 2) {
//                game.moveDraughtsman(chosenDraughtsman.x, chosenDraughtsman.y, chosenField.x, chosenField.y);
//                if (game.getPlayersTurn() == 1) {
//                    MainWindow.refresh(game);
//                }
//                try {
//                    Thread.sleep(100);
//                } catch (Exception ex) {}
//            }
            //refresh();

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



}
