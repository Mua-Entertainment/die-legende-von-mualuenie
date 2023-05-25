package engine;

import javax.swing.*;
import java.awt.*;

// Fenster indem das Spiel läuft
public class Window extends JFrame {

    // Component zur Anzeige der Inhalte
    final GamePanel gamePanel;

    public Window(Settings settings, GameObject environment, String iconSrc) {
        // legt Fenstertitel fest
        super("Die Legende von Müaluenie");

        gamePanel = new GamePanel(settings, environment, this);

        // lädt das App-Icon
        Image icon = new ImageIcon("assets\\" + iconSrc).getImage();

        // konfiguriert das Fenster
        setMinimumSize(new Dimension(settings.xTiles(), settings.yTiles()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(icon);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}