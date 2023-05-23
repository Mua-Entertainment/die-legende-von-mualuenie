package engine;

import java.awt.image.Raster;

public class Button extends ImageObject {
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


    }

    private boolean getHover() {
        return getCursorPosition().x >= getGlobalPosition().x &&
            getCursorPosition().x <= getGlobalPosition().x + getSize().width() &&
            getCursorPosition().y >= getGlobalPosition().y &&
            getCursorPosition().y <= getGlobalPosition().y + getSize().height();
    }
}