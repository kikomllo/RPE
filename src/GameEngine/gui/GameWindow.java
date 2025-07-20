package GameEngine.gui;

import GameEngine.exceptions.GameWindowTooSmallException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GameCanvas canvas;

    public GameWindow(int width, int height) throws GameWindowTooSmallException {
        if (width <= 0) {
            throw new GameWindowTooSmallException("Window width must be > 0");
        }

        if (height <= 0) {
            throw new GameWindowTooSmallException("Window height must be > 0");
        }

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    public void setCanvas(GameCanvas gc) {
        if (canvas != null) {
            this.remove(canvas);
        }

        this.canvas = gc;
        this.setLayout(new BorderLayout());
        this.add(canvas);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void draw() {
        repaint();
    }
}
