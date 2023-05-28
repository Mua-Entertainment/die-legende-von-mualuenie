// Simo Münc

package engine;

// zum Überprüfen ob Objekt zusammenstoßen
public class Collider extends Component{

    private Padding padding = Padding.ZERO;

    // Zusammenstoß-Ereignis
    public final BiEvent<GameObject, Collision> collide = new BiEvent<>();

    @Override
    void update() {
        SafeList<GameObject> list = new SafeList<>();
        list.addAll(owner.panel.gameObjects);

        list.forEach(obj -> {
            if (obj != owner) {
                obj.getComponents(Collider.class).forEach(c2 -> {
                    // prüft auf Kollision
                    if (
                        getPosition().x <= c2.getPosition().x + c2.getSize().width &&
                        getPosition().x + getSize().width >= c2.getPosition().x &&
                        getPosition().y <= c2.getPosition().y + c2.getSize().height &&
                        getPosition().y + getSize().height >= c2.getPosition().y
                    ) {
                        var hDis = getPosition().x + getSize().width - c2.getPosition().x;
                        var hDis2 = c2.getPosition().x + c2.getSize().width - getPosition().x;

                        var vDis = getPosition().y + getSize().height - c2.getPosition().y;
                        var vDis2 = c2.getPosition().y + c2.getSize().height - getPosition().y;

                        // berechnet ob die Collider vertikal oder horizontal zusammenstoßen
                        var type = hDis > vDis && hDis >= 0 ? Collision.VERTICAL : Collision.HORIZONTAL;
                        var type2 = hDis2 > vDis2 && hDis2 >= 0 ? Collision.VERTICAL : Collision.HORIZONTAL;

                        collide.invoke(obj, type);
                        c2.collide.invoke(owner, type2);
                    }
                });
            }
        });
    }

    private Point getPosition() {
        return new Point(
            owner.getGlobalPosition().x - padding.left(),
            owner.getGlobalPosition().y - padding.top()
        );
    }

    private Size getSize() {
        return new Size(
            owner.getSize().width - padding.left() - padding.right(),
            owner.getSize().height - padding.top() - padding.bottom()
        );
    }

    public void setPadding(float left, float top, float right, float bottom) {
        setPadding(new Padding(left, top, right, bottom));
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public Padding getPadding() {
        return padding;
    }
}