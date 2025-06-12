package GameEngine.core;

import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IInputEvent;
import java.util.List;

/**
 * Classe abstrata que fornece uma implementação base para comportamentos
 * de objetos de jogo. As subclasses desta classe devem sobrescrever os métodos
 * para fornecer funcionalidades específicas de comportamento.
 * 
 * @see IBehaviour
 * @see IGameObject
 * @see IInputEvent
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public abstract class Behaviour implements IBehaviour {
    private IGameObject self = null;

    @Override
    public IGameObject gameObject() {
        return this.self;
    }

    @Override
    public void gameObject(IGameObject go) {
        this.self = go;
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onUpdate(long deltaTime, IInputEvent ie) {
    }

    @Override
    public void onCollision(List<IGameObject> go) {
    }
}
