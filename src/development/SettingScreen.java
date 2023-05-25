package development;

import engine.Button;
import engine.GameObject;
import engine.Slider;


// Einstellungsmenü
public class SettingScreen extends GameObject {
    private final MainMenu mainMenu;

    // bestimmt die Lautstärke
    private Slider volumeSlider;

    public SettingScreen(MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
    }

    @Override
    protected void load() {
        super.load();

        // Button mit dem man zum MainMenu zurück kommt
        createButton(this, "Zurück", this::returnToMainMenu, 0.25f, 0.25f);

        // Slider mit dem man die Lautstärke anpassen kann
        volumeSlider = createMenuSlider(this);
    }

    // zurück zum MainMenu
    private void returnToMainMenu()
    {
        destroy();
        mainMenu.load();
    }
}