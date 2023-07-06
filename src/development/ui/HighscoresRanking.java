package development.ui;

import development.data.DataFile;
import development.data.Database;
import development.data.User;
import engine.main.GameObject;
import engine.main.Label;
import engine.main.SafeList;
import engine.main.Button;
import java.awt.*;
import java.sql.SQLException;

public class HighscoresRanking extends GameObject {

    private int index;
    private SafeList<User> highscores;
    private final SafeList<Label> labels = new SafeList<>();

    @Override
    protected void load() {
        super.load();

        try {
            highscores = Database.getSortedHighscores();
            reload();

            Button upBtn = new Button();
            addChildren(upBtn);
            upBtn.setSrc("img\\ui\\arrow-up.png");
            upBtn.click.subscribe(this::switchUp);
            upBtn.setSize(.5f, .5f);
            upBtn.setPosition(getCanvasSize().width - 1f, .5f);

            Button downBtn = new Button();
            addChildren(downBtn);
            downBtn.setSrc("img\\ui\\arrow-down.png");
            downBtn.click.subscribe(this::switchDown);
            downBtn.setSize(.5f, .5f);
            downBtn.setPosition(getCanvasSize().width - 1f, 1.25f);

            createButton(this, "Zurück", () -> replace(new MainMenu()), .5f, .5f);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void reload() {
        labels.forEach(GameObject::destroy);
        labels.clear();

        int max = Math.min(index + 10, highscores.size());

        for (int i = index; i < max; i++) {
            User user = highscores.get(i);

            boolean isThisUser = DataFile.getUID().equals(user.id());
            String name = isThisUser ? "dir" : user.name();

            float x = getCanvasSize().width / 2 + ((i - index) < 5 ? -2f : 2f) - 1f;
            float y = 1.25f + (i - index - ((i - index) < 5 ? 0 : 5)) * .6f;
            Label scoreLabel = createLabel(this, (i + 1) + ". " + user.highscore(), x, y);

            if (isThisUser) {
                scoreLabel.setColor(new Color(0x9200A7));
            }

            Label infoLabel = createLabel(this, "von " + name + " am " + user.date(), x, .25f + y);
            infoLabel.setFont(infoLabel.getFont().deriveFont(10f));
            infoLabel.setColor(isThisUser ? new Color(0xAE00C5) : new Color(0x232323));

            labels.add(scoreLabel);
            labels.add(infoLabel);
        }
    }

    private void switchUp() {
        if (index <= 0) {
            index = (highscores.size() / 10) * 10;
        } else {
            index -= 10;
        }

        reload();
    }

    private void switchDown() {
        index += 10;

        if (index >= highscores.size()) {
            index = 0;
        }

        reload();
    }
}