package engine;

import javax.swing.*;
import java.awt.*;

// Panel auf das die Spielelemente gezeichnet werden
public class GamePanel extends JPanel implements Runnable {

    // enthält alle anzuzeigenden GameObjects
    final SafeList<GameObject> gameObjects = new SafeList<>();

    // Input-Event-Handler zum Lauschen auf Eingabe-Events
    InputHandler input = new InputHandler();

    // Größe der Fläche, auf der das Spiel abgebildet wird
    Size canvasSize = Size.ZERO;

    // Einstellungen für die Koordinateneinheiten und Größen
    final Settings settings;

    // Referenz zum Fenster
    final Window window;

    // Frames pro Sekunde
    float fps;

    // Umgebung - das Objekt, dass alle anderen Objekte enthält
    GameObject environment;

    // letzter Frame in Nanosekunden
    private long lastFrame;

    // FPS-Anzeige
    private String fpsDisplay;

    public GamePanel(Settings settings, GameObject environment, Window window) {

        // legt Attribute Fest
        this.settings = settings;
        this.environment = environment;
        this.window = window;
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
        int width, height, borderSize;

        Point space1Pos, space2Pos;
        Size space1Size, space2Size;

        if (windowRatio > canvasRatio) {
            borderSize = (getWidth() - (int) (getHeight() * canvasRatio)) / 2;

            origin = new Point(borderSize, 0);
            end = new Point(getWidth() - borderSize, 0);

            canvasSize = new Size(getWidth() - 2 * (int) origin.x, getHeight());
        } else {
            borderSize = (getHeight() - (int) (getWidth() / canvasRatio)) / 2;

            origin = new Point(0, borderSize);
            end = new Point(0, getHeight() - borderSize);

            canvasSize = new Size(getWidth(), getHeight() - 2 * (int) origin.y);
        }

        gameObjects.forEach(obj -> {
            if (obj.visible) {
                int x = (int) (origin.x + obj.getGlobalPosition().x / settings.xTiles() * canvasSize.width());
                int y = (int) (origin.y + obj.getGlobalPosition().y / settings.yTiles() * canvasSize.height());
                int w = (int) (obj.getSize().width() / settings.xTiles() * canvasSize.width());
                int h = (int) (obj.getSize().height() / settings.yTiles() * canvasSize.height());

                obj.origin = origin;
                obj.draw(g2d, x, y, w, h);
            }
        });

        if (windowRatio > canvasRatio) {
            g2d.fillRect(0, 0, borderSize, getHeight());
            g2d.fillRect(getWidth() - borderSize, 0, borderSize, getHeight());
        } else {
            g2d.fillRect(0, 0, getWidth(), borderSize);
            g2d.fillRect(0, getHeight() - borderSize, getWidth(), borderSize);
        }

        g2d.setColor(Color.gray);
        g2d.drawString(fpsDisplay, 0, 10);
    }
}