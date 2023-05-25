package development;

import engine.Collider;
import engine.ImageObject;

// Teil der Welt der sich horizontal nach links bewegt
public class Chunk extends ImageObject {

    @Override
    protected void load() {
        super.load();

        setSrc("img\\obj\\world\\dark-chunk\\dark-chunk.png");
        setGlobalPosition(0,  5);
        setSize(2.5f,2f);

        Collider collider = new Collider();
        addComponent(collider);
    }
    @Override
    protected void update() {
        super.update();
        move(-2f/getFPS(),0);
    }

    private void randomize()
    {
        //zufällig unterschiedliche arten von böden, hindernissen
    }

}
