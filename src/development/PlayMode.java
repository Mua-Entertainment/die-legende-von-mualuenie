// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.GameObject;
import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    public static Mualuenie mua;

    public static int coins;

    public static float score;


    @Override
    protected void load() {
        super.load();

        //Hintergrund
        setCanvasBackground(new Color(0xBEF9FF));

        // Weltgenerator
        Chunk chunk;
        addChildren(chunk = new Chunk());
        chunk.setGlobalPosition(getCanvasSize().width,5f);

        Start start;
        addChildren(start = new Start());


        // Müaluenie
        mua = new Mualuenie();
        add(mua);
    }

    @Override
    protected void update()
    {
        super.update();

        if(!paused) score += 100f / getFPS();


    }

}
