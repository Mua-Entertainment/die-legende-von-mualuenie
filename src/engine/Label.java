package engine;

import java.awt.*;

public class Label extends GameObject {

    private String text;
    private Color color;
    private Font font;

    @Override
    protected void load() {
        super.load();

        text = "";
        color = Color.black;
        font = panel.getFont();
    }

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        var metrics = g.getFontMetrics(font);

        var textX  = (int) (x + width / 2 - metrics.stringWidth(text) / 2);
        var textY = (int) (y + height / 2 - metrics.getHeight() / 2 + metrics.getAscent() * 1.22f);

        float fontSize = (float) (font.getSize() * Math.pow(panel.canvasSize.height() / 500f, 0.94f));

        g.setFont(font.deriveFont(fontSize));
        g.setColor(color);
        g.drawString(text, textX, textY);
        g.setFont(panel.getFont());
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}