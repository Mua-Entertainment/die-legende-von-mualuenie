package development;

import engine.GameObject;

public class PlayMode extends GameObject {

    @Override
    protected void load() {
        super.load();

        WorldGen wg = new WorldGen();
        add(wg);

        Mualuenie mua = new Mualuenie();
        add(mua);
    }
}