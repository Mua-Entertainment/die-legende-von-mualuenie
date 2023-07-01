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

    private Label scoreLabel = new Label();

    public static Scenes scenes = Scenes.DW;


    @Override
    protected void load() {
        super.load();

        instance = this;
        paused = false;
        coins = 0;
        score = 0;

        //Hintergrund
        if (scenes == Scenes.OW) setCanvasBackground(new Color(0xBEF9FF));
        else setCanvasBackground(new Color(0x45476c ));

        // Weltgenerator
        Chunk chunk;
        addChildren(chunk = new Chunk());
        chunk.setGlobalPosition(getCanvasSize().width,5f);

        Start start;
        addChildren(start = new Start());

        //scoreboard
        addChildren(scoreLabel);
        scoreLabel.setGlobalPosition((getCanvasSize().width - getWidth())/2f,0f);
        scoreLabel.setFont(getFont("font\\pixel.ttf").deriveFont(25f));
        scoreLabel.setColor(Color.white);

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

        scoreLabel.setText("Score: " + (int) score);
    }

    public void gameOver(boolean showGameOverScreen, boolean executeAlways)
    {
        if (!paused || executeAlways) {
            if (Program.data.getHighscore() < score) {
                //setzen des Highscores in der Datenbank
                Program.database.setHighscore((int) score, System.nanoTime());

                // lokales Speichern des Highscores
                Program.data.setHighscore((int) score);
            }

            Program.data.addCoins(coins);

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