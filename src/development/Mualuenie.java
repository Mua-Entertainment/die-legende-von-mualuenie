// Loui Gabl

package development;

import engine.*;

import java.awt.event.KeyEvent;

public class Mualuenie extends ImageObject {

    private enum State {
        GROUND, JUMP, AIR
    }

    private final float GRAVITY = 10f;
    private final float JUMPFORCE = 7f;
    private final float YIHAA_PROBABILITY = 0.2f;
    private float currentJumpSpeed = 0f;
    private float airtime = 0;
    private float score = 0f;
    private int coins = 0;
    private State state = State.GROUND;
    private Skin skin;
    private Animator animator;
    private boolean yihaaEnabled;



    //setzen der Animationen
    private final AnimationFrame[] runDefault = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-1.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-2.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-4.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-5.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-6.png"))
    };
    private final AnimationFrame[] jumpDefault = new AnimationFrame[]{
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-1.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-2.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\jump-4.png"))
    };
    private final AnimationFrame[] airDefault = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\jump-4.png"))
    };

    private final AnimationFrame[] runKnight = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-1.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-2.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-4.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-5.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-6.png"))
    };
    private final AnimationFrame[] jumpKnight = new AnimationFrame[]{
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-1.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-2.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\jump-4.png"))
    };
    private final AnimationFrame[] airKnight = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\jump-4.png"))
    };

    private final AnimationFrame[] jumpKebab = new AnimationFrame[] {

    };

    @Override
    protected void load() {
        super.load();

        // lädt Wert aus JSON-Datei, damit dies wöhrend dem Spiel nicht wiederholt werden muss
        yihaaEnabled = DataFile.getSFXEnabled();

        //setzen des colliders
        setGlobalPosition(1, getCanvasSize().height - 2);

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
        collider.setPadding(1f/32f,9f/32f,2f/32f,9f/32f);

        animator = new Animator();
        addComponent(animator);

        skin = DataFile.getMuaSkin();

        switch (skin) {
            case DEFAULT -> animator.setFrames(runDefault);
            case KNIGHT -> animator.setFrames(runKnight);
        }
    }

    @Override
    protected void update() {
        super.update();

        //Übergang Boden -> Fallen
        if (state == State.GROUND && airtime > 5f / getFPS()) {
            state = State.AIR;
            currentJumpSpeed = 0;

            switch (skin) {
                case DEFAULT -> animator.setFrames(airDefault);
                case KNIGHT -> animator.setFrames(airKnight);
            }
        }

        //Übergang Springen -> Fallen
        if (state == State.JUMP && airtime > .3f) {
            state = State.AIR;

            switch (skin) {
                case DEFAULT -> animator.setFrames(airDefault);
                case KNIGHT -> animator.setFrames(airKnight);
            }
        }


        //Springen
        if (getInput().keyPressed(KeyEvent.VK_SPACE) && state == State.GROUND) {
            jump();
        }
        //Horizontale Bewegung
        if (getInput().keyPressed(KeyEvent.VK_D) && getGlobalPosition().x < getCanvasSize().width - getWidth()) {
            move(5 / getFPS(),0);
        }
        if (getInput().keyPressed(KeyEvent.VK_A) && getGlobalPosition().x > 0f) {
            move(-5 / getFPS(),0);
        }

        //pausenmenü
        if (getInput().keyPressed(KeyEvent.VK_ESCAPE)) {
            add(new PauseScreen());
        }

        if (state != State.GROUND) {
            //Schwerkraft
            move(0, currentJumpSpeed / getFPS());
            currentJumpSpeed += GRAVITY / getFPS();
        }

        airtime = airtime + 1f / getFPS();

        //aus der welt fallen
        if (getGlobalPosition().y > 5) {
            PlayMode.getInstance().gameOver(true, false);
        }
    }

    //springen
    public void jump() {
        state = State.JUMP;
        currentJumpSpeed = -JUMPFORCE;

        switch (skin) {
            case DEFAULT -> animator.setFrames(jumpDefault);
            case KNIGHT -> animator.setFrames(jumpKnight);
        }

        if (yihaaEnabled && Math.random() < YIHAA_PROBABILITY) {
            new WaveAudio("audio\\yihaa.wav").play(false);
        }
    }


    //Kollidieren mit Boden
    private void onCollide(GameObject other, Collision collision) {
        airtime = 0;
        state = State.GROUND;

        switch (skin) {
            case DEFAULT -> animator.setFrames(runDefault);
            case KNIGHT -> animator.setFrames(runKnight);
        }

        setGlobalPosition(getGlobalPosition().x, other.getGlobalPosition().y + other.getComponents(Collider.class).get(0).getPadding().top() - getSize().height + 9f / 32f);
    }
}
