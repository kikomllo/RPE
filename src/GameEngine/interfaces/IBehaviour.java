package GameEngine.interfaces;

import java.util.List;

/**
 * Interface que define o comportamento genérico de um componente no ciclo de
 * vida
 * de um objeto de jogo ({@link IGameObject}) dentro do motor de jogo.
 * 
 * <p>
 * Esta interface permite que objetos respondam a eventos como inicialização,
 * atualização, colisão e alterações no estado de ativação.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public interface IBehaviour {

    /**
     * Retorna o {@link IGameObject} associado a este comportamento.
     * 
     * @return O objeto de jogo associado.
     */
    public IGameObject gameObject();

    /**
     * Define o {@link IGameObject} associado a este comportamento.
     * 
     * @param go O objeto de jogo a ser associado.
     */
    public void gameObject(IGameObject go);

    /**
     * Método chamado em cada ciclo de atualização da engine.
     * 
     * @param deltaTime O tempo em milissegundos desde o último frame.
     * @param ie        O evento de input capturado durante este ciclo, se existir.
     */
    public void onUpdate(long deltaTime, IInputEvent ie);

    /**
     * Método chamado uma vez, quando o comportamento é adicionado à engine.
     */
    public void onInit();

    /**
     * Método chamado quando o seu objeto associado) é ativado pela engine.
     */
    public void onEnable();

    /**
     * Método chamado quando o seu objeto associado é desativado pela engine.
     */
    public void onDisable();

    /**
     * Método chamado quando o seu objeto associado é removido pela engine.
     */
    public void onDestroy();

    /**
     * Método chamado quando o objeto associado colide com outros objetos.
     * 
     * @param go Lista de objetos com os quais houve colisão.
     */
    public void onCollision(List<IGameObject> go);
}
