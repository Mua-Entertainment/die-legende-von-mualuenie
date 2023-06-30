// Joha Zwin

package development;

import engine.Button;
import engine.GameObject;
import engine.ImageObject;
import engine.SafeList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SkinsMenu extends GameObject {

    private final int SKIN_PRICE = 50;
    private final MainMenu mainMenu;
    private Skin skin;
    private Button selectBtn;

    // Aktuelle Skin-Anzeige
    private final ImageObject skinDisplay = new ImageObject();

    public SkinsMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        skin = Program.data.getMuaSkin();
    }

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0x17C255));

        // Button, der zum Hauptmenü zurückführt
        createButton(this, "Zurück", this::returnToMainMenu, 0.5f, 0.5f);

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

    private void returnToMainMenu() {
        mainMenu.load();
        destroy();
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
        if (Program.data.getUnlockedSkins().contains(skin)) {
            Program.data.setMuaSkin(skin);
            returnToMainMenu();
        } else if (Program.data.getCoins() >= SKIN_PRICE) {
            Program.data.addCoins(-SKIN_PRICE);
            Program.data.unlockSkin(skin);
            loadSkin();
        }
    }

    // lädt den Skin in skinDisplay
    private void loadSkin() {
        String fileName = skin.name().toLowerCase();
        String src = "img\\obj\\mua\\" + fileName + "\\run-6.png";

        String btnText = Program.data.getUnlockedSkins().contains(skin) ? "Auswählen" : (SKIN_PRICE + " Coins");
        selectBtn.label.setText(btnText);

        skinDisplay.setSrc(src);
    }
}