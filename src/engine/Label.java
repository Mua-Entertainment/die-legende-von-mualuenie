// Simo Münc

package engine;

import java.awt.*;

// grafische Darstellung von Text
public class Label extends GameObject {

    // darzustellender Text
    private String text;

    // Farbe des Textes
    private Color color;

    // Schriftart
    private Font font;

    @Override
    protected void load() {
        super.load();

        // Standardwerte
        text = "";
        color = Color.black;
        font = panel.getFont();
    }

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        // zentriert Text in der Mitte des Objektes mit folgenden Berechnungen
        FontMetrics metrics = g.getFontMetrics(font);

        int textX  = x + width / 2 - metrics.stringWidth(text) / 2;
        int textY = (int) (y + height / 2 - metrics.getHeight() / 2 + metrics.getAscent() * 1.22f);

        float fontSize = (float) (font.getSize() * Math.pow(panel.canvasSize.height / 500f, 0.94f));

        // zeichnet Text
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