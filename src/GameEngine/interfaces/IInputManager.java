package GameEngine.interfaces;

/**
 * Interface responsável pelo gerenciamento das entradas do usuário, como
 * pressionamento e liberação de teclas.
 * 
 * <p>
 * Esta interface fornece métodos para monitorar as teclas pressionadas, liberar
 * teclas e verificar o estado de uma tecla específica.
 * </p>
 * 
 * @version 10-05-2025
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 */
public interface IInputManager {

    /**
     * Registra o pressionamento de uma tecla.
     * 
     * @param keyCode O código da tecla que foi pressionada.
     */
    public void press(int keyCode);

    /**
     * Registra a liberação de uma tecla.
     * 
     * @param keyCode O código da tecla que foi liberada.
     */
    public void release(int keyCode);

    public void clearRelease();
    
    /**
     * Verifica se uma tecla específica está pressionada.
     * 
     * @param keyCode O código da tecla a ser verificada.
     * @return {@code true} se a tecla estiver pressionada, {@code false} caso
     *         contrário.
     */
    public boolean isPressed(int keyCode);

    /**
     * Retorna um objeto que representa os eventos de entrada das teclas atualmente
     * pressionadas.
     * 
     * @return Um {@code IInputEvent} contendo os detalhes das teclas pressionadas.
     */
    public IInputEvent getPressedKeys();

    public boolean isReleased(int keyCode);

    public IInputEvent getReleasedKeys();
}
