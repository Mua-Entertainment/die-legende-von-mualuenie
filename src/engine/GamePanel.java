package engine;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final Settings settings;

    public GamePanel(Settings settings) {
        this.settings = settings;

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