package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.ITransform;
import java.awt.Shape;

/**
 * Classe que representa um {@code Collider}, responsável por verificar colisões
 * entre objetos do jogo.
 *
 * Esta classe implementa a interface {@code ICollider}.
 *
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */

public abstract class Collider implements ICollider {
    private final ITransform transform;

    double lastAngle, lastScale;

    /**
     * Construtor que cria uma instância de um {@code Collider}.
     * 
     * @param transform Objeto que implementa a interface {@code ITransform}.
     */
    public Collider(ITransform transform) {
        this.transform = transform;

        this.lastAngle = 0;
        this.lastScale = 1;
    }
    
    public void onUpdate() {
        if (!this.transform.getPosition().equals(this.centroid())) {
            this.translation(new Vector(
                    this.transform.getPosition().getX() - this.centroid().getX(),
                    this.transform.getPosition().getY() - this.centroid().getY()));
        }

        this.rotate(this.transform.angle() - this.lastAngle);
        this.scale(this.transform.scale() / this.lastScale);

        this.lastAngle = this.transform.angle();
        this.lastScale = this.transform.scale();
    }

    public boolean isColliding(ICollider other) {
        return other.isColliding(this);
    }

    public abstract Point centroid();

    public abstract void translation(Vector v);

    public abstract void rotate(double angle);

    public abstract void scale(double scale);

    public abstract Shape getShape();

    public abstract String toString();
}
