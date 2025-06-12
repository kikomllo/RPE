package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.core.utils.Segment;
import GameEngine.core.utils.Vector;
import GameEngine.exceptions.CollPolyException;
import GameEngine.interfaces.ICollider;
import GameEngine.interfaces.ITransform;

import static java.lang.Math.abs;

import java.awt.Polygon;

import java.util.ArrayList;

/**
 * Classe que representa um polígono utilizado para deteção de colisões.
 * 
 * Um {@code CollPoly} é composto por um conjunto de vértices que formam
 * segmentos (arestas), garantindo que não há interseções ou colinearidade que
 * tornem o polígono inválido.
 * 
 * Implementa múltiplas operações geométricas, incluindo cálculo de centroide,
 * translação, escala, rotação e deteção de colisões com círculos e outros
 * polígonos.
 * 
 * @see Point
 * @see Segment
 * @see Vector
 * @see CollCircle
 * @see Collider
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class CollPoly extends Collider {
    private static final int MIN_NUM_OF_POINTS = 3;

    private final int vertexNum;
    private final Point[] vertices;
    private final Segment[] edges;

    public CollPoly(ITransform transform, Point[] vertices, int num) throws CollPolyException {
        super(transform);

        if (num < MIN_NUM_OF_POINTS) {
            throw new CollPolyException("Polygon cannot have less than 3 vertices");
        }

        this.vertexNum = num;
        this.vertices = vertices;
        this.edges = new Segment[this.vertexNum];

        for (int i = 0; i < this.vertexNum; i++) {
            this.edges[i] = new Segment(this.vertices[i], this.vertices[(i + 1) % this.vertexNum]);
        }

        if (CollPoly.checkIntersecoes(this.edges)) {
            throw new CollPolyException("Two or more segments intersect when creating the polygon");
        }

        for (int i = 0; i < this.vertexNum - 2; i++) {
            if (CollPoly.checkColineares(this.vertices[i], this.vertices[i + 1], this.vertices[i + 2])) {
                throw new CollPolyException("Polygon have collinear vertices");
            }
        }

        if (CollPoly.checkColineares(this.vertices[this.vertexNum - 2], this.vertices[this.vertexNum - 1],
                this.vertices[0])) {
            throw new CollPolyException("Polygon have collinear vertices");
        }
    }

    public Point[] getVertices() {
        return this.vertices;
    }

    public int getNumVertices() {
        return this.vertexNum;
    }

    public Point getVertice(int i) {
        if (i < 0 || i >= this.vertexNum) {
            throw new IndexOutOfBoundsException();
        }

        return vertices[i];
    }

    public Segment[] getArrestas() {
        return this.edges;
    }

    private static boolean checkIntersecoes(Segment[] e) {
        int n = e.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (abs(i - j) == 1 || (i == 0 && j == n - 1)) {
                    continue;
                }

                if (e[i].checkIntersecao(e[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkColineares(Point p1, Point p2, Point p3) {
        return p1.calcOrientation(p2, p3) == 0;
    }

    private double calcAreaShoelace() {
        double s1 = 0, s2 = 0;

        for (int i = 0; i < this.vertexNum; i++) {
            int proxIndex = (i + 1) % this.vertexNum; // no fim, pegamos no primeiro elemento (zona vermelha da formula)

            s1 += this.vertices[i].getX() * this.vertices[proxIndex].getY();
            s2 += this.vertices[i].getY() * this.vertices[proxIndex].getX();
        }

        return (s1 - s2) / 2.0;
    }

    public Point centroid() {
        double centX = 0, centY = 0;
        double area = calcAreaShoelace(); // calcula se a area relativa (pode ser + ou -)

        for (int i = 0; i < this.vertexNum; i++) {
            int proxIndex = (i + 1) % this.vertexNum; // no fim, pegamos no primeiro elemento (zona vermelha da formula)

            double fator = (this.vertices[i].getX() * this.vertices[proxIndex].getY())
                    - (this.vertices[proxIndex].getX() * this.vertices[i].getY());

            centX += (this.vertices[i].getX() + this.vertices[proxIndex].getX()) * fator;
            centY += (this.vertices[i].getY() + this.vertices[proxIndex].getY()) * fator;
        }

        centX /= 6 * area;
        centY /= 6 * area;

        return new Point(centX, centY);
    }

    public void translation(Vector vetor) {
        // aqui apenas é necessario mover os pontos, porque as arestas têm
        // referencias para os pontos
        for (int i = 0; i < this.vertexNum; i++) {
            this.vertices[i].move(vetor);
        }
    }

    public void scale(double escala) {
        Point original = this.centroid(); // guardamos a posicao original

        this.translation(new Vector( // movemos o poligono para (0,0), assim é mais facil de escalar
                original.getX() * -1,
                original.getY() * -1));

        for (int i = 0; i < this.vertexNum; i++) {
            Point atual = this.vertices[i];
            atual.move(new Vector(
                    (atual.getX() * escala) - atual.getX(),
                    (atual.getY() * escala) - atual.getY()));
        }

        this.translation(new Vector(original)); // mete se outra vez na posicao original
    }

    public void rotate(double angulo) {
        double rads = Math.toRadians(angulo); // as funcoes trigonometricas usam rads

        Point centroide = this.centroid(); // os pontos serao rotacionados tendo o centroide como centro

        for (int i = 0; i < this.vertexNum; i++) {
            Point atual = this.vertices[i];

            double xp = atual.getX() - centroide.getX();
            double yp = atual.getY() - centroide.getY();

            double xn = xp * Math.cos(rads) - yp * Math.sin(rads);
            double yn = xp * Math.sin(rads) + yp * Math.cos(rads);

            xp = xn + centroide.getX(); // estes dois passos penso que poderiam ser simplificados, mas dessa forma nao
            yp = yn + centroide.getY(); // seguiriamos a formula do prof

            atual.move(new Vector( // calcula se o deslocamento desse ponto e cria se um vetor com base nisso
                    xp - atual.getX(),
                    yp - atual.getY()));
        }
    }

    private Point suporte(CollPoly A, CollPoly B, Vector d) {
        Point suporteA = A.farthest(d);
        Point suporteB = B.farthest(d.neg());
        return suporteA.sub(suporteB);
    }

    public Point farthest(Vector d) {
        double dotP = -10;
        int position = 0;
        for (int i = 0; i < vertices.length; i++) {
            Vector v = new Vector(vertices[i]);
            if (i == 0)
                dotP = v.dotProduct(d);
            else if (v.dotProduct(d) > dotP) {
                dotP = v.dotProduct(d);
                position = i;
            }
        }
        return vertices[position];
    }

    private double checkDirection(Vector ab, Vector d) {
        return ab.dotProduct(d);
    }

    public boolean checkInside(Point p) {
        int intersecCount = 0;
        double px = p.getX();
        double py = p.getY();

        for (int i = 0; i < vertexNum; i++) {
            Point a = vertices[i];
            Point b = vertices[(i + 1) % vertexNum];

            double ax = a.getX();
            double ay = a.getY();
            double bx = b.getX();
            double by = b.getY();

            if (new Segment(a, b).intercept(p)) {
                return true;
            }

            boolean cond1 = (ay > py) != (by > py);
            if (cond1) {
                double intersectX = ax + (bx - ax) * (py - ay) / (by - ay);
                if (intersectX > px) {
                    intersecCount++;
                }
            }
        }

        return (intersecCount % 2 == 1);
    }

    public boolean isColliding(CollPoly p) {
        final double TOLERANCE = 1e-9;
        final Point zero = new Point(0, 0);

        ArrayList<Point> simplex = new ArrayList<>(3);
        Vector d = new Vector(1, 0);
        Vector[] pds;

        for (int its = 0; its < 10; its++) {
            boolean valid = false;
            Vector suporte = (Vector) suporte(this, p, d);

            if (simplex.size() >= 3)
                simplex.removeFirst();

            simplex.add(suporte);
            Vector AO = (Vector) simplex.getFirst().neg();

            if (suporte.equals(zero)) {
                return true;
            }

            // Simplex -> Ponto
            if (simplex.size() == 1) {
                if (suporte.dotProduct(d) <= 0)
                    return false;

                pds = (simplex.getFirst().sub(zero)).perpendicularDirections();
                d = pds[0];
            }

            // Simplex -> SegmentoReta
            if (simplex.size() == 2) {
                if (simplex.get(0).equals(simplex.get(1)))
                    return false;

                Segment sr = new Segment(simplex.get(0), simplex.get(1));

                if (sr.intercept(zero)) {
                    return true;
                }

                Vector vetor = simplex.get(1).sub(simplex.get(0));
                pds = vetor.perpendicularDirections();

                for (Vector pd : pds) {
                    double direction = checkDirection(pd, AO);
                    if (!valid && direction > 0) {
                        valid = true;
                        d = pd;
                    }
                }
                if (!valid)
                    return false;
            }

            // Simplex -> Triângulo
            if (simplex.size() == 3) {
                double max = 0;

                // 3 pontos colineares
                if (abs(simplex.getFirst().calcOrientation(simplex.get(1), simplex.get(2))) < TOLERANCE)
                    return false;

                // Origem dentro do simplex
                CollPoly t = null;

                try {
                    t = new CollPoly(null, simplex.toArray(new Point[0]), 3);
                } catch (CollPolyException e) {
                    System.exit(0);
                }

                // Triangulo t = new Triangulo(simplex);
                if (t.checkInside(zero)) {
                    return true;
                }

                // Novo ponto no simplex
                for (int i = 0; i < simplex.size(); i++) {
                    Point a = simplex.get(i);
                    Point b = simplex.get((i + 1) % simplex.size());

                    Vector vetor = b.sub(a);
                    pds = vetor.perpendicularDirections();

                    for (Vector pd : pds) {
                        double direction = checkDirection(pd, AO);
                        if (direction > max) {
                            max = direction;
                            d = pd;
                        }
                    }
                }

                if (max <= 0)
                    return false;
            }
        }
        return false;
    }

    public boolean isColliding(CollCircle circle) {
        Point center = circle.getCenter();
        double radius = circle.getRadius();

        if (this.checkInside(center)) {
            return true;
        }

        for (Segment edge : this.edges) {
            Point a = edge.getP1();
            Point b = edge.getP2();

            Vector ab = b.sub(a);
            Vector ac = center.sub(a);

            double t = ac.dotProduct(ab) / ab.dotProduct(ab);
            t = Math.max(0, Math.min(1, t));

            Point closest = a.add(ab.multScalar(t));

            if (closest.dist(center) <= radius) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isColliding(ICollider other) {
        return other.isColliding((CollPoly) this);
    }

    public Polygon getShape() {
        int[] x = new int[this.vertexNum];
        int[] y = new int[this.vertexNum];

        for (int i = 0; i < this.vertexNum; i++) {
            x[i] = (int) this.vertices[i].getX();
            y[i] = (int) this.vertices[i].getY();
        }

        return new Polygon(
                x,
                y,
                this.vertexNum);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("");

        for (Point p : this.vertices) {
            str.append(p.toString());
            str.append(" ");
        }

        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}
