package engine;

import java.awt.*;
import java.awt.image.Raster;

// Button auf den der Nutzer klicken kann
public class Button extends ImageObject {

    // Klick-Event
    public final VoidEvent click = new VoidEvent();

    // Label, dass Text auf Button anzeigt
    public final Label label = new Label();

    // die Bild-Daten der Grafik mit Standard-Helligkeit
    private Raster imgRaster;

    // gibt an, ob Cursor sich über Button befindet
    private boolean hover;

    @Override
    protected void load() {
        super.load();

        // fügt Label hinzu
        label.setSize(this.getSize());
        addChildren(label);
    }

    @Override
    protected void update() {
        super.update();

        // setzt die Helligkeit des Button
        if (getVisibility()) {
            if (hover) {
                if (!getHover()) {
                    setBrightness(0);
                    hover = false;
                }

                if (getInput().mouseDown()) {
                    setBrightness(30);
                    // führt Klick-Event aus
                    click.invoke();
                }

                if (getInput().mouseUp()) {
                    setBrightness(15);
                }
            } else if (getHover()) {
                setBrightness(15);
                hover = true;
            }
        }
    }

    @Override
    public void setSrc(String src) {
        super.setSrc(src);

        // Überträgt Bilddaten
        if (img != null) {
            imgRaster = img.getData();
        }
    }

    // gibt an, ober Cursor sich über Button berfindet
    private boolean getHover() {
        return getCursorPosition().x >= getGlobalPosition().x &&
            getCursorPosition().x <= getGlobalPosition().x + getSize().width() &&
            getCursorPosition().y >= getGlobalPosition().y &&
            getCursorPosition().y <= getGlobalPosition().y + getSize().height();
    }

    // setzt die Helligkeit der Grafik
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

    // konvertiert int[] in java.awt.Color
    private Color getColor(int[] rgba) {
        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
}