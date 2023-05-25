package development;

import engine.Collider;
import engine.ImageObject;
// Teil der Welt der sich horizontal nach links bewegt
public class Chunk extends ImageObject {

    boolean duplicated = false;

    @Override
    protected void load() {
        super.load();

        setSrc("img\\obj\\world\\dark-chunk\\dark-chunk.png");
        setGlobalPosition(12.5f, 5f);
        setSize(2.5f, 2f);

        Collider collider = new Collider();
        addComponent(collider);
    }

    @Override
    protected void update() {
        super.update();
        move(-2f / getFPS(), 0);

        if (getGlobalPosition().x <= -getSize().width()) {
            destroy();
        }

        if (!duplicated && getGlobalPosition().x <= getCanvasSize().width() - getSize().width())
        {
            Chunk chunk;
            addChildren(chunk= new Chunk());
            chunk.setGlobalPosition(getGlobalPosition().x + getSize().width(),5f);
            duplicated = true;
        }

    }

}
