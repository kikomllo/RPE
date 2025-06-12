package GameEngine.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import GameEngine.exceptions.GameWindowTooSmallException;

public class GameWindow extends JFrame {
    private GameCanvas canvas;

    public GameWindow(int width, int height) throws GameWindowTooSmallException {
        if (width <= 0) {
            throw new GameWindowTooSmallException("Window width must be > 0");
        }

        if (height <= 0) {
            throw new GameWindowTooSmallException("Window height must be > 0");
        }

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setCanvas(GameCanvas gc) {
        if (canvas != null) {
            this.remove(canvas);
        }

        this.canvas = gc;
        this.add(canvas);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void draw() {
        repaint();
    }
}
