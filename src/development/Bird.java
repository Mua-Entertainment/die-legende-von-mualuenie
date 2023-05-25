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

        setSrc("img\\obj\\obstacles\\bird\\bird_fly0.png");
        setGlobalPosition(getCanvasSize().width(), (float) (Math.random() * 2f));

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
    }

    @Override
    protected void update() {
        super.update();

        if (state == State.FLYING && getGlobalPosition().x > 5 && Math.random() > .99)
        {
            state = State.DIVING;
            setSrc("img\\obj\\obstacles\\bird\\bird_dive.png");

        }

        if (state == State.DIVING)
        {
            move(0f, 4f/getFPS());

            if (getGlobalPosition().y > 4f)
            {
                setGlobalPosition(getGlobalPosition().x,4f);
                state = State.DOVE;
                setSrc("img\\obj\\obstacles\\bird\\bird_fly0.png");

            }
        }

        move(-2f/getFPS(),0f);
    }

    private void onCollide(GameObject other, Collision collision) {

        {
            //die
        }
    }
}

//Louis