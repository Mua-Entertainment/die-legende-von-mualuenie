package engine;

import javax.swing.*;

public class Window extends JFrame {

    final GamePanel gamePanel;

    public Window() {
        super("Die Legende Von Müaluenie");

        gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }
}