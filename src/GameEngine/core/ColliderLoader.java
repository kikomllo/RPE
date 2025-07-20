package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.exceptions.CollCircleException;
import GameEngine.exceptions.CollPolyException;
import GameEngine.exceptions.ColliderLoaderException;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.ITransform;

public class ColliderLoader {
    public static ICollider newGenericCollider(ITransform transform, int[] values) throws ColliderLoaderException{
        try {
            if (values.length < 2) 
                throw new ColliderLoaderException();
            
            if (values.length == 3)
                return new CollCircle(transform, new Point(values[0], values[1]), values[2]);
            
            else {
                if (values.length%2 == 1) 
                    throw new ColliderLoaderException();
                
                    else if (values.length == 2)
                    values = new int[] {
                        0, 0, 
                        0, values[0],
                        values[1], values[0],
                        values[1], 0
                    };

                Point[] points = new Point[values.length/2];
                
                for (int i = 0; i < values.length/2; i++) 
                    points[i] = new Point(values[i*2], values[i*2+1]); 
                
                return new CollPoly(transform, points, points.length);
            }
        } catch (CollCircleException | CollPolyException | ColliderLoaderException e){
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }
}
