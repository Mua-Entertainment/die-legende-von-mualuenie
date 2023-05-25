// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.GameObject;
import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    public static Mualuenie mua;

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0xBEF9FF));

        // Weltgenerator
        Chunk chunk;
        addChildren(chunk = new Chunk());
        chunk.setGlobalPosition(getCanvasSize().width,5f);

        Chunk start;
        addChildren(start = new Chunk());
        start.duplicated = true;
        start.setSize(getCanvasSize().width, 2f);
        start.setGlobalPosition(0,5f);

        // Müaluenie
        mua = new Mualuenie();
        add(mua);
    }
}

//Louis