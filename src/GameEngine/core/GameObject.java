package GameEngine.core;

import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.ITransform;

/**
 * Classe que representa um {@code GameObject} e que implementa a interface
 * {@code IGameObject}.
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public final class GameObject implements IGameObject {
    private final String name;
    private final ITransform transform;
    private final ICollider collider;
    private final IShape shape;
    private final IBehaviour behaviour;
    private final IGameEngine gameEngine; // meio estranho mas é o que esta nos diagramas uml do prof

    /**
     * Construtor que recebe uma {@code String}, uma referência para um objeto que
     * implemente {@code ITransform}, e por último uma referência para um objeto que
     * implemente {@code ICollider}.
     * 
     * @param name      Uma {@code String} que representa o nome deste
     *                  {@code GameObject}.
     * @param transform Uma referência para um objeto que implemente
     *                  {@code ITransform}.
     * @param collider  Uma referência para um objeto que implemente
     *                  {@code ICollider}.
     */
    public GameObject(String name, ITransform transform, ICollider collider, IShape shape, IBehaviour behaviour, IGameEngine gameEngine) {
        this.name = name;
        this.transform = transform;
        this.collider = collider;
        this.shape = shape;
        this.behaviour = behaviour;
        this.gameEngine = gameEngine;

        this.init();
    }

    public void init(){
        enable();
    }

    @Override
    public void enable() {
        if (!this.gameEngine.isEnabled(this)) {
            this.gameEngine.enable(this);
        }
    }

    @Override
    public void disable() {
        if (!this.gameEngine.isDisabled(this)) {
            this.gameEngine.disable(this);
        }
    }

    @Override
    public void destroy() {
        this.gameEngine.destroy(this);
    }

    @Override
    public boolean isColliding(IGameObject gameObject) {
        if (this.transform.layer() != gameObject.transform().layer())
            return false;
        return this.collider.isColliding(gameObject.collider());
    }

    /**
     * Devolve o nome deste GameObject.
     *
     * @return O nome.
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * Devolve o transform associado a este GameObject.
     *
     * @return O transform.
     */
    @Override
    public ITransform transform() {
        return this.transform;
    }

    /**
     * Devolve o collider associado a este GameObject.
     *
     * @return O collider.
     */
    @Override
    public ICollider collider() {
        return this.collider;
    }

    @Override
    public IShape shape() {
        return this.shape;
    }

    @Override
    public IBehaviour behaviour() {
        return this.behaviour;
    }

    /**
     * Devolve uma representação textual do GameObject,
     * incluindo o nome, o transform e o collider.
     *
     * @return A representação em string do GameObject.
     */
    @Override
    public String toString() {
        return String.format("%s\n%s\n%s", this.name, this.transform, this.collider);
    }

}
