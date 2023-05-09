package engine;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    final GamePanel gamePanel;

    public Window() {
        super("Die Legende Von Müaluenie");

        Settings settings = new Settings(16, 20, 12, 100);
        gamePanel = new GamePanel(settings);
        add(gamePanel);

        setMinimumSize(new Dimension(settings.xTiles(), settings.yTiles()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(800, 500);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }
}