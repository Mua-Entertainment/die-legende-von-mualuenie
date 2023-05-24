package engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageObject extends GameObject {

    BufferedImage img;

    public void setSrc(String src) {
        if (src != null) {
            File file = new File("assets\\" + src);

            if (file.exists()) {
                try {
                    img = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }
}