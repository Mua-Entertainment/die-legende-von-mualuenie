package development;

import engine.GameObject;
import engine.RectObject;

//Generiert die Welt
public class WorldGen extends GameObject {

    private Chunk chunk;

    private Chunk start;


    @Override
    protected void load() {
        super.load();

        addChildren(chunk = new Chunk());
        chunk.setGlobalPosition(getCanvasSize().width(),5f);

        addChildren(start = new Chunk());
        start.duplicated = true;
        start.setSize(getCanvasSize().width(), 2f);
        start.setGlobalPosition(0,5f);

    }

    @Override
    protected void update() {
        super.update();


    }

}

//Louis