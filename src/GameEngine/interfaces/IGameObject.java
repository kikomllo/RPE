package GameEngine.interfaces;

/**
 * Interface que representa um objeto de jogo na engine.
 * Cada {@code IGameObject} deve possuir uma transformação, comportamento,
 * forma e, opcionalmente, capacidades de colisão.
 * 
 * <p>
 * Estes objetos são geridos pelo ciclo de vida da engine,
 * podendo ser ativados, desativados ou destruídos.
 * </p>
 * 
 * <p>
 * É o elemento base para entidades interativas no jogo.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public interface IGameObject {

    /**
     * Retorna o nome identificador do objeto de jogo.
     * 
     * @return {@code String} com o nome do objeto.
     */
    public String name();

    /**
     * Retorna o componente de transformação (posição, rotação, escala)
     * associado ao objeto.
     * 
     * @return {@code ITransform} deste objeto de jogo.
     */
    public ITransform transform();

    /**
     * Retorna o componente de colisão do objeto.
     * 
     * @return {@code ICollider} associado a este objeto.
     */
    public ICollider collider();

    /**
     * Retorna a forma visual ou lógica associada a este objeto.
     * 
     * @return {@code IShape} correspondente.
     */
    public IShape shape();

    /**
     * Retorna o comportamento associado a este objeto.
     * 
     * @return {@code IBehaviour} que define as reações e lógica do objeto.
     */
    public IBehaviour behaviour();

    /**
     * Verifica se este objeto está em colisão com outro.
     * 
     * @param gameObject Outro {@code IGameObject} a verificar colisão.
     * @return {@code true} se estiverem em colisão, {@code false} caso contrário.
     */
    boolean isColliding(IGameObject gameObject);

    /**
     * Ativa este objeto, permitindo que seja atualizado e incluído na lógica da
     * engine.
     */
    void enable();

    /**
     * Desativa este objeto, removendo-o da atualização e execução ativa da engine.
     */
    void disable();

    /**
     * Destroi este objeto, removendo-o permanentemente da engine.
     */
    void destroy();
}
