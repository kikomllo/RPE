package Game.objects;

import GameEngine.interfaces.IGameObject;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private List<IGameObject> objects = new ArrayList<>();
    private int currentActive = 0;

    public void activate(int direction, boolean deactLast){
        GenericPlayerBehaviour behaviour;
        if (deactLast){
            behaviour = (GenericPlayerBehaviour) objects.get(currentActive).behaviour();
            behaviour.setActiveStatus(false);
        }

        if (currentActive + direction == -1)
            currentActive = objects.size()-1;
        else
            currentActive = (currentActive + direction) % objects.size();

        behaviour = (GenericPlayerBehaviour) objects.get(currentActive).behaviour();
        behaviour.setActiveStatus(true);
    }

    public int getIndex(IGameObject obj){
        return objects.indexOf(obj);
    }

    public IGameObject getObject(int index){
        return objects.get(index);
    }

    public List<IGameObject> getObjects(){
        return objects;
    }

    public void add(IGameObject obj){
        objects.add(obj);
        if (objects.size() == 1)
            activate(0, false);
    }

    public void remove(IGameObject obj){
        objects.remove(obj);
    }
}
