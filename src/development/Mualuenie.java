// Loui Gabl

package development;

import engine.*;

import java.awt.event.KeyEvent;

public class Mualuenie extends ImageObject {

    private enum State {
        GROUND, JUMP, AIR
    }
    private final float SPEED = 10f;
    private final float GRAVITY = 10f;
    private final float JUMPFORCE = 7f;

    private float currentJumpSpeed = 0f;

    private float airtime = 0;
    private State state = State.GROUND;

    private Animator animator;

    private float score = 0f;

    private int coins = 0;

    private  final AnimationFrame[] run = new AnimationFrame[]{
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-1.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-2.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-3.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-4.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-5.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-6.png"))
    };
    private  final AnimationFrame[] jump = new AnimationFrame[]{
            new AnimationFrame(.05f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-1.png")),
            new AnimationFrame(.05f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-2.png")),
            new AnimationFrame(.05f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-3.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-4.png"))
    };
    private final AnimationFrame[] air = new AnimationFrame[]{
            new AnimationFrame(.1f, () -> setSrc("img\\obj\\mua\\jump\\mua-jump-4.png"))
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

            if (getInput().keyPressed(KeyEvent.VK_D) && getGlobalPosition().x < getCanvasSize().width - getWidth())
            {
                move(5 / getFPS(),0);
            }
            if (getInput().keyPressed(KeyEvent.VK_A) && getGlobalPosition().x > 0f)
            {
                move(-5 / getFPS(),0);
            }

            if (getInput().keyPressed(KeyEvent.VK_ESCAPE)) paused = true;
            //pausenmenü


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

    public void jump()
    {
        state = State.JUMP;
        currentJumpSpeed = -JUMPFORCE;
        animator.setFrames(jump);
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
