package development;

import engine.Button;
import engine.GameObject;
import java.awt.*;


// Einstellungsmenü
public class SettingScreen extends GameObject
{
    private final MainMenu mainMenu;
    private Button backButton;
    private VolumeSlider volumeSlider;

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

        volumeSlider =  new VolumeSlider();
        add(volumeSlider);
    }

    // zurück zum MainMenu
    private void back()
    {
        volumeSlider.destroy();
        mainMenu.load();
        destroy();
    }
}