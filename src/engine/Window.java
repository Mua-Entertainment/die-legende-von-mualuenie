package engine;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    final GamePanel gamePanel;

    public Window(Settings settings, GameObject environment) {
        super("Die Legende Von Müaluenie");

        gamePanel = new GamePanel(settings, environment);

        setMinimumSize(new Dimension(settings.xTiles(), settings.yTiles()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}