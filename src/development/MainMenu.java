package development;

import java.awt.*;

public class MainMenu extends Menu {
    @Override
    protected void load() {
        super.load();

        createButton(2, "Start", this::runGame);
        createButton(2.7f, "Einstellungen", this::openSettings);
        createButton(3.4f, "Skins", this::openSkinsMenu);

        setCanvasBackground(new Color(0x567BB4));
    }

    // startet Spiel
    private void runGame()
    {
        destroy();
        PlayMode playMode = new PlayMode();
        add(playMode);
    }

    // öffnet Einstellungen
    private void openSettings()
    {
        SettingScreen settingScreen = new SettingScreen(this);
        add(settingScreen);
        hide();
    }

    // öffnet Skin-Menü
    private void openSkinsMenu()
    {
        SkinsMenu skinMenu = new SkinsMenu(this);
        add(skinMenu);
        hide();
    }
}