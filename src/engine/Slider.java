package engine;

public class Slider extends ImageObject {

    private final Button button = new Button();

    @Override
    protected void load() {
        super.load();

        addChildren(button);
    }
}