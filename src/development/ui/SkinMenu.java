// Joha Zwin

package development.ui;

import development.data.DataFile;
import development.enums.Skin;
import engine.main.Button;
import engine.main.GameObject;
import engine.main.ImageObject;
import engine.main.Label;
import java.awt.*;
import java.util.List;

public class SkinMenu extends GameObject {

    private final int SKIN_PRICE = 50;
    private Skin skin;
    private Label skinNameLabel;
    private Button selectBtn;

    // Aktuelle Skin-Anzeige
    private final ImageObject skinDisplay = new ImageObject();

    @Override
    protected void load() {
        super.load();

        skin = DataFile.getMuaSkin();
        setCanvasBackground(new Color(0x17C255));

        // Button, der zum Hauptmenü zurückführt
        createButton(this, "Zurück", () -> replace(new MainMenu()), 0.5f, 0.5f);

        // Label, der den Name des Skins Anzeigt
        skinNameLabel = createMenuLabel(this, "", 1.25f);

        // Button, der Skin auswählt
        selectBtn = createMenuButton(this, "Auswählen", this::selectSkin, 4);

        Button leftBtn = new Button();
        addChildren(leftBtn);
        leftBtn.setSize(0.5f, 0.5f);
        leftBtn.setPosition((getCanvasSize().width - leftBtn.getWidth()) / 2 - 1.5f, 4);
        leftBtn.setSrc("img\\ui\\arrow-left.png");
        leftBtn.click.subscribe(this::switchLeft);

        Button rightBtn = new Button();
        addChildren(rightBtn);
        rightBtn.setSize(0.5f, 0.5f);
        rightBtn.setPosition((getCanvasSize().width - rightBtn.getWidth()) / 2 + 1.5f, 4);
        rightBtn.setSrc("img\\ui\\arrow-right.png");
        rightBtn.click.subscribe(this::switchRight);

        addChildren(skinDisplay);
        skinDisplay.setSize(2, 2);
        skinDisplay.setPosition((getCanvasSize().width - skinDisplay.getWidth()) / 2f, 2);

        loadSkin();
    }

    private void switchLeft() {
        int index = List.of(Skin.values()).indexOf(skin);
        index--;

        if (index < 0) {
            index = Skin.values().length - 1;
        }

        skin = Skin.values()[index];
        loadSkin();
    }

    private void switchRight() {
        int index = List.of(Skin.values()).indexOf(skin);
        index++;

        if (index >= Skin.values().length) {
            index = 0;
        }

        skin = Skin.values()[index];
        loadSkin();
    }

    private void selectSkin() {
        if (DataFile.getUnlockedSkins().contains(skin)) {
            DataFile.setMuaSkin(skin);
            replace(new MainMenu());
        } else if (DataFile.getCoins() >= SKIN_PRICE) {
            DataFile.addCoins(-SKIN_PRICE);
            DataFile.unlockSkin(skin);
            loadSkin();
        }
    }

    // lädt den Skin in skinDisplay
    private void loadSkin() {
        String fileName = skin.name().toLowerCase();
        String src = "img\\obj\\mua\\" + fileName + "\\run-6.png";

        String btnText = DataFile.getUnlockedSkins().contains(skin) ? "Auswählen" : (SKIN_PRICE + " Coins");
        selectBtn.label.setText(btnText);

        skinDisplay.setSrc(src);

        String name = "Müaluenie";

        switch (skin) {
            case KNIGHT -> name = "Ritterluenie";
            case PIRATE -> name = "Piratenluenie";
            case MARTIAN -> name = "Marsluenie";
        }

        skinNameLabel.setText(name);
    }
}