package engine;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final SafeList<GameObject> gameObjects = new SafeList<>();
    InputHandler input = new InputHandler();
    Size canvasSize = Size.ZERO;
    final Settings settings;
    float fps;
    GameObject environment;
    private long lastFrame;
    private String fpsDisplay;

    public GamePanel(Settings settings, GameObject environment) {
        this.settings = settings;
        this.environment = environment;
        this.lastFrame = System.nanoTime();

        setBackground(Color.black);
        setPreferredSize(new Dimension(800, 500));
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(input);
        addMouseListener(input);

        Thread loopThread = new Thread(this);
        loopThread.start();

        Thread fpsThread = new Thread(this::configFPSDisplay);
        fpsThread.start();

        environment.panel = this;
        environment.load();
        gameObjects.add(environment);
    }

    @Override
    public void run() {
        while (true) {
            try {
                update();
                repaint();

                Thread.sleep((int) (1000f / settings.maxFps()));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void configFPSDisplay() {
        while (true) {
            try {
                fpsDisplay = Math.round(fps) + "/" + settings.maxFps() + " FPS";
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void update() {

        // FPS berechnen
        fps = 1_000_000_000f / (System.nanoTime() - lastFrame);
        lastFrame = System.nanoTime();

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

        // Führt für jedes GameObject update() aus
        gameObjects.forEach(GameObject::update);

        // Überprüft Eingaben
        input.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float canvasRatio = (float) settings.xTiles() / settings.yTiles();
        float windowRatio = (float) getWidth() / getHeight();

        g2d.setColor(Color.black);

        Point origin, end;
        int width, height;

        if (windowRatio > canvasRatio) {
            int borderSize = (getWidth() - (int) (getHeight() * canvasRatio)) / 2;
            g2d.fillRect(0, 0, borderSize, getHeight());
            g2d.fillRect(getWidth() - borderSize, 0, borderSize, getHeight());

            origin = new Point(borderSize, 0);
            end = new Point(getWidth() - borderSize, 0);

            canvasSize = new Size(getWidth() - 2 * (int) origin.x, getHeight());
        } else {
            int borderSize = (getHeight() - (int) (getWidth() / canvasRatio)) / 2;
            g2d.fillRect(0, 0, getWidth(), borderSize);
            g2d.fillRect(0, getHeight() - borderSize, getWidth(), borderSize);

            origin = new Point(0, borderSize);
            end = new Point(0, getHeight() - borderSize);

            canvasSize = new Size(getWidth(), getHeight() - 2 * (int) origin.y);
        }

        gameObjects.forEach(obj -> {
            int x = (int) (origin.x + obj.getGlobalPosition().x / settings.xTiles() * canvasSize.width());
            int y = (int) (origin.y + obj.getGlobalPosition().y / settings.yTiles() * canvasSize.height());
            int w = (int) (obj.getSize().width() / settings.xTiles() * canvasSize.width());
            int h = (int) (obj.getSize().height() / settings.yTiles() * canvasSize.height());

            obj.draw(g2d, x, y, w, h);
        });

        g2d.setColor(Color.gray);
        g2d.drawString(fpsDisplay, 0, 10);
    }
}