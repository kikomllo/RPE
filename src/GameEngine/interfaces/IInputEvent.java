package GameEngine.interfaces;

import java.util.Set;

/**
 * Interface que representa um evento de entrada, que é uma coleção de teclas pressionadas.
 * 
 * <p>Esta interface estende a interface {@link Set}, o que significa que um evento de entrada pode ser tratado
 * como um conjunto de códigos de teclas. Cada código de tecla representa uma tecla que foi pressionada no
 * momento do evento.</p>
 * 
 * @author Raquel Nunes, a83883  
 * @author David Silvestre, a83938  
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public interface IInputEvent extends Set<Integer> {

}
