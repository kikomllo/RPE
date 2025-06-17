package GameEngine.gui.loader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageLoader {

    @SuppressWarnings("CallToPrintStackTrace")
    public static BufferedImage loadImage(String path) {
        File imageFile = new File(path);

        if (!imageFile.exists()) {
            return null;
        }

        BufferedImage originalImage;

        try {
            originalImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        BufferedImage optimizedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = optimizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return optimizedImage;
    }

    public static List<BufferedImage> loadImages(List<String> paths) {
        List<BufferedImage> images = new ArrayList<>();

        for (String str : paths) {
            BufferedImage img = ImageLoader.loadImage(str);

            if (img != null) {
                images.add(img);
            }
        }

        return images;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        if (image == null) {
            return null;
        }

        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double ratio = imageHeight/imageWidth;

        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        System.out.println("Width and Height: " + imageWidth + " " + imageHeight);
        System.out.println("ratio: " + ratio);

        if (Math.abs(width - imageWidth) < Math.abs(height - imageHeight)){
            g2d.drawImage(image, 0, 0, width, (int)(width*ratio), null);
        } else g2d.drawImage(image, 0, 0, (int)(height/ratio), height, null);

        g2d.dispose();

        return resizedImage;
    }

    public static List<BufferedImage> resizeAll(List<BufferedImage> images, int width, int height) {
        List<BufferedImage> newImages = new ArrayList<>();

        for (BufferedImage img : images) {
            BufferedImage newImg = ImageLoader.resize(img, width, height);

            if (newImg != null) {
                newImages.add(newImg);
            }
        }

        return newImages;
    }

    public static BufferedImage flipHorizontal(BufferedImage image) {
        if (image == null) {
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());

        Graphics2D g2d = flippedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g2d.dispose();

        return flippedImage;
    }

    public static List<BufferedImage> flipAllHorizontal(List<BufferedImage> images) {
        List<BufferedImage> flippedImages = new ArrayList<>();

        for (BufferedImage img : images) {
            BufferedImage flipped = flipHorizontal(img);
            if (flipped != null) {
                flippedImages.add(flipped);
            }
        }

        return flippedImages;
    }

}
