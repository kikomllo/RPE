package GameEngine.core.utils;

/**
 * Classe que representa um vetor no plano 2D. É uma extensão de {@link Point},
 * utilizando as componentes {@code x} e {@code y} como base.
 * 
 * <p>
 * Inclui operações vetoriais como produto escalar, inversão, multiplicação por
 * escalar e geração de direções perpendiculares.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class Vector extends Point {

    /**
     * Constrói um vetor com componentes {@code x} e {@code y} definidos.
     * 
     * @param x Componente x do vetor.
     * @param y Componente y do vetor.
     */
    public Vector(double x, double y) {
        super(x, y);
    }

    /**
     * Constrói um vetor a partir de um ponto.
     * 
     * @param p O ponto cujas coordenadas serão utilizadas para criar o vetor.
     */
    public Vector(Point p) {
        super(p.getX(), p.getY());
    }

    /**
     * Multiplica o vetor por um escalar e retorna o resultado como um novo ponto.
     * 
     * @param scalar Valor escalar pelo qual o vetor será multiplicado.
     * @return Um novo {@link Point} com as componentes multiplicadas pelo escalar.
     */
    public Point multScalar(double scalar) {
        return new Point(this.getX() * scalar, this.getY() * scalar);
    }

    /**
     * Retorna o vetor oposto (negado).
     * 
     * @return Um novo {@link Vector} com as componentes invertidas.
     */
    @Override
    public Vector neg() {
        return new Vector(-this.getX(), -this.getY());
    }

    /**
     * Calcula dois vetores perpendiculares ao vetor atual.
     * 
     * @return Um array de dois {@link Vector}, ambos perpendiculares ao vetor
     *         original.
     */
    public Vector[] perpendicularDirections() {
        Vector[] pds = new Vector[2];

        double x = -this.getY();
        double y = this.getX();

        pds[0] = new Vector(x, y);
        pds[1] = new Vector(-x, -y);

        return pds;
    }

    /**
     * Calcula o produto escalar entre este vetor e outro vetor {@code v}.
     * 
     * @param v O outro vetor a ser utilizado no cálculo.
     * @return O valor do produto escalar como {@code double}.
     */
    public double dotProduct(Vector v) {
        return this.getX() * v.getX() + this.getY() * v.getY();
    }

    /**
     * Verifica se o vetor é nulo (ambas as componentes iguais a zero).
     * 
     * @return {@code true} se o vetor for nulo, {@code false} caso contrário.
     */
    public boolean isZero() {
        return this.getX() == 0 && this.getY() == 0;
    }
}
