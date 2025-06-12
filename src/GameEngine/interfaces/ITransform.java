package GameEngine.interfaces;

import GameEngine.core.utils.Point;

/**
 * Interface que representa a transformação de um {@code IGameObject}, incluindo
 * posição, rotação, escala e camada de renderização.
 * 
 * <p>
 * Este componente é essencial para posicionar objetos no mundo do jogo e
 * determinar a sua orientação e dimensão relativa.
 * </p>
 * 
 * <p>
 * Usado pela engine para aplicar transformações consistentes em objetos.
 * </p>
 * 
 * @version 10-05-2025
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 */
public interface ITransform {

    /**
     * Move este {@code ITransform} por um vetor de deslocação e define a nova
     * camada.
     * 
     * @param dPos  Vetor de deslocação.
     * @param layer Nova camada (z-index) do objeto.
     */
    void move(Point dPos, int layer);

    /**
     * Aplica uma rotação incremental ao {@code ITransform}.
     * 
     * @param dTheta Ângulo de rotação em graus a adicionar ao atual.
     */
    void rotate(double dTheta);

    /**
     * Altera a escala atual deste {@code ITransform} com base num fator
     * incremental.
     * 
     * @param dScale Valor de incremento (ou decremento) a aplicar à escala.
     */
    void scale(double dScale);

    /**
     * Retorna a posição atual (x, y) do objeto no espaço do jogo.
     * 
     * @return {@code Point} representando a posição.
     */
    Point getPosition();

    /**
     * Retorna a camada atual do objeto, usada para determinar ordem de desenho e
     * colisões.
     * 
     * @return Inteiro representando a camada (z-index).
     */
    int layer();

    /**
     * Retorna o ângulo atual de rotação do objeto em graus.
     * 
     * @return Valor em graus da rotação.
     */
    double angle();

    /**
     * Retorna o fator de escala atual aplicado ao objeto.
     * 
     * @return Escala como valor decimal (ex: 1.0 é tamanho original).
     */
    double scale();
}
