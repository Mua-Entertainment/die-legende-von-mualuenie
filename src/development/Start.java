// Loui Gabl

package development;

import engine.*;

// Teil der Welt der sich horizontal nach links bewegt
public class Start extends ImageObject {

    boolean duplicated = false;

    SafeList<GameObject> obstacles = new SafeList<>();

    //setzen der Animation Frames für den Boden
    private  final AnimationFrame[] dw = new AnimationFrame[]{
            new AnimationFrame(.3f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-1.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-2.png")),
            new AnimationFrame(.2f, () -> setSrc("img\\obj\\world\\dark-chunk\\dark-start-3.png"))
    };
    private  final AnimationFrame[] ow = new AnimationFrame[]{
            new AnimationFrame(.3f, () -> setSrc("img\\obj\\world\\ow\\ow-start.png"))
    };

    @Override
    protected void load() {
        super.load();

        // setzen des Bodens Oberwelt oder Dark World
        Animator animator = new Animator();
        addComponent(animator);
        if (PlayMode.scenes == PlayMode.Scenes.OW) {
            animator.setFrames(ow);
        } else {
            animator.setFrames(dw);
        }
        ;

        Collider collider = new Collider();
        addComponent(collider);
        setGlobalPosition(0f, 5f);
        setSize(12.5f, 2f);





    }

    @Override
    protected void update() {
        super.update();

        //bewegung der welt
        move((-2 - 0.0001f * PlayMode.score)/ getFPS(), 0);

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getWidth()) {
            destroy();
        }

    }

}
