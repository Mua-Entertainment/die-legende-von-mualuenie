// Loui Gabl

package development;

import engine.*;

public class Obstacle extends ImageObject {


    protected void load() {
        super.load();
        //Festlegen Bild und Collider
        setSrc("img\\obj\\obstacles\\spears.png");
        setSize(1f,2f);
        setGlobalPosition(getParent().getGlobalPosition().x +(float) Math.random() * (getParent().getWidth() - getWidth()), 3f);

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
        collider.setPadding(0f,23f/32f,21f/32f,0f);



    }

    @Override
    protected void update() {
        super.update();

    }

    private void onCollide(GameObject other, Collision collision) {

        if (other == PlayMode.mua)
        {
            paused = true;
            System.out.println("game over");
        }
    }
    }