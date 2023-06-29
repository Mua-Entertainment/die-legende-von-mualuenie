// Joha Zwin

package development;

import engine.Button;
import engine.GameObject;
import engine.ImageObject;

import java.awt.*;

public class SkinsMenu extends GameObject {

    private final MainMenu mainMenu;
    private Mualuenie.Skin skin;
    // Aktuelle Skin-Anzeige
    private final ImageObject skinDisplay = new ImageObject();

    public SkinsMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        skin = Program.data.getMuaSkin();
    }

    @Override
    protected void load() {
        super.load();

        // Button, der zum Hauptmenü zurückführt
        Button returnButton = new Button();
        addChildren(returnButton);
        returnButton.setSrc("img\\ui\\pfeil.png");
        returnButton.setPosition(2,4);
        setCanvasBackground(new Color(0x17C255));
        returnButton.click.subscribe(this::returnToMainMenu);

        // Button, der Skin auswählt
        createMenuButton(this, "Auswählen", this::selectSkin, 4);

        addChildren(skinDisplay);
        skinDisplay.setSize(2, 2);
        skinDisplay.setPosition((getCanvasSize().width - skinDisplay.getWidth()) / 2f, 2);

        loadSkin();
    }

    private void returnToMainMenu()
    {
        mainMenu.load();
        destroy();
    }

    private void selectSkin() {

    }

    // lädt den Skin in skinDisplay
    private void loadSkin() {
        String fileName = skin.name().toLowerCase();
        String src = "img\\obj\\mua\\" + fileName + "\\run-6.png";

        skinDisplay.setSrc(src);
    }
}