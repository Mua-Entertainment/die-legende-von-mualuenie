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
    private AnimationFrame[] run = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-1.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-2.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-4.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-5.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-6.png"))
    };
    private AnimationFrame[] jump = new AnimationFrame[]{
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-1.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-2.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\jump-4.png"))
    };
    private AnimationFrame[] air = new AnimationFrame[]{
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



    @Override
    protected void load() {
        super.load();

        // lädt Wert aus JSON-Datei, damit dies wöhrend dem Spiel nicht wiederholt werden muss
        yihaaEnabled = Program.data.getSFXEnabled();

        //setzen des colliders
        setGlobalPosition(1, getCanvasSize().height - 2);

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
        collider.setPadding(1f/32f,9f/32f,2f/32f,9f/32f);

        animator = new Animator();
        addComponent(animator);

        //setze des skins

        skin = Program.data.getMuaSkin();

        if (skin == Skin.KNIGHT) air[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\jump-4.png"));
        if (skin == Skin.KNIGHT) {
            jump[0] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-1.png"));
            jump[1] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-2.png"));
            jump[2] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\knight\\jump-3.png"));
            jump[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\jump-4.png"));}
        if (skin == Skin.KNIGHT) {
            run[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-1.png"));
            run[1] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-2.png"));
            run[2] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-3.png"));
            run[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-4.png"));
            run[4] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-5.png"));
            run[5] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\knight\\run-6.png"));}

        if (skin == Skin.PIRATE) air[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\jump-4.png"));
        if (skin == Skin.PIRATE) {
            jump[0] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\pirate\\jump-1.png"));
            jump[1] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\pirate\\jump-2.png"));
            jump[2] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\pirate\\jump-3.png"));
            jump[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\jump-4.png"));}
        if (skin == Skin.PIRATE) {
            run[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-1.png"));
            run[1] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-2.png"));
            run[2] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-3.png"));
            run[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-4.png"));
            run[4] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-5.png"));
            run[5] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\pirate\\run-6.png"));}

        if (skin == Skin.MARTIAN) air[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\jump-4.png"));
        if (skin == Skin.MARTIAN) {
            jump[0] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\martian\\jump-1.png"));
            jump[1] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\martian\\jump-2.png"));
            jump[2] = new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\martian\\jump-3.png"));
            jump[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\jump-4.png"));}
        if (skin == Skin.MARTIAN) {
            run[0] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-1.png"));
            run[1] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-2.png"));
            run[2] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-3.png"));
            run[3] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-4.png"));
            run[4] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-5.png"));
            run[5] = new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\martian\\run-6.png"));}

        animator.setFrames(run);
    }

    @Override
    protected void update() {
        super.update();
        if(!paused) {
            //Übergang Boden -> Fallen
            if (state == State.GROUND && airtime > 5f / getFPS()) {
                state = State.AIR;
                currentJumpSpeed = 0;
                animator.setFrames(air);
            }

            //Übergang Springen -> Fallen
            if (state == State.JUMP && airtime > .3f) {
                state = State.AIR;
                animator.setFrames(air);
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
                paused = true;
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
    }

    //springen
    public void jump() {
        state = State.JUMP;
        currentJumpSpeed = -JUMPFORCE;


        animator.setFrames(jump);


        if (yihaaEnabled && Math.random() < YIHAA_PROBABILITY) {
            new WaveAudio("audio\\yihaa.wav").play(false);
        }
    }


    //Kollidieren mit Boden
    private void onCollide(GameObject other, Collision collision) {
        if(!paused) {
            airtime = 0;
            state = State.GROUND;


            animator.setFrames(run);


            setGlobalPosition(getGlobalPosition().x, other.getGlobalPosition().y + other.getComponents(Collider.class).get(0).getPadding().top() - getSize().height + 9f / 32f);
        }
    }
}
