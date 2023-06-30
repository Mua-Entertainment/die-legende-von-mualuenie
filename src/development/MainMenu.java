// Joha Zwin

package development;

import engine.Button;
import engine.GameObject;
import engine.Label;
import engine.RectObject;
import engine.SafeList;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends GameObject {

    @Override
    protected void load() {
        super.load();

        RectObject headBar = new RectObject();
        addChildren(headBar);
        headBar.setColor(new Color(0x191D3E));
        headBar.setSize(getCanvasSize().width, 0.5f);

        // Zeigt Name und Coins an
        String text = Program.data.getName() + " - " + Program.data.getCoins() + " Coins";
        createMenuLabel(this, text, 0).setColor(Color.white);

        // Listet die globalen Highscores auf
        createMenuLabel(this, "Highscores", 1f);

        try {
            SafeList<User> highscores = Program.database.getSortedHighscores();

            for (int i = 0; i < Math.min(3, Program.database.getSortedHighscores().size()); i++) {
                User user = highscores.get(i);

                boolean isThisUser = Program.data.getUID().equals(user.id());
                String name = isThisUser ? "dir" : user.name();

                Label scoreLabel = createMenuLabel(this, String.valueOf(user.highscore()), 1.5f + i * 0.7f);

                if (isThisUser) {
                    scoreLabel.setColor(new Color(0x9200A7));
                }

                Label infoLabel = createMenuLabel(this, "von " + name + " am " + getDateString(user.date()), 1.75f + i * 0.7f);
                infoLabel.setFont(infoLabel.getFont().deriveFont(10f));
                infoLabel.setColor(isThisUser ? new Color(0xAE00C5) : new Color(0x232323));
            }
        } catch (SQLException e) {
            createMenuLabel(this, "Connection Error", 2.5f).setColor(new Color(0x873434));
        }


        Button settingsBtn = createMenuButton(this, "Einstellungen", this::openSettings, 4.5f);
        settingsBtn.setX((getCanvasSize().width - settingsBtn.getWidth()) / 2 - 2);

        createMenuButton(this, "Start", this::runGame, 4.5f);

        Button skinsBtn = createMenuButton(this, "Skins", this::openSkinsMenu, 4.5f);
        skinsBtn.setX((getCanvasSize().width - skinsBtn.getWidth()) / 2 + 2);

        setCanvasBackground(new Color(0x567BB4));
    }

    // startet Spiel
    private void runGame()
    {
        add(new PlayMode());
        destroy();
    }

    // öffnet Einstellungen
    private void openSettings()
    {
        add(new SettingScreen());
        destroy();
    }

    // öffnet Skin-Menü
    private void openSkinsMenu()
    {
        add(new SkinsMenu());
        destroy();
    }

    private String getDateString(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(new Date());
    }
}