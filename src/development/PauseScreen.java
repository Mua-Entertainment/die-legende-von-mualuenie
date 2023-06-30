package development;

import engine.RectObject;
import java.awt.*;

public class PauseScreen extends RectObject {

    @Override
    protected void load() {
        super.load();

        setSize(getCanvasSize());
        setColor(new Color(0x7C000000, true));

        createMenuButton(this,"Weiter", this::continuePlaying, 2);
        createMenuButton(this, "Aufgeben", this::surrender, 3f);
    }

    private void continuePlaying() {
        paused = false;
        destroy();
    }

    private void surrender() {
        PlayMode.getInstance().gameOver(false, true);
        destroy();
    }
}