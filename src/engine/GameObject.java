package engine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

// Superklasse für alle Objekte auf der Spieloberfläche
public class GameObject {

    // gibt an ob Spiel pausiert ist
    protected static boolean paused;

    // Position im Spielfenster
    private Point position;

    // anzuzeigende Größe
    private Size size;

    // GameObject zu dem dieses Objekt sich relativ bewegt
    private GameObject parent;

    // Position in z-Achse
    private int layer;

    // Referenz auf Game-Panel
    GamePanel panel;

    // Ursprungs-Position des Koordinatensystems
    Point origin;

    // Sichtbarkeit des Objektes
    boolean visible;

    // Objekte, die sich relativ zu diesem Objekt bewegen
    private SafeList<GameObject> children;

    // Komponenten, die diesem Objekt weitere Funktionen verleihen
    private SafeList<Component> components;

    // wird nach das Objekt im Spielfeld hinzugefügt wurde aufgerufen
    // soll den Konstruktor ersetzen, damit zu dem Zeitpunkt die meisten Werte nicht mehr null sind
    protected void load() {
        // setzt Attribute auf Standard-Werte
        position = Point.ZERO;
        size = Size.ONE;
        children = new SafeList<>();
        components = new SafeList<>();
        visible = true;
    }

    // wird jeden Frame aufgerufen
    protected void update() {
        components.forEach(Component::update);
    }

    // zum Zeichnen grafischer Elemente
    void draw(Graphics2D g, int x, int y, int width, int height) { }

    // löscht dieses Objekt aus dem Spielfeld
    public void destroy() {
        destroy(this);
    }

    // löscht mehere Objekte aus dem Spielfeld
    protected void destroy(GameObject... objects) {
        for (GameObject obj : objects) {
            destroy(obj.children.toArray(new GameObject[0]));
            panel.gameObjects.remove(obj);
        }
    }

    // gibt die Frames pPro Sekunde zurück
    protected float getFPS() {
        return panel.fps;
    }

    // gibt eine Klasse mit Eingabe-Events zurück
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

    // Position relativ zum Ursprung
    public Point getGlobalPosition() {
        if (parent == null) {
            return getPosition();
        } else {
            return new Point(getPosition().x + parent.getGlobalPosition().x, getPosition().y + parent.getGlobalPosition().y);
        }
    }

    // setzt Position relativ zum Ursprung
    public void setGlobalPosition(float x, float y) {
        Point pos = new Point(x - parent.getGlobalPosition().x, y - parent.getGlobalPosition().y);
        setPosition(pos);
    }

    // setzt Position relativ zum Ursprung
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

    // bewegt das Objekt
    public void move(float x, float y) {
        setPosition(getPosition().x + x, getPosition().y + y);
    }

    // macht das Objekt und die Children sichtbar
    public void show() {
        visible = true;
        children.forEach(GameObject::show);
    }

    // macht das Objekt und die Children unsichtbar
    public void hide() {
        visible = false;
        children.forEach(GameObject::hide);
    }

    public boolean getVisibility() {
        return visible;
    }

    // fügt GameObjetcts zum Environment-Objekt hinzu
    protected void add(GameObject... objects) {
        panel.environment.addChildren(objects);
    }

    // fügt GameObjetcts als Children dieses Objekts hinzu
    public void addChildren(GameObject... objects) {
        for (GameObject obj : objects) {
            children.add(obj);
            obj.parent = this;
            obj.panel = panel;
            panel.gameObjects.add(obj);
            obj.load();
        }
    }

    // fügt diesem Objekt einen Komponente hinzu
    public void addComponent(Component component) {
        component.owner = this;
        components.add(component);
    }

    // entfernt von diesem Objekt einen Komponente
    public void removeComponent(Component component) {
        components.remove(component);
    }

    // entfernt von diesem Objekt Komponenten mit einem bestimmten Typ
    public void removeComponents(Class<? super Component> type) {
        components.forEach(c -> {
            if (type.isInstance(c)) {
                components.remove(c);
            }
        });
    }

    // Komponenten von diesem Objekt mit einem bestimmten Typ
    public <T extends Component> SafeList<T> getComponents(Class<T> type) {
        SafeList<T> list = new SafeList<>();

        components.forEach(c -> {
            if (type.isInstance(c)) {
                list.add((T) c);
            }
        });

        return list;
    }

    // Spielfeld-Größe
    public Size getCanvasSize() {
        return new Size(panel.settings.xTiles(), panel.settings.yTiles());
    }

    // Setzt den Hintergrund vom Spielfeld
    protected void setCanvasBackground(Color bgColor) {
        panel.setBackground(bgColor);
    }

    // Cursor-Position
    protected Point getCursorPosition() {
        if (origin == null) {
            return Point.ZERO;
        } else {
            var pos = MouseInfo.getPointerInfo().getLocation();

            // wandelt Werte entsprechend der in den Settings angegebenen Einheiten um
            float x = (pos.x - origin.x - panel.window.getX()) * panel.settings.xTiles() / (panel.getWidth() - 2 * origin.x);
            float y = (pos.y - origin.y - panel.window.getY() - panel.window.getInsets().top) * panel.settings.yTiles() / (panel.getHeight() - 2 * origin.y);

            return new Point(x, y);
        }
    }

    protected static Font getFont(String src) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return null;
    }
}