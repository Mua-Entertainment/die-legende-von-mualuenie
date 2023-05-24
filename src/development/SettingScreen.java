package development;

import engine.Button;
import engine.GameObject;
import java.awt.*;

public class SettingScreen extends GameObject
{
    private final MainMenu mainMenu;

    public SettingScreen(MainMenu mainMenu)
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

    }

    private void back()
    {
        mainMenu.load();
        destroy();
    }
}