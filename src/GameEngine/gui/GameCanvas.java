package GameEngine.gui;

import GameEngine.interfaces.IGameObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class GameCanvas extends JPanel {
    private List<IGameObject> objects;

    private boolean showColliders;
    private int colliderStroke;

    public GameCanvas(Dimension d) {
        setPreferredSize(d);

        this.objects = null;

        this.showColliders = false;
        this.colliderStroke = (int) d.getWidth() / 500;
    }

    public void setObjects(List<IGameObject> objects) {
        this.objects = objects;
    }

    public void setColliderStroke(int stroke) {
        this.colliderStroke = stroke;
    }

    public void showColliders(boolean status) {
        this.showColliders = status;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.objects == null) {
            return;
        }

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Stroke stroke = null;

        if (this.showColliders) {
            stroke = new BasicStroke(this.colliderStroke);
        }

        for (IGameObject gameObj : this.objects) {
            BufferedImage image = (BufferedImage) gameObj.shape().getCurrentShape();

            AffineTransform at = new AffineTransform();

            double posX = gameObj.transform().getPosition().getX() + gameObj.shape().getRelativePosition().getX();
            double posY = gameObj.transform().getPosition().getY() + gameObj.shape().getRelativePosition().getY();

            double centerX = image.getWidth() / 2.0;
            double centerY = image.getHeight() / 2.0;

            double rotation = Math.toRadians(gameObj.transform().angle());
            double scale = gameObj.transform().scale();

            at.translate(posX + centerX, posY + centerY);
            at.rotate(rotation);
            at.scale(scale, scale);
            at.translate(-centerX, -centerY);

            g2d.drawRenderedImage((BufferedImage) image, at);

            if (this.showColliders) {
                g2d.setColor(Color.RED);
                g2d.setStroke(stroke);
                g2d.draw(gameObj.collider().getShape());
            }
        }

        g.dispose();
        g2d.dispose();

    }
}
