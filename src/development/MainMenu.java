// Joha Zwin

package development;

import engine.GameObject;

import java.awt.*;

public class MainMenu extends GameObject {
    @Override
    protected void load() {
        super.load();

        createMenuButton(this, "Start", this::runGame, 2);
        createMenuButton(this, "Einstellungen", this::openSettings, 2.75f);
        createMenuButton(this, "Skins", this::openSkinsMenu, 3.5f);

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