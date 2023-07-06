package development.ui;

import development.world.PlayMode;
import engine.main.RectObject;
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
        replace(new PlayMode(PlayMode.getInstance().getScene()));
    }

    private void returnToMainMenu() {
        PlayMode.getInstance().destroy();
        replace(new MainMenu());
    }
}