// Loui Gabl

package development;

import engine.*;

public class ObstacleBird extends ImageObject {

    private enum State {
        FLYING, DIVING, DOVE
    }

    private ObstacleBird.State state = ObstacleBird.State.FLYING;

    private Animator animator;

    private  final AnimationFrame[] fly = new AnimationFrame[]{
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\obstacles\\bird\\bird-fly-1.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\obstacles\\bird\\bird-fly-2.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\obstacles\\bird\\bird-fly-3.png"))
    };
    private  final AnimationFrame[] dive = new AnimationFrame[]{
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\obstacles\\bird\\bird-dive.png"))
    };
    protected void load() {
        super.load();
    //Festlegen Bild und Collider
        setGlobalPosition(12.5f, (float) (Math.random() * 2f));

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
        collider.setPadding(2f/32f,13f/32f,0f,2f/32f);

        animator = new Animator();
        addComponent(animator);
        animator.setFrames(fly);
    }

    @Override
    protected void update() {
        super.update();

        if(!paused) {
            //Übergang Flug -> Sturzflug
            if (state == State.FLYING && getGlobalPosition().x > 5 && Math.random() > .99) {
                state = State.DIVING;
                animator.setFrames(dive);

            }

            //Sturzflug
            if (state == State.DIVING) move(0f, 4f / getFPS());

            //bewegung
            move(-2f / getFPS(), 0f);
        }
    }

    //
    private void onCollide(GameObject other, Collision collision) {
        //Wenn Müa draufspringt => tot
        if (other == PlayMode.mua && PlayMode.mua.getGlobalPosition().y < getGlobalPosition().y + getSize().height)
            {
                destroy();
            }
        //else if (other == PlayMode.mua) paused=true;

        // muas postion < my position + getSize

        // Übergang Sturzflug ->Flug
        if (state == State.DIVING)
        {
            state = State.DOVE;
            animator.setFrames(fly);
            setGlobalPosition(getGlobalPosition().x,other.getGlobalPosition().y - getHeight());
        }

    }
}