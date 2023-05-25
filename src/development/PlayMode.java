package development;

import engine.GameObject;
import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0xBEF9FF));

        // Weltgenerator
        WorldGen wg = new WorldGen();
        add(wg);

        // Müaluenie
        Mualuenie mua = new Mualuenie();
        add(mua);
    }
}