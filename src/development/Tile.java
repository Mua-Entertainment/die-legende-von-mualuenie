package development;

import engine.Collider;
import engine.ImageObject;

public class Tile extends ImageObject {


    @Override
    protected void load() {
        super.load();

        setSrc("dark_tile.png");
        setGlobalPosition(0,  5);
        setSize(1f,1f);

        Collider collider = new Collider();
        addComponent(collider);
    }
    @Override
    protected void update() {
        super.update();
        move(-2f/getFPS(),0);
    }
}
