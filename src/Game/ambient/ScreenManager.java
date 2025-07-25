package Game.ambient;

import Game.objects.ObjectManager;
import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import java.util.List;

public class ScreenManager {
    private ObjectManager objectManager;
    private IGameUI ui;
    private ScreenConfig config;
    private Point center, boardCenter;
    private double zoom = 1;
    private Vector offset;

    public ScreenManager(ObjectManager objectManager, IGameUI ui, ScreenConfig config){
        this.objectManager = objectManager;
        this.ui = ui;
        this.config = config;
        this.center = 
            (config.center == null) ? new Point(ui.getWidth()/2, ui.getHeight()/2) : config.center;
        config.maxDis = ui.getWidth()/2;
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
        return sum/objects.size();
    }

    private Point computeCentroid(List<IGameObject> objects){
        int centroidX = (int)getCentroidValue(objects, true);
        int centroidY = (int)getCentroidValue(objects, false);
        
        return new Point(centroidX, centroidY);
    }

    private double computeSpread(List<IGameObject> objects, Point centroid){
        double maxSpread = 0;

        for (IGameObject obj : objects){
            double distance = obj.transform().getPosition().dist(centroid);
            if (distance > maxSpread) maxSpread = distance;
        }

        return maxSpread;
    }

    private double computeZoom(double spread){
        double value;

        value = (spread - config.minDis) / (config.maxDis - config.minDis); //normalization

        value = Double.max(0, Double.min(1, value));

        value = config.maxZoom - value * (config.maxZoom - config.minZoom);

        return value;
    }

    public Point computeDrag(Point position, double scalar) {
        Vector distPlayerBoard = position.sub(boardCenter);
        Point scaledPlayerBoard = distPlayerBoard.multScalar(scalar);
        return scaledPlayerBoard.sub(distPlayerBoard);
    }

    public void updateValues(){
        List<IGameObject> objects = objectManager.getObjects();
        
        Point centroid = computeCentroid(objects);
        double spread = computeSpread(objects, centroid);
        this.zoom = computeZoom(spread);

        this.offset = this.center.sub(centroid);
    }

    public Vector getOffset(){
        return offset;
    }

    public double getZoom(){
        return zoom;
    }

    public void setBoardCenter(Point boardCenter){
        this.boardCenter = boardCenter;
    }

    
}
