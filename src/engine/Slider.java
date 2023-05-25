package engine;

public class Slider extends ImageObject {

    // Button der zum Verschieben gedrückt wird
    private final Button button = new Button();

    private float cursorOriginX, buttonOriginX;

    @Override
    protected void load() {
        super.load();

        // fügt Button hinzu
        addChildren(button);
        button.setSize(1/4f, 1/4f);
        button.click.subscribe(this::onButtonClick);
        button.setLayer(getLayer() + 1);

        setSize(3f, 1/4f);
    }

    @Override
    protected void update() {
        super.update();

        // passt die Button-Position der Cursor-Position an
        if (button.isPressed()) {
            float x = buttonOriginX + getCursorPosition().x - cursorOriginX;
            float max = getSize().width() - button.getSize().width();

            if (x > max) {
                x = max;
            } else if (x < 0) {
                x = 0;
            }

            button.setPosition(x, button.getPosition().y);
        }
    }

    private void onButtonClick() {
        cursorOriginX = getCursorPosition().x;
        buttonOriginX = button.getPosition().x;
    }

    public Button getButton() {
        return button;
    }

    // brechnet Wert zwischen 0 und 1
    public float getValue() {
        return button.getPosition().x / (getSize().width() - button.getSize().width());
    }
}