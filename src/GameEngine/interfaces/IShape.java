package GameEngine.interfaces;

import java.awt.Image;

import GameEngine.core.utils.Point;

/**
 * Interface que representa a forma visual de um {@code IGameObject}.
 * A forma pode ser uma imagem ou uma sequência de imagens (animação).
 * 
 * <p>
 * Esta interface fornece métodos para acessar e manipular a forma do objeto,
 * incluindo o controle da posição relativa e a animação.
 * </p>
 * 
 * @version 10-05-2025
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 */
public interface IShape {

    /**
     * Retorna a forma atual do objeto, normalmente uma imagem.
     * 
     * @return A {@code Image} que representa a forma visual do objeto.
     */
    public Image getCurrentShape();

    /**
     * Retorna a posição relativa da forma em relação ao seu objeto de jogo.
     * 
     * @return {@code Point} que representa a posição relativa da forma.
     */
    public Point getRelativePosition();

    /**
     * Avança para o próximo quadro da animação.
     * Este método é utilizado para animações que têm múltiplos quadros.
     */
    public void nextFrame();

    public void setFrame(int frame); //TODO comentar isto
}
