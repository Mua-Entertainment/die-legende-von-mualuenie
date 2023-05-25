package development;

import engine.Collider;
import engine.Collision;
import engine.GameObject;
import engine.ImageObject;
public class Bird extends ImageObject {

    private enum State {
        FLYING, DIVING, DOVE
    }

    private Bird.State state = Bird.State.FLYING;
    protected void load() {
        super.load();
    //Festlegen Bild und Collider
        setSrc("img\\obj\\obstacles\\bird\\bird_fly0.png");
        setGlobalPosition(getCanvasSize().width(), (float) (Math.random() * 2f));

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
    }

    @Override
    protected void update() {
        super.update();

        //Übergang Flug -> Sturzflug
        if (state == State.FLYING && getGlobalPosition().x > 5 && Math.random() > .99)
        {
            state = State.DIVING;
            setSrc("img\\obj\\obstacles\\bird\\bird_dive.png");

        }

        //Sturzflug
        if (state == State.DIVING) move(0f, 4f/getFPS());

        //bewegung
        move(-2f/getFPS(),0f);
    }

    //
    private void onCollide(GameObject other, Collision collision) {
        //Wenn Müa draufspringt => tot
        if (other == PlayMode.mua && PlayMode.mua.getGlobalPosition().y < getGlobalPosition().y)
            {
                destroy();
            }

        // Übergang Sturzflug ->Flug
        if (state == State.DIVING)
        {
            state = State.DOVE;
            setSrc("img\\obj\\obstacles\\bird\\bird_fly0.png");
            setGlobalPosition(getGlobalPosition().x,other.getGlobalPosition().y - getSize().height());
        }

    }
}

//Louis