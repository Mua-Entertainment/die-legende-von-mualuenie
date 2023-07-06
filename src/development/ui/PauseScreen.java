package development.ui;

import development.world.PlayMode;
import engine.main.RectObject;
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

    // lässt Spiel weiter laufen
    private void continuePlaying() {
        destroy();
        PlayMode.getInstance().setActive(true);
    }

    // Aufgeben und zurück zum Hauptmenü
    private void surrender() {
        destroy();
        PlayMode.getInstance().gameOver(false, true);
    }
}