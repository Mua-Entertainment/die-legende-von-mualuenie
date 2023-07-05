package development.ui;

import development.world.PlayMode;
import development.enums.Scene;
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
        Scene scene = PlayMode.getInstance().getScene();
        destroy();
        PlayMode.getInstance().destroy();
        add(new PlayMode(scene));
    }

    private void returnToMainMenu() {
        destroy();
        PlayMode.getInstance().destroy();
        add(new MainMenu());
    }
}