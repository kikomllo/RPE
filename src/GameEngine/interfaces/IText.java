package GameEngine.interfaces;

import java.awt.Color;
import java.awt.Image;

/**
 * Interface que representa um texto visual que pode ser exibido na interface
 * gráfica do jogo.
 * Pode ser utilizado para exibir informações, como pontuação, mensagens ou
 * outros textos no jogo.
 * 
 * <p>
 * Esta interface fornece métodos para definir o conteúdo, tamanho, cor e forma
 * do texto.
 * </p>
 * 
 * @version 10-05-2025
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 */
public interface IText {

    /**
     * Define o texto a ser exibido.
     * 
     * @param str O texto que será exibido.
     */
    public void setText(String str);

    /**
     * Define o tamanho da fonte do texto.
     * 
     * @param size O tamanho da fonte do texto.
     */
    public void setSize(int size);

    /**
     * Define a cor do texto.
     * 
     * @param color A cor do texto a ser exibido.
     */
    public void setColor(Color color);

    /**
     * Retorna o texto atual exibido.
     * 
     * @return O texto atual como uma string.
     */
    public String getString();

    /**
     * Retorna o tamanho atual da fonte do texto.
     * 
     * @return O tamanho da fonte.
     */
    public int getSize();

    /**
     * Retorna a forma visual do texto, geralmente representada como uma imagem.
     * 
     * @return A {@code Image} que representa a forma visual do texto.
     */
    public Image getShape();
}
