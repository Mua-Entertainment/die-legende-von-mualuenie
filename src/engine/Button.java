package engine;

public class Button extends ImageObject {
    public final Label label = new Label();
    private boolean hover, click;

    @Override
    protected void load() {
        super.load();

        label.setSize(this.getSize());
        addChildren(label);
    }

    @Override
    protected void update() {
        super.update();

        
    }
}