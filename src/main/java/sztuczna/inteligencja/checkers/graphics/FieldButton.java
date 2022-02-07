package sztuczna.inteligencja.checkers.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class FieldButton implements ActionListener {
    int x;
    int y;
    int pxSize;

    public FieldButton(int x, int y, int pxSize) {
        this.x = x;
        this.y = y;
        this.pxSize = pxSize;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.print("click");
    }

    protected void draw(Graphics g) {
        //super.paintComponent(g);

        g.setColor(new Color(100,10,20,50));
        g.drawRect(x*pxSize, y*pxSize, pxSize, pxSize);
    }
}
