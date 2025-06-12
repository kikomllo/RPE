package GameEngine.interfaces;

import java.util.List;

/**
 * Interface que define os métodos essenciais de controlo da execução da engine
 * de jogo. Responsável pela gestão do ciclo de vida dos objetos de jogo e
 * pela coordenação entre os seus estados (ativo, inativo, destruído).
 * 
 * <p>
 * Esta interface permite a abstração da lógica principal da engine, permitindo
 * um controlo claro sobre o estado e comportamento dos {@link IGameObject}.
 * </p>
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public interface IGameEngine {

    /**
     * Adiciona um {@link IGameObject} diretamente ao conjunto de objetos ativos
     * (que serão atualizados e renderizados pela engine).
     * 
     * @param go Objeto de jogo a adicionar ao conjunto de ativos.
     */
    public void addEnabled(IGameObject go);

    /**
     * Adiciona um {@link IGameObject} ao conjunto de objetos inativos (não
     * atualizados).
     * 
     * @param go Objeto de jogo a adicionar ao conjunto de inativos.
     */
    public void addDisabled(IGameObject go);

    /**
     * Ativa um {@link IGameObject}, movendo-o do estado inativo para o ativo.
     * 
     * <p>
     * Chama internamente {@code onEnable()} no objeto ativado.
     * </p>
     * 
     * @param go Objeto de jogo a ser ativado.
     */
    public void enable(IGameObject go);

    /**
     * Desativa um {@link IGameObject}, impedindo que ele seja atualizado ou
     * renderizado.
     * 
     * <p>
     * Chama internamente {@code onDisable()} no objeto desativado.
     * </p>
     * 
     * @param go Objeto de jogo a ser desativado.
     */
    public void disable(IGameObject go);

    /**
     * Remove permanentemente um {@link IGameObject} da engine.
     * 
     * <p>
     * Chama internamente {@code onDestroy()} no objeto destruído.
     * </p>
     * 
     * @param go Objeto de jogo a ser destruído.
     */
    public void destroy(IGameObject go);

    /**
     * Inicia o ciclo de execução principal da engine.
     * 
     * <p>
     * Deve ser chamado apenas uma vez após a configuração inicial.
     * </p>
     */
    public void run();

    /**
     * Verifica se um determinado objeto de jogo está atualmente ativado.
     * 
     * @param go Objeto de jogo a verificar.
     * @return {@code true} se o objeto estiver ativo, {@code false} caso contrário.
     */
    public boolean isEnabled(IGameObject go);

    /**
     * Verifica se um determinado objeto de jogo está atualmente desativado.
     * 
     * @param go Objeto de jogo a verificar.
     * @return {@code true} se o objeto estiver inativo, {@code false} caso
     *         contrário.
     */
    public boolean isDisabled(IGameObject go);

    /**
     * Retorna a lista de todos os objetos de jogo atualmente ativos na engine.
     * 
     * @return Lista de objetos ativos.
     */
    public List<IGameObject> getEnabled();

    /**
     * Retorna a lista de todos os objetos de jogo atualmente inativos na engine.
     * 
     * @return Lista de objetos inativos.
     */
    public List<IGameObject> getDisabled();

    /**
     * Define o tempo decorrido entre frames.
     * 
     * @param deltaTime Tempo em milissegundos entre frames.
     */
    public void setDelta(long deltaTime);
}
