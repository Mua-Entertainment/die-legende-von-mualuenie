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
    private final float JUMPFORCE = .2f;

    private float airtime = 0;
    private State state = State.GROUND;

    private Animator animator;

    private  final AnimationFrame[] run = new AnimationFrame[]{
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-1.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-2.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-3.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-4.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-5.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\run\\mua-run-6.png"))
    };
    private  final AnimationFrame[] jump = new AnimationFrame[]{
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-1.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-2.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-3.png")),
            new AnimationFrame(.1f,() -> setSrc("img\\obj\\mua\\jump\\mua-jump-4.png"))
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
            //Übergang Boden -> Fallen (wip)

            //Übergang Springen -> Fallen
            if (state == State.JUMP && airtime > JUMPFORCE) {
                state = State.AIR;
                //setSrc("img\\obj\\mua\\jump\\mua-jump-4.png");
            }

            //Sprung
            if (state == State.JUMP && airtime <= JUMPFORCE) {
                move(0, -SPEED / getFPS());
            }

            //Springen Initial
            if (getInput().keyPressed(KeyEvent.VK_SPACE) && state == State.GROUND) {
                state = State.JUMP;
                move(0, -SPEED / getFPS());
                animator.setFrames(jump);
            }

            //Schwerkraft
            move(0, GRAVITY * airtime / getFPS());
            airtime = airtime + 1f / getFPS();

            //aus der welt fallen
            if (getGlobalPosition().y > 5) {
                //game over :)
                //paused = true;
            }
        }
        /* fürs testen (will remove later)
        if (getInput().keyPressed(KeyEvent.VK_W)) move(0, -SPEED / (airtime*getFPS()));
        if (getInput().keyPressed(KeyEvent.VK_S)) move(0, SPEED / getFPS());
        if (getInput().keyPressed(KeyEvent.VK_A)) move(-SPEED / getFPS(), 0);
        if (getInput().keyPressed(KeyEvent.VK_D)) move(SPEED / getFPS(), 0);

         */
    }

    //Kollidieren mit Boden
    private void onCollide(GameObject other, Collision collision) {
        if(!paused) {
            airtime = 0;
            state = State.GROUND;
            animator.setFrames(run);
            setGlobalPosition(getGlobalPosition().x, other.getGlobalPosition().y - getSize().height + 9f / 32f);
        }
    }
}
