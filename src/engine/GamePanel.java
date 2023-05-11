package engine;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final Settings settings;
    final SafeList<GameObject> gameObjects;
    float fps;
    private long lastFrame;

    public GamePanel(Settings settings) {
        this.settings = settings;
        this.gameObjects = new SafeList<>();
        this.lastFrame = System.nanoTime();

        setMinimumSize(new Dimension(300, 200));
        setBackground(Color.black);

        Thread loopThread = new Thread(this);
        loopThread.start();
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();
        }
    }

    private void update() {
        // Bubble-Sort nach Attribut GameObject.layer
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = 0; j < gameObjects.size() - 1; j++) {
                if (gameObjects.get(j).getLayer() < gameObjects.get(j + 1).getLayer()) {
                    GameObject temp = gameObjects.get(j);
                    gameObjects.set(j, gameObjects.get(j + 1));
                    gameObjects.set(j + 1, temp);
                }
            }
        }

        gameObjects.forEach(GameObject::update);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float canvasRatio = (float) settings.xTiles() / settings.yTiles();
        float windowRatio = (float) getWidth() / getHeight();

        g2d.setColor(Color.black);

        if (windowRatio > canvasRatio) {
            int borderSize = (getWidth() - (int) (getHeight() * canvasRatio)) / 2;
            g2d.fillRect(0, 0, borderSize, getHeight());
            g2d.fillRect(getWidth() - borderSize, 0, borderSize, getHeight());
        } else {
            int borderSize = (getHeight() - (int) (getWidth() / canvasRatio)) / 2;
            g2d.fillRect(0, 0, getWidth(), borderSize);
            g2d.fillRect(0, getHeight() - borderSize, getWidth(), borderSize);
        }
    }
}