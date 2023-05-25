package development;

import engine.Button;
import engine.GameObject;
import java.awt.*;


// Einstellungsmenü
public class SettingScreen extends GameObject
{
    private final MainMenu mainMenu;
    private Button backButton;
    private Lautstaerkenregler lautstaerkenregler;

    public SettingScreen(MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
    }

    @Override
    protected void load() {
        super.load();

        // Button mit dem man zum MainMenu zurück kommt
        backButton = new Button();
        addChildren(backButton);
        backButton.setSrc("img\\ui\\pfeil.png");
        backButton.setPosition(2,4);
        setCanvasBackground(new Color(0x17C255));
        backButton.click.subscribe(this::back);

        lautstaerkenregler =  new Lautstaerkenregler();
        add(lautstaerkenregler);
    }

    // zurück zum MainMenu
    private void back()
    {
        lautstaerkenregler.destroy();
        mainMenu.load();
        destroy();
    }
}