package Game.ambient.background;

import Game.ambient.ScreenManager;
import GameEngine.core.CollCircle;
import GameEngine.core.GameObject;
import GameEngine.core.Shape;
import GameEngine.core.Transform;
import GameEngine.core.utils.Point;
import GameEngine.exceptions.CollCircleException;
import GameEngine.gui.loader.ImageLoader;
import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.ITransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Background {
    private Point center;
    private IGameUI ui;
    private final IGameObject go;

    public Background(IGameEngine engine, IGameUI ui, ScreenManager screenManager) {
        this.ui = ui;
        this.center = new Point(ui.getWidth() / 2, ui.getHeight() / 2);

        /* BufferedImage image = ImageLoader.loadImage("./resources/ambient/background/0.png");
        image = ImageLoader.resize(image, 1280, 720); */

        List<String> framesPath = List.of(
                "./resources/ambient/background/0.png"/* ,
                "./resources/ambient/background/1.png" */);

        List<BufferedImage> frames = ImageLoader.loadImages(framesPath);

        frames = ImageLoader.resizeAll(frames, ui.getWidth(), ui.getHeight());

        ITransform transform = new Transform(this.center, -1, 0, 0.5);

        ICollider collider = null;
        try {
            collider = new CollCircle(transform, this.center, 1);
        } catch (CollCircleException e) {
            e.printStackTrace();
        }

        collider.onUpdate();

        IShape shape = new Shape(frames);

        IBehaviour behaviour = new BackgroundBehaviour(this.ui, screenManager);

        IGameObject background = new GameObject("background", transform, collider, shape, behaviour, engine);
        behaviour.gameObject(background);

        this.go = background;
    }
}
