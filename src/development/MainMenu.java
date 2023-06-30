// Joha Zwin

package development;

import engine.GameObject;
import engine.SafeList;
import java.awt.*;
import java.sql.SQLException;

public class MainMenu extends GameObject {
    @Override
    protected void load() {
        super.load();

        String highscores = "Highscores: ";

        try {
            SafeList<Integer> scores = Program.database.getSortedHighscores();

            for (int i = 0; i < Math.min(scores.size(), 3); i++) {
                highscores += (i + 1) + ". " + scores.get(i) + "  ";
            }
        } catch (SQLException e) {
            highscores += "Error";
        }

        // Zeigt die globale Ranglist der Highscores an
        createLabel(this, highscores, 0, 0).setWidth(getCanvasSize().width);

        String coins = Program.data.getCoins() + " Coins";
        createLabel(this, coins, 0, 0.5f).setWidth(getCanvasSize().width);

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