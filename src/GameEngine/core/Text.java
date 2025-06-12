package GameEngine.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import GameEngine.interfaces.IText;

/**
 * Classe que representa um texto renderizável como imagem.
 * 
 * A {@code Text} permite definir e renderizar uma cadeia de caracteres com um
 * tamanho e cor específicos. O texto é transformado em uma imagem
 * {@link BufferedImage} que pode ser utilizada como uma forma gráfica no motor
 * de jogo, nomeadamente para HUDs, menus ou informação no ecrã.
 * 
 * Implementa a interface {@link IText}, oferecendo métodos para atualizar o
 * conteúdo, tamanho, cor e para obter a imagem correspondente ao texto atual.
 * 
 * @see IText
 * @see BufferedImage
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class Text implements IText {
    private String text;
    private int size;
    private Color color;

    public Text(String str, int size) {
        this.text = str;
        this.size = size;
        this.color = Color.WHITE;
    }

    public Text(String str, int size, Color color) {
        this.text = str;
        this.size = size;
        this.color = color;
    }

    private BufferedImage stringToImage() {
        Font font = new Font("Arial", Font.PLAIN, this.size);

        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG = tempImg.createGraphics();

        tempG.setFont(font);
        FontMetrics metrics = tempG.getFontMetrics();

        int width = metrics.stringWidth(text) + 10;
        int height = metrics.getHeight() + 10;

        tempG.dispose();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);
        g2d.setComposite(AlphaComposite.SrcOver);

        g2d.setFont(font);
        g2d.setColor(this.color);
        metrics = g2d.getFontMetrics();
        g2d.drawString(text, 5, metrics.getAscent() + 5);

        g2d.dispose();

        return image;
    }

    @Override
    public void setText(String str) {
        this.text = str;
    }

    @Override
    public void setSize(int size) {
        if (size <= 0) {
            this.size = 0;
        } else {
            this.size = size;
        }
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String getString() {
        return this.text;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public BufferedImage getShape() {
        return this.stringToImage();
    }
}
