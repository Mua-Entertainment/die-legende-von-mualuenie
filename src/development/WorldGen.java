package development;

import engine.GameObject;
import engine.RectObject;

//Generiert die Welt
public class WorldGen extends GameObject {

    private Chunk chunk;

    private RectObject rect;


    @Override
    protected void load() {
        super.load();

        addChildren(chunk = new Chunk());
        chunk.setGlobalPosition(getCanvasSize().width(),5f);

        addChildren(rect = new RectObject());
        //rect.

    }

    @Override
    protected void update() {
        super.update();


    }

}
