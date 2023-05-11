package engine;

public class GameObject {
    protected static boolean paused;

    private Point position;
    private Size size;
    private GameObject parent;
    private int layer;

    private SafeList<GameObject> children;

    protected void load() {
        position = Point.ZERO;
        size = Size.ONE;
        children = new SafeList<>();
    }

    protected void update() { }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }
}