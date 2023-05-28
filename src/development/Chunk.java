// Loui Gabl

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
        //zufälliges setzen des Bodens


        if (Math.random() < .7) {
            if (Math.random() < .5) {
                setSrc("img\\obj\\world\\dark-chunk\\dark-chunk.png");
                setGlobalPosition(12.5f, 5f);
                setSize(2.5f, 2f);
                Collider collider = new Collider();
                addComponent(collider);
            }
            else {
                setSrc("img\\obj\\world\\dark-chunk\\dark-clock-tower.png");
                setGlobalPosition(12.5f, 1.5f);
                setSize(2f, 5.5f);
                Collider colliderRoof = new Collider();
                addComponent(colliderRoof);
                colliderRoof.setPadding(8f/64f*2f,10f/176f*5.5f,8f/64f*2f,144f/176f*5.5f);
                Collider colliderFloor = new Collider();
                addComponent(colliderFloor);
                colliderFloor.setPadding(0f,112f/176f*5.5f,0f,0f);
            }

        }
        else {
            setSrc("img\\obj\\world\\dark-chunk\\dark-chunk-hole.png");
            setGlobalPosition(12.5f, 5f);
            setSize(2.5f, 2f);

            Collider colliderL = new Collider();
            addComponent(colliderL);
            colliderL.setPadding(0f, 0f,63f/80f*2.5f, 58/64f*2f);
            Collider colliderR = new Collider();
            addComponent(colliderR);
            colliderR.setPadding(63f/80f*2.5f, 0f,0f, 58/64f*2f);
        }

        //Zufällige Hindernisse (Momentan nur Vogel)
        Bird bird= new Bird();
        obstacles.add(bird);
        Obstacle obstacle = new Obstacle();
        obstacles.add(obstacle);

        if (Math.random() > .3)
        {
            addChildren(obstacles.get((int) (Math.random() * obstacles.size())));
        }




    }

    @Override
    protected void update() {
        super.update();
        if(!paused) {
            //bewegung der welt
            move(-2f / getFPS(), 0);
        }

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getWidth()) {
            destroy();
        }

        //generieren neuer chunks, wenn vollkommen im bildschirm
        if (!duplicated && getGlobalPosition().x <= getCanvasSize().width - getWidth())
        {
            Chunk chunk;
            getParent().addChildren(chunk= new Chunk());
            chunk.setGlobalPosition(getGlobalPosition().x + getWidth(), chunk.getY());
            duplicated = true;
        }

    }

}
