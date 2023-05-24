package development;

import engine.GameObject;

import java.awt.*;

public class PlayMode extends GameObject {

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0xBEF9FF));

        WorldGen wg = new WorldGen();
        add(wg);

        Mualuenie mua = new Mualuenie();
        add(mua);
    }
}