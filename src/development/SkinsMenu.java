package development;

import engine.Button;
import engine.GameObject;

import java.awt.*;

public class SkinsMenu extends GameObject {

    private final MainMenu mainMenu;

    public SkinsMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
    @Override
    protected void load() {
        super.load();

        Button returnButton = new Button();
        addChildren(returnButton);
        returnButton.setSrc("img\\ui\\pfeil.png");
        returnButton.setPosition(2,4);
        setCanvasBackground(new Color(0x17C255));
        returnButton.click.subscribe(this::returnToMainMenu);

        //skinAuswahl
        Button skin1 = new Button();
        addChildren(skin1);
        skin1.setSrc("img\\obj\\mua\\mua-sad.png");
        skin1.setPosition(2, 2);
        skin1.click.subscribe(this::skin1);


    }
    private void returnToMainMenu()
    {
        mainMenu.load();
        destroy();

    }
    private void skin1()
    {

    }
}

