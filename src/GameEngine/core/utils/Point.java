package GameEngine.core.utils;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Representa um ponto num plano 2D, com coordenadas {@code x} e {@code y}.
 * Fornece métodos para manipulação e cálculo entre pontos, como deslocamento,
 * orientação e distância.
 * 
 * <p>
 * Esta classe é utilizada como parte do motor de jogo para representar
 * posições e movimentos em duas dimensões.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class Point {
    private double x, y;

    /**
     * Constrói um ponto com coordenadas específicas.
     *
     * @param x A abcissa (coordenada horizontal).
     * @param y A ordenada (coordenada vertical).
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constrói um ponto na origem do referencial (0, 0).
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Obtém a abcissa do ponto.
     *
     * @return A coordenada {@code x} do ponto.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Obtém a ordenada do ponto.
     *
     * @return A coordenada {@code y} do ponto.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Move o ponto somando-lhe as componentes de um vetor de translação.
     * 
     * @param vetor O vetor utilizado para mover o ponto.
     */
    public void move(Vector vetor) {
        this.x += vetor.getX();
        this.y += vetor.getY();
    }

    /**
     * Verifica se dois pontos são iguais, ou seja, se possuem as mesmas
     * coordenadas {@code x} e {@code y}.
     * 
     * @param obj O objeto a comparar com este ponto.
     * @return {@code true} se forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point p2 = (Point) obj;
        return Double.compare(this.x, p2.x) == 0 && Double.compare(this.y, p2.y) == 0;
    }

    /**
     * Calcula a orientação formada por este ponto e dois pontos adicionais.
     * 
     * <p>
     * Baseia-se no cálculo do determinante para os pontos {@code this}, {@code q},
     * {@code r}:
     * </p>
     * <ul>
     * <li>Resultado igual a 0, pontos colineares</li>
     * <li>Resultado maior que 0, orientação anti-horária</li>
     * <li>Resultado menor que 0, orientação horária</li>
     * </ul>
     *
     * @param q O segundo ponto.
     * @param r O terceiro ponto.
     * @return O valor do determinante que define a orientação dos pontos.
     */
    public double calcOrientation(Point q, Point r) {
        return (q.x - x) * (r.y - y) - (q.y - y) * (r.x - x);
    }

    /**
     * Calcula a distância euclidiana entre este ponto e outro ponto.
     *
     * @param p O ponto de destino.
     * @return A distância entre os dois pontos.
     */
    public double dist(Point p) {
        return sqrt(pow(p.x - x, 2) + pow(p.y - y, 2));
    }

    /**
     * Subtrai outro ponto a este, retornando o vetor resultante da operação.
     *
     * @param p O ponto a subtrair.
     * @return Um vetor que representa a diferença entre os dois pontos.
     */
    public Vector sub(Point p) {
        return new Vector(getX() - p.getX(), getY() - p.getY());
    }

    /**
     * Soma outro ponto a este, retornando um novo ponto com as coordenadas somadas.
     *
     * @param p O ponto a somar.
     * @return Um novo ponto com as coordenadas somadas.
     */
    public Point add(Point p) {
        return new Point(getX() + p.getX(), getY() + p.getY());
    }

    /**
     * Adiciona um vetor a este ponto, alterando suas coordenadas.
     *
     * @param v O vetor a adicionar.
     */
    public void add(Vector v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    /**
     * Retorna o ponto oposto (negativo) a este, com coordenadas invertidas.
     *
     * @return Um novo ponto com coordenadas invertidas.
     */
    public Point neg() {
        return new Point(-x, -y);
    }

    /**
     * Retorna o valor de hash do ponto, baseado nas suas coordenadas.
     * 
     * @return O código de hash correspondente ao ponto.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    /**
     * Representa o ponto como uma {@code String} no formato "(x,y)" com duas casas
     * decimais.
     * 
     * @return A string que representa o ponto.
     */
    @Override
    public String toString() {
        return String.format("(%.2f,%.2f)", this.getX(), this.getY());
    }
}
