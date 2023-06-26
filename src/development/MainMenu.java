// Joha Zwin

package development;

import engine.GameObject;
import engine.SafeList;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainMenu extends GameObject {
    @Override
    protected void load() {
        super.load();

        createMenuLabel(this, "Global Highscores:", 0.5f);

        try {
            SafeList<Integer> scores = Program.database.getSortedHighscores();

            for (int i = 0; i < Math.min(scores.size(), 3); i++) {
                createMenuLabel(this, (i + 1) + ".  " + scores.get(i), i * 0.5f + 1f);
            }
        } catch (SQLException e) {
            createMenuLabel(this, "Error", 1.5f).setColor(new Color(0x5D1010));
        }

        createMenuButton(this, "Start", this::runGame, 3f);
        createMenuButton(this, "Einstellungen", this::openSettings, 3.75f);
        createMenuButton(this, "Skins", this::openSkinsMenu, 4.5f);


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