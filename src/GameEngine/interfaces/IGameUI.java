package GameEngine.interfaces;

import java.util.List;

/**
 * Interface que representa a interface gráfica do usuário (UI) do jogo.
 * A interface UI é responsável por exibir informações e interagir com o
 * usuário,
 * bem como controlar a exibição de objetos e colisões no jogo.
 * 
 * <p>
 * Esta interface fornece métodos para mostrar ou ocultar colisores, renderizar
 * objetos
 * no jogo e lidar com entradas do usuário.
 * </p>
 * 
 * @version 10-05-2025
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 */
public interface IGameUI {

    /**
     * Controla a exibição de colisores na interface.
     * 
     * @param status {@code true} para mostrar os colisores, {@code false} para
     *               ocultá-los.
     */
    public void showColliders(boolean status);

    /**
     * Controla a exibição de colisores na interface, permitindo também definir a
     * espessura da borda.
     * 
     * @param status {@code true} para mostrar os colisores, {@code false} para
     *               ocultá-los.
     * @param stroke A espessura da borda dos colisores.
     */
    public void showColliders(boolean status, int stroke);

    /**
     * Retorna o evento de entrada atual, permitindo o acesso à interação do usuário
     * com a interface.
     * 
     * @return O evento de entrada atual (por exemplo, teclas pressionadas.
     */
    public IInputEvent getInput();

    /**
     * Retorna o manager de entradas atual, permitindo o acesso à interação do usuário
     * com a interface.
     * 
     * @return O manager de entradas atual (por exemplo, teclas pressionadas.
     */
    public IInputManager getInputManager();
    

    /**
     * Define os objetos do jogo que serão exibidos na interface gráfica.
     * 
     * @param gol Lista de objetos do jogo a serem exibidos.
     */
    public void setObjects(List<IGameObject> gol);

    /**
     * Realiza o processo de renderização de todos os objetos na interface gráfica.
     * Este método é responsável por desenhar os objetos na tela.
     */
    public void render();

    //TODO comentar isto
    public int getHeight();

    //TODO e isto
    public int getWidth();
}
