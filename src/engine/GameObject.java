package engine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class GameObject {

    protected static boolean paused;

    private Point position;
    private Size size;
    private GameObject parent;
    private int layer;
    GamePanel panel;
    Point origin;
    boolean visible;

    private SafeList<GameObject> children;
    private SafeList<Component> components;

    protected void load() {
        position = Point.ZERO;
        size = Size.ONE;
        children = new SafeList<>();
        components = new SafeList<>();
        visible = true;
    }

    protected void update() {
        components.forEach(Component::update);
    }

    void draw(Graphics2D g, int x, int y, int width, int height) { }

    public void destroy() {
        destroy(this);
    }

    protected void destroy(GameObject... objects) {
        for (GameObject obj : objects) {
            destroy(obj.children.toArray(new GameObject[0]));
            panel.gameObjects.remove(obj);
        }
    }

    protected float getFPS() {
        return panel.fps;
    }

    protected InputHandler getInput() {
        return panel.input;
    }

    public void setPosition(float x, float y) {
        setPosition(new Point(x, y));
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public Point getGlobalPosition() {
        if (parent == null) {
            return getPosition();
        } else {
            return new Point(getPosition().x + parent.getGlobalPosition().x, getPosition().y + parent.getGlobalPosition().y);
        }
    }

    public void setGlobalPosition(float x, float y) {
        Point pos = new Point(x - parent.getGlobalPosition().x, y - parent.getGlobalPosition().y);
        setPosition(pos);
    }

    public void setGlobalPosition(Point position) {
        setGlobalPosition(position.x, position.y);
    }

    public void setSize(float width, float height) {
        setSize(new Size(width, height));
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
        setGlobalPosition(getGlobalPosition());
    }

    public GameObject getParent() {
        return parent;
    }

    public SafeList<GameObject> getChildren() {
        return (SafeList<GameObject>) children.clone();
    }

    public void move(float x, float y) {
        setPosition(getPosition().x + x, getPosition().y + y);
    }

    public void show() {
        visible = true;
        children.forEach(GameObject::show);
    }

    public void hide() {
        visible = false;
        children.forEach(GameObject::hide);
    }

    public boolean getVisibility() {
        return visible;
    }

    protected void add(GameObject... objects) {
        panel.environment.addChildren(objects);
    }

    public void addChildren(GameObject... objects) {
        for (GameObject obj : objects) {
            children.add(obj);
            obj.parent = this;
            obj.panel = panel;
            panel.gameObjects.add(obj);
            obj.load();
        }
    }

    public void addComponent(Component component) {
        component.owner = this;
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public void removeComponents(Class<? super Component> type) {
        components.forEach(c -> {
            if (type.isInstance(c)) {
                components.remove(c);
            }
        });
    }

    public <T extends Component> SafeList<T> getComponents(Class<T> type) {
        SafeList<T> list = new SafeList<>();

        components.forEach(c -> {
            if (type.isInstance(c)) {
                list.add((T) c);
            }
        });

        return list;
    }

    public Size getCanvasSize() {
        return new Size(panel.settings.xTiles(), panel.settings.yTiles());
    }

    protected void setCanvasBackground(Color bgColor) {
        panel.setBackground(bgColor);
    }

    protected Point getCursorPosition() {
        if (origin == null) {
            return Point.ZERO;
        } else {
            var pos = MouseInfo.getPointerInfo().getLocation();

            float x = (pos.x - origin.x - panel.window.getX()) * panel.settings.xTiles() / (panel.getWidth() - 2 * origin.x);
            float y = (pos.y - origin.y - panel.window.getY() - panel.window.getInsets().top) * panel.settings.yTiles() / (panel.getHeight() - 2 * origin.y);

            return new Point(x, y);
        }
    }
}