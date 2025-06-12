package GameEngine.core;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import GameEngine.interfaces.IInputEvent;

/**
 * Classe que representa um evento de entrada contendo um conjunto de teclas
 * pressionadas.
 * 
 * {@code InputEvent} funciona como um wrapper sobre um conjunto de códigos de
 * teclas, permitindo consultas sobre quais teclas foram pressionadas num dado
 * instante.
 * 
 * Estende {@link AbstractSet} e implementa a interface {@link IInputEvent} para
 * integração com o motor de jogo.
 * 
 * @see IInputEvent
 * @see AbstractSet
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class InputEvent extends AbstractSet<Integer> implements IInputEvent {
    private final Set<Integer> keys;

    public InputEvent(Collection<Integer> keys) {
        this.keys = Collections.unmodifiableSet(new HashSet<>(keys));
    }

    @Override
    public Iterator<Integer> iterator() {
        return keys.iterator();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean contains(Object o) {
        return keys.contains(o);
    }
}
