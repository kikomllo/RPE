package GameEngine.gui;

import GameEngine.exceptions.GameWindowTooSmallException;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import GameEngine.interfaces.IInputEvent;
import GameEngine.interfaces.IInputManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameUI implements IGameUI {
    private final GameWindow gameWindow;
    private final GameCanvas canvas;
    private final IInputManager inputManager;

    public GameUI(IInputManager inputManager, int width, int height, String title) throws GameWindowTooSmallException {
        this.gameWindow = new GameWindow(width, height);

        this.inputManager = inputManager;

        this.canvas = new GameCanvas(this.gameWindow.getPreferredSize());

        this.canvas.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        inputManager.press(e.getKeyCode());
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        inputManager.release(e.getKeyCode());
                    }
                });

        this.canvas.setFocusable(true);
        this.canvas.requestFocusInWindow();

        this.gameWindow.setCanvas(this.canvas);
        this.gameWindow.setTitle(title);
    }

    @Override
    public void showColliders(boolean status) {
        this.canvas.showColliders(status);
    }

    @Override
    public void showColliders(boolean status, int stroke) {
        this.canvas.showColliders(status);

        this.canvas.setColliderStroke(stroke);
    }

    @Override
    public void setObjects(List<IGameObject> gol) {
        this.canvas.setObjects(gol);
    }

    @Override
    public IInputEvent getInput() {
        return this.inputManager.getPressedKeys();
    }

    @Override
    public IInputManager getInputManager() {
        return this.inputManager;
    }

    @Override
    public void render() {
        this.gameWindow.draw();
    }

    @Override
    public int getHeight() {
        return (int) this.gameWindow.getPreferredSize().getHeight(); // TODO talvez o int dê problemas?...
    }

    @Override
    public int getWidth() {
        return (int) this.gameWindow.getPreferredSize().getWidth();
    }
}
