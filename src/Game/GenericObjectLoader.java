package Game;

import GameEngine.core.ColliderLoader;
import GameEngine.core.GameObject;
import GameEngine.core.Shape;
import GameEngine.core.Transform;
import GameEngine.core.utils.Point;
import GameEngine.exceptions.ColliderLoaderException;
import GameEngine.gui.loader.ImageLoader;
import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.ITransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class GenericObjectLoader{
    public static IGameObject loadObject(String name, Point position, int[] size,
                        int[] values, List<String> spritesPath, 
                        IBehaviour behaviour, IGameEngine engine) 
    {
        ITransform transform = transformBuilder(position);

        ICollider collider = null;
        try {
            collider = ColliderLoader.newGenericCollider(transform, values);
            collider.onUpdate();
        } catch (ColliderLoaderException e) {
            e.printStackTrace();
        }

        IShape shape = shapeBuilder(spritesPath, size);

        IGameObject go = new GameObject(name, transform, collider, shape, behaviour, engine);
        
        behaviour.gameObject(go);

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

    public void setupTransform(int layer, double angle, double scale, IGameObject object){

    }
}
