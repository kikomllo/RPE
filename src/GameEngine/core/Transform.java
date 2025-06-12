package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.ITransform;

/**
 * Classe que representa a transformação espacial de um objeto no motor de jogo.
 * 
 * Um {@code Transform} encapsula a posição, o layer, o ângulo de rotação e o
 * fator de escala de um objeto. Permite operações de translação, rotação e
 * escala.
 * 
 * A posição é representada pela classe {@link Point}, e os deslocamentos
 * utilizam a classe {@link Vector}.
 * 
 * Implementa a interface {@link ITransform}, fornecendo os métodos necessários
 * para acesso e manipulação das propriedades de transformação.
 * 
 * @see Point
 * @see Vector
 * @see ITransform
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */

public class Transform implements ITransform {
    private final Point position;
    private int layer;
    private double angle, scale;

    /**
     * Construtor que inicializa um {@code Transform} com os parâmetros indicados.
     *
     * @param posicao Posição inicial.
     * @param layer   Layer onde o objeto se encontra.
     * @param angulo  Ângulo de rotação inicial.
     * @param escala  Fator de escala inicial.
     */
    public Transform(Point posicao, int layer, double angulo, double escala) {
        this.position = posicao;
        if (layer >= 0)
            this.layer = layer;
        this.angle = angulo;
        this.scale = escala;
    }

    public void move(Point dPos, int dlayer) {
        this.layer += dlayer;

        this.position.move(new Vector(dPos));
    }

    public void rotate(double dTheta) {
        this.angle += dTheta;
    }

    public void scale(double dScale) {
        this.scale += dScale;
    }

    public Point getPosition() {
        return this.position;
    }

    public int layer() {
        return this.layer;
    }

    public double angle() {
        return this.angle;
    }

    public double scale() {
        return this.scale;
    }

    /**
     * Retorna uma representação textual do Transform, incluindo posição, layer,
     * ângulo e escala.
     *
     * @return String formatada com os valores do Transform.
     */
    @Override
    public String toString() {
        return String.format("%s %d %.2f %.2f", this.position, this.layer, this.angle, this.scale);
    }
}
