package GameEngine.core;

import GameEngine.core.utils.Point;
import GameEngine.interfaces.IShape;
import GameEngine.interfaces.IText;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Classe que representa uma forma gráfica associada a um objeto do jogo.
 * 
 * Um {@code Shape} pode ser composto por uma imagem única, uma sequência de
 * imagens (frames) para animação, ou por texto renderizável através da
 * interface {@link IText}. Cada forma tem também uma posição relativa ao seu
 * objeto de origem, usada para desenhar corretamente na cena.
 * 
 * Implementa a interface {@link IShape}, fornecendo métodos para obter a imagem
 * atual e para avançar para o próximo frame, caso aplicável.
 * 
 * @see IShape
 * @see IText
 * @see Point
 * 
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 * 
 * @version 10-05-2025
 */
public class Shape implements IShape {
    private List<BufferedImage> frames;
    private final Point relativePosition;

    private int numberOfFrames;
    private int currentFrame;

    private final boolean isText;

    private IText text;

    public Shape(BufferedImage image, int offsetX, int offsetY) {
        this.frames = List.of(image);
        this.relativePosition = new Point(offsetX, offsetY);
        this.numberOfFrames = 1;
        this.currentFrame = 0;

        this.isText = false;
    }

    public Shape(List<BufferedImage> frames, int offsetX, int offsetY) {
        this.frames = frames;
        this.relativePosition = new Point(offsetX, offsetY);
        this.numberOfFrames = frames.size();
        this.currentFrame = 0;

        this.isText = false;
    }

    public Shape(IText text, int offsetX, int offsetY) {
        this.text = text;
        this.relativePosition = new Point(offsetX, offsetY);

        this.isText = true;
    }

    @Override
    public Image getCurrentShape() {
        if (this.isText) {
            return this.text.getShape();
        }

        return this.frames.get(currentFrame);
    }

    @Override
    public Point getRelativePosition() {
        return this.relativePosition;
    }

    @Override
    public void nextFrame() {
        if (this.isText) {
            return;
        }

        if (this.currentFrame++ >= this.numberOfFrames - 1) {
            this.currentFrame = 0;
        }
    }

    @Override
    public void setFrame(int frame) {
        if (this.isText) {
            return;
        }

        this.currentFrame = frame; //TODO verificacao frame < 0, ou frame > len()
    }
}
