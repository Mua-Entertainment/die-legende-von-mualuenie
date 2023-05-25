package development;

import engine.Collider;
import engine.ImageObject;
// Teil der Welt der sich horizontal nach links bewegt
public class Chunk extends ImageObject {

    boolean duplicated = false;

    @Override
    protected void load() {
        super.load();
        //setzen des bildes, colliders
        if (Math.random() < .9) {
            setSrc("img\\obj\\world\\dark-chunk\\dark-chunk.png");
            setGlobalPosition(12.5f, 5f);
            setSize(2.5f, 2f);
            Collider collider = new Collider();
            addComponent(collider);
        }
        setGlobalPosition(12.5f, 5f);
        setSize(2.5f, 2f);


    }

    @Override
    protected void update() {
        super.update();
        //bewegung der welt
        move(-2f / getFPS(), 0);

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getSize().width()) {
            destroy();
        }

        //generieren neuer chunks, wenn vollkommen im bildschirm
        if (!duplicated && getGlobalPosition().x <= getCanvasSize().width() - getSize().width())
        {
            Chunk chunk;
            getParent().addChildren(chunk= new Chunk());
            chunk.setGlobalPosition(getGlobalPosition().x + getSize().width(),5f);
            duplicated = true;
        }

    }

}
