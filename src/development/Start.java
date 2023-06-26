// Loui Gabl

package development;

import engine.*;

// Teil der Welt der sich horizontal nach links bewegt
public class Start extends ImageObject {

    boolean duplicated = false;

    SafeList<GameObject> obstacles = new SafeList<>();

    private  final AnimationFrame[] idle = new AnimationFrame[]{
            new AnimationFrame(.3f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-1.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-2.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-3.png"))
    };

    @Override
    protected void load() {
        super.load();
        // setzen des Bodens


        Animator animator = new Animator();
        addComponent(animator);
        animator.setFrames(idle);

        Collider collider = new Collider();
        addComponent(collider);
        setGlobalPosition(0f, 5f);
        setSize(12.5f, 2f);





    }

    @Override
    protected void update() {
        super.update();
        if(!paused) {
            //bewegung der welt
            move((-2 - 0.0001f * PlayMode.score)/ getFPS(), 0);
        }

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getWidth()) {
            destroy();
        }

    }

}
