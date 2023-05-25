package development;

import engine.Button;
import engine.GameObject;

public class Menu extends GameObject {

    public void createButton(float y, String text, Runnable action) {
        float x = (getCanvasSize().width() - 3/4f) / 2f;
        createButton(x, y, text, action);
    }

    public void createButton(float x, float y, String text, Runnable action) {
        Button button = new Button();
        addChildren(button);

        button.setSize(3/2f, 1/2f);
        button.setSrc("img\\ui\\button.png");
        button.setPosition(x, y);

        button.label.setText(text);
        button.label.setFont(getFont("font\\pixel.ttf"));
        button.click.subscribe(action);
    }
}