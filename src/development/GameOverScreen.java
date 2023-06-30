package development;

import engine.RectObject;

import java.awt.*;

public class GameOverScreen extends RectObject {

    @Override
    protected void load() {
        super.load();

        setSize(getCanvasSize());
        setColor(new Color(0x7C000000, true));

        createMenuButton(this, "Nochmal", this::tryAgain, 2);
        createMenuButton(this, "Hauptmenü", this::returnToMainMenu, 3);
    }

    private void tryAgain() {
        PlayMode.getInstance().destroy();
        add(new PlayMode());
        destroy();
    }

    private void returnToMainMenu() {
        add(new MainMenu());
        PlayMode.getInstance().destroy();
        destroy();
    }
}