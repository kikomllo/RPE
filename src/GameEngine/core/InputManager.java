package GameEngine.core;

import GameEngine.interfaces.IInputEvent;
import GameEngine.interfaces.IInputManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe responsável por gerir os eventos de entrada do utilizador.
 * 
 * {@code InputManager} mantém um registo das teclas atualmente pressionadas,
 * permitindo consultar o estado atual de qualquer tecla e gerar eventos de
 * entrada para o motor de jogo.
 * 
 * Implementa a interface {@link IInputManager}, sendo utilizada como o
 * mecanismo central de deteção de entradas no sistema.
 * 
 * @see InputEvent
 * @see IInputEvent
 * @see IInputManager
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class InputManager implements IInputManager {
    private final Set<Integer> pressedKeys = new HashSet<>();
    private Set<Integer> releasedKeys = new HashSet<>();

    @Override
    public void press(int keyCode) {
        pressedKeys.add(keyCode);
    }

    @Override
    public void release(int keyCode) {
        pressedKeys.remove(keyCode);
        releasedKeys.add(keyCode);
    }

    @Override
    public void clearRelease(){
        releasedKeys = new HashSet<>();
    }

    @Override
    public boolean isPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    @Override
    public IInputEvent getPressedKeys() {
        return new InputEvent(this.pressedKeys);
    }

    @Override
    public boolean isReleased(int keyCode) {
        return releasedKeys.contains(keyCode);
    }

    @Override
    public IInputEvent getReleasedKeys() {
        return new InputEvent(this.releasedKeys);
    }

}
