package engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageObject extends GameObject {

    private String src;

    public void setSrc(String src) {
        this.src = "assets\\" + src;
    }

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        if (src != null) {
            File file = new File(src);

            if (file.exists()) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    g.drawImage(img, x, y, width, height, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}