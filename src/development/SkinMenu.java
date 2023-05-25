package development;

import engine.Button;
import engine.GameObject;

import java.awt.*;

public class SkinMenu extends GameObject {

    private final MainMenu mainMenu;
    public SkinMenu(MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;

    }
    @Override
    protected void load() {
        super.load();
        Button Back = new Button();
        addChildren(Back);
        Back.setSrc("img\\ui\\pfeil.png");
        Back.setPosition(2,4);
        setCanvasBackground(new Color(0x17C255));
        Back.click.subscribe(this::back);

        Button skin1 = new Button();
        addChildren(skin1);
        skin1.setSrc("img\\obj\\mua\\run\\mua-sad.png");
        skin1.setPosition(2, 2);
        skin1.click.subscribe(this::skin1);


    }
        private void back()
        {
            mainMenu.load();
            destroy();

        }
        private void skin1()
        {

        }
}

