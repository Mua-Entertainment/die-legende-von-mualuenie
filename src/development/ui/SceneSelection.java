package development.ui;

import development.data.DataFile;
import development.world.PlayMode;
import development.enums.Scene;
import engine.main.Button;
import engine.main.GameObject;
import engine.main.Label;
import java.awt.*;
import java.util.List;

public class SceneSelection extends GameObject {

    private final int PRICE = 50;
    private Label sceneLabel;
    private Scene scene;
    private Button playButton;

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0x541414));

        scene = Scene.OVERWORLD;
        sceneLabel = createMenuLabel(this, scene.name().toLowerCase(), 2);
        sceneLabel.setColor(Color.white);

        Button leftBtn = new Button();
        addChildren(leftBtn);
        leftBtn.setSize(0.5f, 0.5f);
        leftBtn.setPosition((getCanvasSize().width - leftBtn.getWidth()) / 2 - 1.5f, 2);
        leftBtn.setSrc("img\\ui\\arrow-left.png");
        leftBtn.click.subscribe(this::switchLeft);

        Button rightBtn = new Button();
        addChildren(rightBtn);
        rightBtn.setSize(0.5f, 0.5f);
        rightBtn.setPosition((getCanvasSize().width - rightBtn.getWidth()) / 2 + 1.5f, 2);
        rightBtn.setSrc("img\\ui\\arrow-right.png");
        rightBtn.click.subscribe(this::switchRight);

        playButton = createMenuButton(this, "Spielen", this::play, 2.75f);
        reload();
    }

    private void switchLeft() {
        int index = List.of(Scene.values()).indexOf(scene);
        index--;

        if (index < 0) {
            index = Scene.values().length - 1;
        }

        scene = Scene.values()[index];
        reload();
    }

    private void switchRight() {
        int index = List.of(Scene.values()).indexOf(scene);
        index++;

        if (index >= Scene.values().length) {
            index = 0;
        }

        scene = Scene.values()[index];
        reload();
    }

    // lädt die Buttons und Labels neu
    private void reload() {
        sceneLabel.setText(scene.name().toLowerCase());

        // Überprüft ob Scene freigeschalten ist und bei Bedarf den Preis an
        if (DataFile.getUnlockedScenes().contains(scene)) {
            playButton.label.setText("Spielen");
        } else {
            playButton.label.setText(getPrice() + " Coins");
        }
    }

    private void play() {
        // Startet Spiel falls Scene freigeschalten
        if (DataFile.getUnlockedScenes().contains(scene)) {
            replace(new PlayMode(scene));
        // Schaltet neue Scene falls genug Coins frei
        } else if (DataFile.getCoins() >= getPrice()) {
            DataFile.addCoins(-getPrice());
            DataFile.unlockScene(scene);
            reload();
        }
    }

    private int getPrice() {
        return PRICE * List.of(Scene.values()).indexOf(scene);
    }
}