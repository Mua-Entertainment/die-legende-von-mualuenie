package development;

import engine.Button;
import engine.GameObject;
import engine.Label;
import java.awt.*;
import java.util.List;

public class SceneSelection extends GameObject {

    private Label sceneLabel;
    private Scene scene;

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0x541414));

        scene = Scene.OVERWORLD;
        sceneLabel = createMenuLabel(this, scene.name().toLowerCase(), 2);
        sceneLabel.setColor(Color.white);

        engine.Button leftBtn = new engine.Button();
        addChildren(leftBtn);
        leftBtn.setSize(0.5f, 0.5f);
        leftBtn.setPosition((getCanvasSize().width - leftBtn.getWidth()) / 2 - 1.5f, 2);
        leftBtn.setSrc("img\\ui\\arrow-left.png");
        leftBtn.click.subscribe(this::switchLeft);

        engine.Button rightBtn = new Button();
        addChildren(rightBtn);
        rightBtn.setSize(0.5f, 0.5f);
        rightBtn.setPosition((getCanvasSize().width - rightBtn.getWidth()) / 2 + 1.5f, 2);
        rightBtn.setSrc("img\\ui\\arrow-right.png");
        rightBtn.click.subscribe(this::switchRight);

        createMenuButton(this, "Spielen", this::play, 2.75f);
    }

    private void switchLeft() {
        int index = List.of(Scene.values()).indexOf(scene);
        index--;

        if (index < 0) {
            index = Scene.values().length - 1;
        }

        scene = Scene.values()[index];
        loadScene();
    }

    private void switchRight() {
        int index = List.of(Scene.values()).indexOf(scene);
        index++;

        if (index >= Scene.values().length) {
            index = 0;
        }

        scene = Scene.values()[index];
        loadScene();
    }

    private void loadScene() {
        sceneLabel.setText(scene.name().toLowerCase());
    }

    private void play() {
        add(new PlayMode(scene));
        destroy();
    }
}