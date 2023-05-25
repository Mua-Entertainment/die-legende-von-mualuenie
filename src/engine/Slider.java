package engine;

public class Slider extends ImageObject {

    private final Button button = new Button();

    private float cursorOriginX;

    @Override
    protected void load() {
        super.load();

        addChildren(button);
        button.setSize(1/4f, 1/4f);
        button.click.subscribe(this::onButtonClick);

        setSize(3f, 1/4f);
    }

    @Override
    protected void update() {
        super.update();

        if (button.isPressed()) {
            float x = Math.min(getCursorPosition().x - cursorOriginX, getSize().width() - button.getSize().width());
            button.setPosition(x, button.getPosition().y);
        }
    }

    private void onButtonClick() {
        cursorOriginX = getCursorPosition().x;
    }

    public Button getButton() {
        return button;
    }
}