package development;

import engine.GameObject;
import engine.Label;
import engine.SafeList;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void reload() {
        labels.forEach(Label::destroy);
        labels.clear();

        int max = Math.min(index + 10, highscores.size() - 10 * index);

        for (int i = index; i < max; i++) {
            User user = highscores.get(i);

            boolean isThisUser = DataFile.getUID().equals(user.id());
            String name = isThisUser ? "dir" : user.name();

            Label scoreLabel = createMenuLabel(this, String.valueOf(user.highscore()), i * .6f);

            if (isThisUser) {
                scoreLabel.setColor(new Color(0x9200A7));
            }

            Label infoLabel = createMenuLabel(this, "von " + name + " am " + user.date(), i * .6f + .5f);
            infoLabel.setFont(infoLabel.getFont().deriveFont(10f));
            infoLabel.setColor(isThisUser ? new Color(0xAE00C5) : new Color(0x232323));

            labels.add(scoreLabel);
            labels.add(infoLabel);
        }
    }

    private void switchUp() {
        if (index >= (highscores.size() / 10)) {
            index = 0;
        } else {
            index += 10;
        }

        reload();
    }

    private void switchDown() {
        if (index <= 0) {
            index = (highscores.size() / 10) * 10;
        } else {
            index -= 10;
        }

        reload();
    }
}