package GameEngine.core.utils;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Representa um segmento de reta válido definido por dois pontos distintos.
 * 
 * <p>
 * Fornece métodos para verificar interseções, calcular distância a um ponto
 * e validar a definição do segmento.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class Segment {
    private final Point p1, p2;

    /**
     * Constrói um segmento de reta definido por dois pontos.
     *
     * @param p1 O primeiro ponto do segmento.
     * @param p2 O segundo ponto do segmento.
     * @throws IllegalArgumentException se os pontos forem iguais (segmento
     *                                  inválido).
     */
    public Segment(Point p1, Point p2) {
        if (this.checkPontos(p1, p2)) {
            System.out.println("Segmento:vi");
            System.exit(0); // Pode ser substituído por uma exceção para melhor prática
        }

        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Obtém o primeiro ponto do segmento.
     *
     * @return O ponto {@code p1}.
     */
    public Point getP1() {
        return this.p1;
    }

    /**
     * Obtém o segundo ponto do segmento.
     *
     * @return O ponto {@code p2}.
     */
    public Point getP2() {
        return this.p2;
    }

    /**
     * Verifica se os dois pontos formam um segmento inválido (iguais).
     *
     * @param p1 O primeiro ponto.
     * @param p2 O segundo ponto.
     * @return {@code true} se os pontos forem iguais, {@code false} caso contrário.
     */
    private boolean checkPontos(Point p1, Point p2) {
        return p1.getX() == p2.getX() && p1.getY() == p2.getY();
    }

    /**
     * Verifica se um ponto {@code p} está contido no interior do segmento.
     *
     * @param p O ponto a verificar.
     * @return {@code true} se o ponto estiver no segmento (excluindo extremos),
     *         {@code false} caso contrário.
     */
    public boolean intercept(Point p) {
        double dx = (int) p2.getX() - (int) p1.getX();
        double dy = (int) p2.getY() - (int) p1.getY(); // antes estava getX e getX, foi trocado para getY
        double a_x = p1.getX(), a_y = p1.getY();
        double b_x = p2.getX(), b_y = p2.getY();
        double t = ((p.getX() - a_x) * dx + (p.getY() - a_y) * dy) / (dx * dx + dy * dy);

        if (t >= 0 && t <= 1)
            return p.getX() > min(a_x, b_x) && p.getX() < max(a_x, b_x) &&
                    p.getY() > min(a_y, b_y) && p.getY() < max(a_y, b_y);

        return false;
    }

    /**
     * Verifica se este segmento se intersecta com outro segmento.
     *
     * @param seg0 O outro segmento a verificar.
     * @return {@code true} se os segmentos se intersectarem, {@code false} caso
     *         contrário.
     */
    public boolean checkIntersecao(Segment seg0) {
        Point[] pontos = new Point[4];
        double[] d = new double[4];

        pontos[0] = p1;
        pontos[1] = p2;
        pontos[2] = seg0.getP1();
        pontos[3] = seg0.getP2();

        for (int i = 2, j = 0; j < 4; j++) {
            d[j] = pontos[i].calcOrientation(pontos[i + 1], pontos[j]);
            if (j == 2)
                i = 0;
        }

        return d[0] * d[1] <= 0 && d[2] * d[3] <= 0;
    }

    /**
     * Calcula a menor distância entre este segmento e um ponto arbitrário.
     *
     * @param p O ponto de referência.
     * @return A distância mínima entre o ponto e o segmento.
     */
    public double distPonto(Point p) {
        Point p1 = this.getP1();
        Point p2 = this.getP2();

        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();

        if (dx == 0 && dy == 0) {
            return Math.hypot(p.getX() - p1.getX(), p.getY() - p1.getY());
        }

        double t = ((p.getX() - p1.getX()) * dx + (p.getY() - p1.getY()) * dy) / (dx * dx + dy * dy);

        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }

        double projX = p1.getX() + t * dx;
        double projY = p1.getY() + t * dy;

        return Math.hypot(p.getX() - projX, p.getY() - projY);
    }
}
