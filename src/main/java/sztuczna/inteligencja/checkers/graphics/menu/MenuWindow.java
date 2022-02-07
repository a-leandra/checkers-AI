package sztuczna.inteligencja.checkers.graphics.menu;

import lombok.Getter;
import sztuczna.inteligencja.checkers.Game;
import sztuczna.inteligencja.checkers.gametree.GameTree;
import sztuczna.inteligencja.checkers.graphics.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

@Getter
public class MenuWindow extends JFrame {
    JPanel panel = new JPanel();
    private JTextPane titleText;
    private JTextPane sizeText;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JRadioButton radioButton7;
    private JTextPane algText;
    private JTextPane treeTypeText;
    private JTextPane gradeTypeText;
    private JRadioButton buttonMM;
    private JRadioButton buttonAB;
    private JRadioButton buttonAO;
    private JRadioButton buttonDefaultTree;
    private JRadioButton buttonNeural;
    private JRadioButton buttonDefaultGrading;
    private JButton separator;
    private JButton buttonStart;
    @Getter
    int game;
    @Getter
    int gameTree;
    ButtonGroup sizeGroup;
    ButtonGroup algGroup;
    ButtonGroup treeTypeGroup;
    ButtonGroup gradeTypeGroup;
    @Getter
    boolean finished = false;
    int chosenAlg;
    int chosenTree;
    int chosenGrading;


    public MenuWindow(){
        setName("Checkers");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout layout = new GridLayout(9,1);
        //setLayout(layout);
        titleText = new JTextPane();
        titleText.setMargin(new Insets(0,400,50,400));
        titleText.setText("Checkers");
        sizeText = new JTextPane();
        sizeText.setMargin(new Insets(20,150,0,100));

        // SIZE
        sizeText.setText("Size of the board:");
        sizeGroup = new ButtonGroup();
        radioButton5 = new JRadioButton("5");
        radioButton6 = new JRadioButton("6");
        radioButton7 = new JRadioButton("7");
        radioButton5.setMargin(new Insets(10,80,0,200));
        radioButton6.setMargin(new Insets(10,80,0,200));
        radioButton7.setMargin(new Insets(10,75,30,665));
        sizeGroup.add(radioButton5);
        sizeGroup.add(radioButton6);
        sizeGroup.add(radioButton7);

        // ALGORITHM
        algText = new JTextPane();
        algText.setMargin(new Insets(20,100,0,150));
        algText.setText("Computer's algorithm:");
        algGroup = new ButtonGroup();
        buttonMM = new JRadioButton("Min-max   ");
        buttonAB = new JRadioButton("Alpha-beta");
        algGroup.add(buttonMM);
        algGroup.add(buttonAB);
        buttonMM.setMargin(new Insets(10,150,0,200));
        buttonAB.setMargin(new Insets(10,150,0,200));


        // TREE TYPE
        treeTypeText = new JTextPane();
        treeTypeText.setMargin(new Insets(20,65,0,100));
        treeTypeText.setText("Type of the tree:");
        treeTypeGroup = new ButtonGroup();
        buttonAO = new JRadioButton("And-or    ");
        buttonDefaultTree = new JRadioButton("Default tree");
        buttonDefaultTree.setMargin(new Insets(10,80,0,100));
        buttonAO.setMargin(new Insets(10,70,70,100));
        treeTypeGroup.add(buttonDefaultTree);
        treeTypeGroup.add(buttonAO);


        // GRADING TYPE
        gradeTypeText = new JTextPane();
        gradeTypeText.setMargin(new Insets(20,110,0,120));
        gradeTypeText.setText("Type of grading:");
        gradeTypeGroup = new ButtonGroup();
        buttonNeural = new JRadioButton("Neural network");
        buttonNeural.setMargin(new Insets(10,170,70,150));
        buttonDefaultGrading = new JRadioButton("Default grading");
        buttonDefaultGrading.setMargin(new Insets(10,150,0,150));
        gradeTypeGroup.add(buttonDefaultGrading);
        gradeTypeGroup.add(buttonNeural);

//        separator = new JButton();
//        separator.setForeground(new Color(0,0,0,0));
//        separator.setBackground(Color.WHITE);
//        separator.setMargin(new Insets(40,400,30,400));
//        separator.setText("text");
//        separator.setForeground(Color.WHITE);

        buttonStart = new JButton();
        buttonStart.setPreferredSize(new Dimension(300,30));
        buttonStart.setBackground(new Color(191,44,53));
        buttonStart.setForeground(Color.WHITE);
        buttonStart.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonStart.setText("START");
        buttonStart.setMargin(new Insets(40,200,40,200));
        buttonStart.setBorder(BorderFactory.createEmptyBorder());
        panel.add(titleText);
        panel.add(sizeText);
        panel.add(algText);
        panel.add(radioButton5);
        panel.add(buttonMM);
        panel.add(radioButton6);
        panel.add(buttonAB);
        panel.add(radioButton7);
        panel.add(treeTypeText);
        panel.add(gradeTypeText);
        panel.add(buttonDefaultTree);
        panel.add(buttonDefaultGrading);
        panel.add(buttonAO);
        panel.add(buttonNeural);
        //panel.add(separator);
        panel.add(buttonStart);
        panel.setBackground(Color.WHITE);

        titleText.setOpaque(false);
        sizeText.setOpaque(false);
        algText.setOpaque(false);

        titleText.setFont(new Font("Source Code Pro Black", Font.PLAIN, 48));
        titleText.setForeground(new Color(191,44,53));
        titleText.setEditable(false);

        sizeText.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        sizeText.setForeground(new Color(40,40,50));
        sizeText.setEditable(false);

        algText.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        algText.setForeground(new Color(40,40,50));
        algText.setEditable(false);

        treeTypeText.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        treeTypeText.setForeground(new Color(40,40,50));
        treeTypeText.setEditable(false);

        gradeTypeText.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        gradeTypeText.setForeground(new Color(40,40,50));
        gradeTypeText.setEditable(false);

        radioButton5.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        radioButton6.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        radioButton7.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonMM.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonAB.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonAO.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonDefaultTree.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonDefaultGrading.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        buttonNeural.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 16));
        radioButton5.setOpaque(false);
        radioButton6.setOpaque(false);
        radioButton7.setOpaque(false);
        buttonMM.setOpaque(false);
        buttonAB.setOpaque(false);
        buttonAO.setOpaque(false);
        buttonDefaultTree.setOpaque(false);
        buttonDefaultGrading.setOpaque(false);
        buttonNeural.setOpaque(false);


        this.setPreferredSize(new Dimension(1000,650));
        panel.setPreferredSize(new Dimension(1000,650));

        panel.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        buttonAO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonAO.isSelected()) {
                    buttonAB.setEnabled(false);
                    buttonAB.setSelected(false);
                    buttonMM.setSelected(true);
                }
            }
        });
        buttonDefaultTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonDefaultTree.isSelected()) {
                    buttonAB.setEnabled(true);
                }
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sizeGroup.getSelection() != null && algGroup.getSelection() != null) {
                    if (radioButton5.isSelected()) {
                        game = 5;
                    }
                    if (radioButton6.isSelected()) {
                        game = 6;
                    }
                    if (radioButton7.isSelected()) {
                        game = 7;
                    }

                    if (buttonMM.isSelected()) {
                        chosenAlg = 0;
                    }
                    if (buttonAB.isSelected()) {
                        chosenAlg = 1;
                    }
                    if (buttonAO.isSelected()) {
                        chosenTree = 0;
                        buttonAB.setEnabled(false);
                        System.out.println("Click");
                        buttonMM.setSelected(true);
                    }
                    if (buttonDefaultTree.isSelected()) {
                        chosenTree = 1;
                    }
                    if (buttonDefaultGrading.isSelected()) {
                        chosenGrading = 0;
                    }
                    if (buttonNeural.isSelected()) {
                        chosenGrading = 1;
                    }
                    finished = true;
                    setVisible(false);
                }
                else {
                    System.out.println("nie wybrano");
                }
            }
        });

        pack();
        setVisible(true);
    }

    public int getChosenAlg() {
        return chosenAlg;
    }
}
