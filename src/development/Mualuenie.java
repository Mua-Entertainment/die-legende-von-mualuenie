// Loui Gabl

package development;

import engine.*;

import java.awt.event.KeyEvent;

public class Mualuenie extends ImageObject {


    public enum Skin {
        DEFAULT, KNIGHT
    }

    private enum State {
        GROUND, JUMP, AIR
    }

    private final float GRAVITY = 10f;
    private final float JUMPFORCE = 7f;

    private float currentJumpSpeed = 0f;

    private float airtime = 0;

    private float score = 0f;

    private int coins = 0;
    private State state = State.GROUND;

    private Skin skin;

    private Animator animator;


    //setzen der Animationen
    private final AnimationFrame[] run = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-1.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-2.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-4.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-5.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\run-6.png"))
    };
    private final AnimationFrame[] jump = new AnimationFrame[]{
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-1.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-2.png")),
            new AnimationFrame(.05f, () -> setSrc("img\\obj\\mua\\default\\jump-3.png")),
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\default\\jump-4.png"))
    };
    private final AnimationFrame[] air = new AnimationFrame[]{
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

        //setzen des colliders
        setGlobalPosition(1, getCanvasSize().height - 2);

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
        collider.setPadding(1f/32f,9f/32f,2f/32f,9f/32f);

        animator = new Animator();
        addComponent(animator);

        skin = Program.data.getMuaSkin();

        if (skin == Skin.DEFAULT) animator.setFrames(run);
        if (skin == Skin.KNIGHT) animator.setFrames(runKnight);
    }

    @Override
    protected void update() {
        super.update();
        if(!paused) {
            //Übergang Boden -> Fallen
            if (state == State.GROUND && airtime > 5f / getFPS()) {
                state = State.AIR;
                currentJumpSpeed = 0;
                if (skin == Skin.DEFAULT) animator.setFrames(air);
                if (skin == Skin.KNIGHT) animator.setFrames(airKnight);
            }

            //Übergang Springen -> Fallen
            if (state == State.JUMP && airtime > .3f) {
                state = State.AIR;
                if (skin == Skin.DEFAULT) animator.setFrames(air);
                if (skin == Skin.KNIGHT) animator.setFrames(airKnight);
            }


            //Springen
            if (getInput().keyPressed(KeyEvent.VK_SPACE) && state == State.GROUND) {
                jump();
            }
            //Horizontale Bewegung
            if (getInput().keyPressed(KeyEvent.VK_D) && getGlobalPosition().x < getCanvasSize().width - getWidth())
            {
                move(5 / getFPS(),0);
            }
            if (getInput().keyPressed(KeyEvent.VK_A) && getGlobalPosition().x > 0f)
            {
                move(-5 / getFPS(),0);
            }

            //pausenmenü
            if (getInput().keyPressed(KeyEvent.VK_ESCAPE)) paused = true;



            if (state != State.GROUND) {
                //Schwerkraft
                move(0, currentJumpSpeed / getFPS());
                currentJumpSpeed += GRAVITY / getFPS();

            }
            airtime = airtime + 1f / getFPS();

            //aus der welt fallen
            if (getGlobalPosition().y > 5) {
                PlayMode.gameOver();
            }
        }
    }

    //springen
    public void jump() {
        state = State.JUMP;
        currentJumpSpeed = -JUMPFORCE;
        if (skin == Skin.DEFAULT) animator.setFrames(jump);
        if (skin == Skin.KNIGHT) animator.setFrames(jumpKnight);
    }

    //setzen des Skins
    public void setSkin ()
    {

    }


    //Kollidieren mit Boden
    private void onCollide(GameObject other, Collision collision) {
        if(!paused) {
            airtime = 0;
            state = State.GROUND;
            if (skin == Skin.DEFAULT) animator.setFrames(run);
            if (skin == Skin.KNIGHT) animator.setFrames(runKnight);
            setGlobalPosition(getGlobalPosition().x, other.getGlobalPosition().y + other.getComponents(Collider.class).get(0).getPadding().top() - getSize().height + 9f / 32f);
        }
    }
}
