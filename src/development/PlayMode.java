// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.GameObject;
import engine.Label;

import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    private static PlayMode instance;

    public enum Scenes {
        OW, DW
    }

    public static Mualuenie mua;

    public static int coins;

    public static float score;

    private Label scoreUI = new Label();

    public static Scenes scenes = Scenes.DW;


    @Override
    protected void load() {
        super.load();

        instance = this;
        paused = false;

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
        scoreUI.setGlobalPosition((getCanvasSize().width - getWidth())/2f,0f);
        scoreUI.setFont(getFont("font\\pixel.ttf").deriveFont(25f));
        scoreUI.setColor(Color.black);

        // Müaluenie
        mua = new Mualuenie();
        addChildren(mua);

        createButton(this, "Pause", this::pause, getCanvasSize().width - 2, 0.5f);
    }

    @Override
    protected void update()
    {
        super.update();

        //erhöhung des scores
        if(!paused) score += 100f / getFPS();

        scoreUI.setText("score: " + (int) score );

    }

    public void gameOver(boolean showGameOverScreen, boolean executeAlways)
    {
        if (!paused || executeAlways) {
            //setzen des Highscores in der Datenbank
            if (Program.database.getHighscore() < score) {
                Program.database.setHighscore((int) score);
            }

            if (showGameOverScreen) {
                add(new GameOverScreen());
            } else {
                add(new MainMenu());
                destroy();
            }
        }

        paused = true;
    }

    private void pause() {
        paused = true;
        add(new PauseScreen());
    }

    public static PlayMode getInstance() {
        return instance;
    }
}