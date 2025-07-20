package Game.ambient;

import Game.objects.ObjectManager;
import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.IGameObject;
import java.util.List;

public class ScreenManager {
    private ObjectManager objectManager;
    private Point center;
    private double zoom = 1;
    private Vector offset;

    public ScreenManager(ObjectManager objectManager, Point center){
        this.objectManager = objectManager;
        this.center = center;
    }

    private double getCentroidValue(List<IGameObject> objects, boolean x_y){
        double sum = 0;
        for (IGameObject obj : objects){
            if (x_y) {
                sum += obj.transform().getPosition().getX();
            }
            else {
                sum += obj.transform().getPosition().getY();}
        }
        System.out.println();
        return sum/objects.size();
    }

    public void updateValues(){
        List<IGameObject> objects = objectManager.getObjects();
        
        int centroidX = (int)getCentroidValue(objects, true);
        int centroidY = (int)getCentroidValue(objects, false);
        
        Point centroid = new Point(centroidX, centroidY);


        this.offset = this.center.sub(centroid);
    }

    public Vector getOffset(){
        return offset;
    }
}
