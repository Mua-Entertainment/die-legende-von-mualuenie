package engine;

public class Button extends ImageObject {

    private enum State {
        DEFAULT, HOVER, CLICK
    }

    private Label label = new Label();
    private State state;

    @Override
    protected void load() {
        super.load();
    }

    @Override
    protected void update() {
        super.update();
    }
}