package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;
import GameEngine.exceptions.CollCircleException;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.ITransform;

import java.awt.geom.Ellipse2D;

/**
 * Classe que representa um colisor circular, utilizado para detecção de
 * colisões.
 * 
 * <p>
 * Um {@link CollCircle} é definido por um ponto central e um raio, e implementa
 * os métodos necessários para verificar colisões, realizar transformações
 * (como translação, escala e rotação), e representar graficamente o colisor
 * como um círculo.
 * </p>
 * 
 * @see ICollider
 * @see ITransform
 * @see CollPoly
 * @see Point
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class CollCircle extends Collider {
    private final Point center;
    private double radius;

    public CollCircle(ITransform transform, Point center, double radius) throws CollCircleException {
        super(transform);

        if (radius <= 0) {
            throw new CollCircleException("Radius can't be <= 0");
        }

        if (center == null) {
            throw new CollCircleException("Center point can't be null");
        }

        this.radius = radius;
        this.center = center;
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Point centroid() {
        return new Point(
                this.center.getX(),
                this.center.getY());
    }

    public void translation(Vector v) {
        this.center.move(v);
    }

    public void scale(double scale) {
        this.radius *= scale;

        if (this.radius < 0) {
            this.radius = 0.0;
        }
    }

    public void rotate(double angle) {
        return;
    }

    public boolean isColliding(CollCircle other) {
        return (other.getCenter().dist(this.getCenter()) < other.getRadius() + this.getRadius());
    }

    public boolean isColliding(CollPoly other) {
        return other.isColliding(this);
    }

    public boolean isColliding(ICollider other) {
        return other.isColliding((CollCircle) this);
    }

    public Ellipse2D getShape() {
        return new Ellipse2D.Double(
                this.center.getX() - this.radius,
                this.center.getY() - this.radius,
                this.radius * 2,
                this.radius * 2);
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", this.center, this.radius);
    }
}
