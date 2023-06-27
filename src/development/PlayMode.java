// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.GameObject;
import engine.Label;

import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    public enum Scenes {
        OW, DW
    }

    public static Mualuenie mua;

    public static int coins;

    public static float score;

    private Label scoreUI = new Label();

    public static Scenes scenes = Scenes.OW;


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

        //scoreboard
        addChildren(scoreUI);
        scoreUI.setGlobalPosition((getCanvasSize().width-getWidth())/2f,0f);
        scoreUI.setFont(getFont("font\\pixel.ttf").deriveFont(25f));
        scoreUI.setColor(Color.black);

        // Müaluenie
        mua = new Mualuenie();
        add(mua);



    }

    @Override
    protected void update()
    {
        super.update();

        if(!paused) score += 100f / getFPS();

        scoreUI.setText("score: " + (int) score );

    }

    public static void gameOver()
    {
        if (Program.database.getHighscore() < score) Program.database.setHighscore((int) score);
        paused = true;
    }

}
