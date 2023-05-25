package development;

import engine.Collider;
import engine.GameObject;
import engine.ImageObject;
import engine.SafeList;

// Teil der Welt der sich horizontal nach links bewegt
public class Chunk extends ImageObject {

    boolean duplicated = false;

    SafeList<GameObject> obstacles = new SafeList<>();



    @Override
    protected void load() {
        super.load();
        //zufälliges setzen des bildes, colliders


        if (Math.random() < .9) {
            setSrc("img\\obj\\world\\dark-chunk\\dark-chunk.png");
            setGlobalPosition(12.5f, 5f);
            setSize(2.5f, 2f);
            Collider collider = new Collider();
            addComponent(collider);
        }

        setGlobalPosition(12.5f, 5f);
        setSize(2.5f, 2f);



        Bird bird= new Bird();
        obstacles.add(bird);

        if (Math.random() > .3)
        {
            addChildren(obstacles.get((int) (Math.random() * obstacles.size())));
        }




    }

    @Override
    protected void update() {
        super.update();
        //bewegung der welt
        move(-2f / getFPS(), 0);

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getWidth()) {
            destroy();
        }

        //generieren neuer chunks, wenn vollkommen im bildschirm
        if (!duplicated && getGlobalPosition().x <= getCanvasSize().width - getWidth())
        {
            Chunk chunk;
            getParent().addChildren(chunk= new Chunk());
            chunk.setGlobalPosition(getGlobalPosition().x + getWidth(), 5f);
            duplicated = true;
        }

    }

}

//Louis