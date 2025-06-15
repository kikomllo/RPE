package Game.players;

import GameEngine.core.CollPoly;
import GameEngine.core.GameObject;
import GameEngine.core.Shape;
import GameEngine.core.Transform;
import GameEngine.core.utils.Point;
import GameEngine.exceptions.CollPolyException;
import GameEngine.gui.loader.ImageLoader;
import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.ITransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Neny extends GenericPlayer {
    private final Point POSITION = new Point(960, 200);

    public Neny(IGameEngine engine) {
        this.engine = engine;

        this.health = new PlayerHealth(20); //TODO valor perdido

        //XXX criacao do boneco em si
        List<String> spritesPath = List.of(
                "./resources/sprites/neny/default/0.png",
                "./resources/sprites/neny/default/1.png",
                "./resources/sprites/neny/default/Neny_Walking_11.png",
                "./resources/sprites/neny/default/Neny_Walking_12.png",
                "./resources/sprites/neny/default/Neny_Walking_13.png",
                "./resources/sprites/neny/default/Neny_Walking_14.png");

        List<BufferedImage> sprites = ImageLoader.loadImages(spritesPath);

        sprites = ImageLoader.resizeAll(sprites, this.WIDTH, this.HEIGHT);

        ITransform playerTransform = new Transform(this.POSITION, 1, 0, 1);

        Point playerHitbox[] = {
                new Point(0, 0),
                new Point(0, this.HEIGHT),
                new Point(this.WIDTH, this.HEIGHT),
                new Point(this.WIDTH, 0)
        };

        ICollider playerCollider = null;
        try {
            playerCollider = new CollPoly(playerTransform, playerHitbox, 4);
        } catch (CollPolyException e) {
            e.printStackTrace();
            System.exit(0);
        }

        playerCollider.onUpdate();

        IShape playerShape = new Shape(sprites, -this.WIDTH/2, -this.HEIGHT/2);

        IBehaviour playerBehaviour = new NenyBehaviour(this.health, this.HEIGHT);

        IGameObject player = new GameObject("player", playerTransform, playerCollider, playerShape, playerBehaviour, engine);

        playerBehaviour.gameObject(player);

        this.go = player;
    }
}
