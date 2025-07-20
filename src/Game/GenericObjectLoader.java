package Game;

import Game.ambient.ScreenManager;
import Game.objects.GenericPlayerBehaviour;
import Game.objects.ObjectConfig;
import Game.objects.ObjectManager;
import GameEngine.core.ColliderLoader;
import GameEngine.core.GameObject;
import GameEngine.core.Shape;
import GameEngine.core.Transform;
import GameEngine.core.utils.Point;
import GameEngine.exceptions.ColliderLoaderException;
import GameEngine.gui.loader.ImageLoader;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.ITransform;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class GenericObjectLoader{
    ObjectManager objectManager;
    ScreenManager screenManager;
    IGameEngine engine;
    
    public GenericObjectLoader(ObjectManager objectManager, ScreenManager screenManager, IGameEngine engine){
        this.objectManager = objectManager;
        this.screenManager = screenManager;
        this.engine = engine;
    }

    public IGameObject loadObject(ObjectConfig config) 
    {
        final List<Integer> keySet = List.of(
            KeyEvent.VK_W,
            KeyEvent.VK_S,
            KeyEvent.VK_A,
            KeyEvent.VK_D
        );

        final List<Byte> animFrames = List.of(
            (byte) 2,
            (byte) 4
        );

        //TODO: HARDCODED

        ITransform transform = transformBuilder(config.position);

        ICollider collider = null;
        try {
            collider = ColliderLoader.newGenericCollider(transform, config.size);
            collider.onUpdate();
        } catch (ColliderLoaderException e) {
            e.printStackTrace();
        }

        IShape shape = shapeBuilder(config.path, config.size);

        GenericPlayerBehaviour behaviour = new GenericPlayerBehaviour(this.objectManager, this.screenManager, keySet, animFrames);
        
        IGameObject go = new GameObject(config.name, transform, collider, shape, behaviour, engine);

        behaviour.gameObject(go);

        this.objectManager.add(go);

        return go;
    }

    private static ITransform transformBuilder(Point position){
        return new Transform(position, 1, 0, 1);
    }

    private static IShape shapeBuilder(List<String> paths, int[] size){
        List<BufferedImage> sprites = ImageLoader.loadImages(paths);

        sprites = ImageLoader.resizeAll(sprites, size[0], size[1]);

        return new Shape(sprites, -size[0]/2, -size[1]/2);
    }

    public void setupTransform(Point position, int layer, double angle, double scale, IGameObject object){

    }
}
