// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.Button;
import engine.GameObject;
import engine.Label;

import java.awt.*;

// Objekt, indem die "Action" abläuft
public class PlayMode extends GameObject {

    private static PlayMode instance;
    public static Scenes scenes = Scenes.DW;

    public enum Scenes {
        OW, DW
    }

    public static Mualuenie mua;
    public static int coins;
    public static float score;
    private Label scoreLabel = new Label();
    private Button pauseButton;


    @Override
    protected void load() {
        super.load();

        instance = this;
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

        pauseButton = createButton(this, "Pause", this::pause, getCanvasSize().width - 2, 0.5f);
    }

    @Override
    protected void update()
    {
        super.update();

        //erhöhung des scores
        score += 100f / getFPS();

        scoreLabel.setText("Score: " + (int) score);
    }

    public void gameOver(boolean showGameOverScreen, boolean executeAlways)
    {
        if (getActive() || executeAlways) {
            if (DataFile.getHighscore() < score) {
                //setzen des Highscores in der Datenbank
                Database.setHighscore((int) score, System.nanoTime());

                // lokales Speichern des Highscores
                DataFile.setHighscore((int) score);
            }

            DataFile.addCoins(coins);

            if (showGameOverScreen) {
                add(new GameOverScreen());
                setActive(false);
            } else {
                add(new MainMenu());
                destroy();
            }
        }
    }

    private void pause() {
        add(new PauseScreen());
        setActive(false);
    }

    public static PlayMode getInstance() {
        return instance;
    }
}