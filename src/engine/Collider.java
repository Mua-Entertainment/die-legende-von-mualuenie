package engine;

// zum Überprüfen ob Objekt zusammenstoßen
public class Collider extends Component{

    // Rand-Verkleinerung des Colliders
    private Padding padding = Padding.ZERO;

    // Zusammenstoß-Ereignis
    public final BiEvent<GameObject, Collision> collide = new BiEvent<>();

    @Override
    void update() {
        if (padding != null) {
            SafeList<GameObject> list = new SafeList<>();
            list.addAll(owner.panel.gameObjects);

            list.forEach(obj -> {
                if (obj != owner) {
                    obj.getComponents(Collider.class).forEach(c2 -> {
                        var pad = this.padding;
                        var pad2 = c2.padding;

                        // prüft auf Kollision
                        if (
                            owner.getGlobalPosition().x + pad.left() <= obj.getGlobalPosition().x + pad2.left() + obj.getSize().width - pad2.right() &&
                            owner.getGlobalPosition().x + pad.left() + owner.getSize().width - pad.right() >= obj.getGlobalPosition().x + pad2.left() &&
                            owner.getGlobalPosition().y + pad.top() <= obj.getGlobalPosition().y + pad2.top() + obj.getSize().height - pad2.bottom() &&
                            owner.getGlobalPosition().y + pad.top() + owner.getSize().height - pad.bottom() >= obj.getGlobalPosition().y + pad2.top()
                        ) {
                            var hDis = owner.getGlobalPosition().x + pad.left() + owner.getSize().width - pad.right() - obj.getGlobalPosition().x + pad2.left();
                            var hDis2 = obj.getGlobalPosition().x + pad2.left() + obj.getSize().width - pad2.right() - owner.getGlobalPosition().x + pad.left();

                            var vDis = owner.getGlobalPosition().y + pad.top() + owner.getSize().height - pad.bottom() - obj.getGlobalPosition().y + pad2.top();
                            var vDis2 = obj.getGlobalPosition().y + pad2.top() + obj.getSize().height - pad2.bottom() - owner.getGlobalPosition().y + pad.top();

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
    }

    public void setPadding(float left, float top, float right, float bottom) {
        padding = new Padding(left, top, right, bottom);
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }
}