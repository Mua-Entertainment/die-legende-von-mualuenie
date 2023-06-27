// Loui Gabl

package development;

import engine.*;

public class Obstacle extends ImageObject {

    int id = 0;

    protected void load() {
        super.load();
        //Festlegen Bild und Collider
        if (PlayMode.scenes == PlayMode.Scenes.OW) {

            //Overworld Obstacles

            id = (int) (Math.random() * 3);
            if (id >= 2) {
                setSrc("img\\obj\\obstacles\\bush-1.png");
                setGlobalPosition(getParent().getGlobalPosition().x + (float) Math.random() * (getParent().getWidth() - getWidth()), 4f);

                Collider collider = new Collider();
                collider.collide.subscribe(this::onCollide);
                addComponent(collider);
                collider.setPadding(5f / 32f, 19f / 32f, 9f / 32f, 0f);
            }
            if (id == 1) {
                setSrc("img\\obj\\obstacles\\bush-2.png");
                setGlobalPosition(getParent().getGlobalPosition().x + (float) Math.random() * (getParent().getWidth() - getWidth()), 4f);

                Collider collider = new Collider();
                collider.collide.subscribe(this::onCollide);
                addComponent(collider);
                collider.setPadding(4f / 32f, 19f / 32f, 9f / 32f, 0f);
            }
            if (id == 0) {
                setSrc("img\\obj\\obstacles\\tree-1.png");
                setSize(2f, 2f);
                setGlobalPosition(getParent().getGlobalPosition().x + (float) Math.random() * (getParent().getWidth() - getWidth()), 3f);

                Collider collider = new Collider();
                collider.collide.subscribe(this::onCollide);
                addComponent(collider);
                collider.setPadding(10f / 32f, 1f / 32f, 7f / 32f, 1f);
            }
        }
        else {
            //Dark World Obstacles
            setSrc("img\\obj\\obstacles\\spears.png");
            setSize(1f, 2f);
            setGlobalPosition(getParent().getGlobalPosition().x + (float) Math.random() * (getParent().getWidth() - getWidth()), 3f);

            Collider collider = new Collider();
            collider.collide.subscribe(this::onCollide);
            addComponent(collider);
            collider.setPadding(0f, 23f / 32f, 21f / 32f, 0f);
        }


    }

    @Override
    protected void update() {
        super.update();

    }

    private void onCollide(GameObject other, Collision collision) {

        if (other == PlayMode.mua && PlayMode.scenes == PlayMode.Scenes.DW)
        {
            PlayMode.gameOver();
        }
    }
    }