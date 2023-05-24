package engine;

import java.awt.*;
import java.awt.image.Raster;

public class Button extends ImageObject {

    public final VoidEvent Click = new VoidEvent();
    public final Label label = new Label();
    private Raster imgRaster;
    private boolean hover, click;

    @Override
    protected void load() {
        super.load();

        label.setSize(this.getSize());
        addChildren(label);
    }

    @Override
    protected void update() {
        super.update();

        if (hover) {
            if (!getHover()) {
                setBrightness(0);
                hover = false;
            }

            if (click) {
                if (!getInput().mousePressed()) {
                    setBrightness(15);
                    click = false;
                }
            } else {
                if (getInput().mousePressed()) {
                    setBrightness(30);
                    click = true;
                }
            }
        } else if (getHover()) {
            setBrightness(15);
            hover = true;
        }
    }

    @Override
    public void setSrc(String src) {
        super.setSrc(src);

        if (img != null) {
            imgRaster = img.getData();
        }
    }

    private boolean getHover() {
        return getCursorPosition().x >= getGlobalPosition().x &&
            getCursorPosition().x <= getGlobalPosition().x + getSize().width() &&
            getCursorPosition().y >= getGlobalPosition().y &&
            getCursorPosition().y <= getGlobalPosition().y + getSize().height();
    }

    private void setBrightness(int increase) {
        if (img != null && imgRaster != null) {
            for (int x = 0; x < imgRaster.getWidth(); x++) {
                for (int y = 0; y < imgRaster.getHeight(); y++) {
                    Color def = getColor(imgRaster.getPixel(x, y, new int[4]));

                    Color color = new Color(
                            Math.min(def.getRed() + increase, 255),
                            Math.min(def.getGreen() + increase, 255),
                            Math.min(def.getBlue() + increase, 255),
                            def.getAlpha()
                    );

                    img.getRaster().setPixel(x, y, new int[] {
                            color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()
                    });
                }
            }
        }
    }

    private Color getColor(int[] rgba) {
        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
}