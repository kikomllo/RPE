package Game.ambient.background;

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

    private IGameEngine engine;

    private IGameObject go;

    public Background(IGameEngine engine, IGameUI ui) { //TODO talvez em vez de passar ui, passa se logo o height e o width
        this.engine = engine;

        this.center = new Point(ui.getWidth() / 2, ui.getHeight() / 2);

        /* BufferedImage image = ImageLoader.loadImage("./resources/ambient/background/0.png");
        image = ImageLoader.resize(image, 1280, 720); */

        List<String> framesPath = List.of(
                "./resources/ambient/background/0.png"/* ,
                "./resources/ambient/background/1.png" */);

        List<BufferedImage> frames = ImageLoader.loadImages(framesPath);

        frames = ImageLoader.resizeAll(frames, ui.getWidth(), ui.getHeight());

        ITransform transform = new Transform(this.center, -1, 0, 1);

        ICollider collider = null;
        try {
            collider = new CollCircle(transform, this.center, 1);
        } catch (CollCircleException e) {
            e.printStackTrace();
        }

        collider.onUpdate();

        System.out.println(frames.get(0).getWidth() + "   " + frames.get(0).getHeight());

        int offsetX = (int) (-frames.get(0).getWidth()/2);
        int offsetY = (int) (-frames.get(0).getHeight()/2);

        IShape shape = new Shape(frames, offsetX, offsetY);

        IBehaviour behaviour = new BackgroundBehaviour();

        IGameObject background = new GameObject("background", transform, collider, shape, behaviour, engine);
        behaviour.gameObject(background);

        this.go = background;
    }

    public void start() {
        this.engine.addEnabled(go);
    }
}
