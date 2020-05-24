package Tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Monika
 */
public class Game extends JFrame {
    JLabel welcome=new JLabel("Welcome to TETRIS!!", SwingConstants.CENTER);
    JLabel infoBar = new JLabel("   Score:    0      ");


    public Game() {
        setFocusable(true);
        welcome.setFont(new Font("Serif", Font.BOLD, 18));
        welcome.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        add(welcome, BorderLayout.NORTH);
        infoBar.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.GRAY));
        add(infoBar, BorderLayout.EAST);
        setUndecorated(false);
        getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        Frame frame = new Frame(this);
        setSize(new Dimension(300, 450));
        setTitle("Tetris By Monika Delekta");
        frame.setVisible(true);
        add(frame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.begin();
    }

    public JLabel sendText() {
        return infoBar;
    }

    public static void main(String[] args) {
        Game tetris = new Game();
        tetris.setLocationRelativeTo(null);
        tetris.setVisible(true);
    }
}
