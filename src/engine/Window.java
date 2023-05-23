package engine;

import javax.swing.*;
import java.awt.*;

// Fenster indem das Spiel läuft
public class Window extends JFrame {

    final GamePanel gamePanel;

    public Window(Settings settings, GameObject environment) {
        // legt Fenstertitel fest
        super("Deine Oma");

        gamePanel = new GamePanel(settings, environment, this);

        // konfiguriert das Fenster
        setMinimumSize(new Dimension(settings.xTiles(), settings.yTiles()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}