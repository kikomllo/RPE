package GameEngine.interfaces;

import java.awt.Shape;

import GameEngine.core.CollCircle;
import GameEngine.core.CollPoly;
import GameEngine.core.utils.Point;
import GameEngine.core.utils.Vector;

/**
 * Interface que representa um componente de colisão utilizado para deteção de
 * interseções
 * entre objetos de jogo. Implementações desta interface devem representar
 * diferentes formas
 * geométricas como círculos e polígonos.
 * 
 * <p>
 * É usada para calcular colisões entre objetos e fornecer representações
 * geométricas
 * para propósitos gráficos ou lógicos.
 * </p>
 * 
 * @see CollCircle
 * @see CollPoly
 * @see GameEngine.core.utils.Point
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public interface ICollider {

    /**
     * Retorna o centroide da forma do collider.
     * 
     * @return {@link Point} que representa o centro do collider.
     */
    public Point centroid();

    /**
     * Aplica uma translação à forma do collider.
     * 
     * <p>
     * Este método deve mover a forma do collider de acordo com o vetor fornecido,
     * alterando a posição da forma.
     * </p>
     * 
     * @param v O vetor de translação que será aplicado à forma do collider.
     */
    public void translation(Vector v);

    /**
     * Aplica uma rotação à forma do collider.
     * 
     * <p>
     * Este método deve rotacionar a forma do collider pelo ângulo fornecido.
     * A rotação será realizada em torno do centroide da forma.
     * </p>
     * 
     * @param angle O ângulo, em graus, pelo qual a forma será rotacionada.
     */
    public void rotate(double angle);

    /**
     * Aplica uma escala à forma do collider.
     * 
     * <p>
     * Este método deve alterar o tamanho da forma do collider, escalando-a
     * pelo fator fornecido.
     * </p>
     * 
     * @param scale O fator de escala a ser aplicado à forma do collider.
     */
    public void scale(double scale);

    /**
     * Atualiza o estado interno do collider com base na transformação associada
     * (como posição, rotação ou escala).
     * 
     * <p>
     * Deve ser chamado sempre que o {@code ITransform} associado for alterado.
     * </p>
     */
    public void onUpdate();

    /**
     * Verifica se este collider está a colidir com outro qualquer
     * {@link ICollider}.
     * 
     * @param other Outro collider a verificar colisão.
     * @return {@code true} se houver colisão, {@code false} caso contrário.
     */
    public boolean isColliding(ICollider other);

    /**
     * Verifica se este collider está a colidir com um {@link CollCircle}.
     * 
     * @param other Instância de {@code CollCircle} com a qual se deseja testar
     *              colisão.
     * @return {@code true} se houver colisão, {@code false} caso contrário.
     */
    public boolean isColliding(CollCircle other);

    /**
     * Verifica se este collider está a colidir com um {@link CollPoly}.
     * 
     * @param other Instância de {@code CollPoly} com a qual se deseja testar
     *              colisão.
     * @return {@code true} se houver colisão, {@code false} caso contrário.
     */
    public boolean isColliding(CollPoly other);

    /**
     * Retorna a forma geométrica que representa graficamente este collider.
     * 
     * <p>
     * Esta forma pode ser usada, por exemplo, para desenhar colisores ou
     * realizar cálculos adicionais com APIs gráficas.
     * </p>
     * 
     * @return Objeto {@link Shape} correspondente à geometria do collider.
     */
    public Shape getShape();
}
