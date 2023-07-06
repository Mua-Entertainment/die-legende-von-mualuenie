package development.ui;

import development.data.DataFile;
import development.data.Database;
import development.data.User;
import engine.main.*;
import engine.main.Button;
import engine.main.Label;
import engine.tools.SafeList;

import java.awt.*;
import java.sql.SQLException;

// Hauptmenü in dem der Nutzer zwischen verschieden Sachen machen kann
public class MainMenu extends GameObject {

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0x567BB4));

        RectObject headBar = new RectObject();
        addChildren(headBar);
        headBar.setColor(new Color(0x191D3E));
        headBar.setSize(getCanvasSize().width, 0.5f);

        // Zeigt Name und Coins an
        String text = DataFile.getName() + "  -  " + DataFile.getCoins() + " Coins  -  Highscore: " + DataFile.getHighscore();
        createMenuLabel(this, text, 0).setColor(Color.white);

        // Listet die globalen Highscores auf
        createMenuLabel(this, "Highscores", 1f);

        // lädt die 3 besten Highscores und zeigt sie an
        try {
            SafeList<User> highscores = Database.getSortedByHighscore();

            for (int i = 0; i < Math.min(3, Database.getSortedByHighscore().size()); i++) {
                User user = highscores.get(i);

                boolean isThisUser = DataFile.getUID().equals(user.id());
                String name = isThisUser ? "dir" : user.name();

                Label scoreLabel = createMenuLabel(this, String.valueOf(user.highscore()), 1.5f + i * 0.7f);

                if (isThisUser) {
                    scoreLabel.setColor(new Color(0x9200A7));
                }

                Label infoLabel = createMenuLabel(this, "von " + name + " am " + user.date(), 1.75f + i * 0.7f);
                infoLabel.setFont(infoLabel.getFont().deriveFont(10f));
                infoLabel.setColor(isThisUser ? new Color(0xAE00C5) : new Color(0x232323));
            }
        } catch (SQLException e) {
            createMenuLabel(this, "Connection Error", 2.5f).setColor(new Color(0x873434));
        }

        // öffnet Einstellungen
        Button settingsBtn = createMenuButton(this, "Einstellungen", () -> replace(new SettingsMenu()), 4.5f);
        settingsBtn.setX((getCanvasSize().width - settingsBtn.getWidth()) / 2 - 2);

        // zeigt alle Highscores an
        createMenuButton(this, "Bestenliste", () -> replace(new HighscoreMenu()), 3.75f);

        // startet Spiel
        createMenuButton(this, "Spielen", () -> replace(new SceneSelection()), 4.5f);

        // öffnet Skin-Menü
        Button skinsBtn = createMenuButton(this, "Skins", () -> replace(new SkinMenu()), 4.5f);
        skinsBtn.setX((getCanvasSize().width - skinsBtn.getWidth()) / 2 + 2);
    }
}