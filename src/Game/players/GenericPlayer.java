package Game.players;

import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;

public class GenericPlayer {
    protected final int HEIGHT = 128; // relacao de 1.6
    protected final int WIDTH = 80;

    protected IGameEngine engine;
    protected IGameObject go;

    protected PlayerHealth health;

    public PlayerHealth getHealth() {
        return this.health;
    }

    public IGameObject gameObject() {
        return this.go;
    }
}
