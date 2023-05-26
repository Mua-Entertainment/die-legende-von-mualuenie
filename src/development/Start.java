// Loui Gabl

package development;

import engine.Collider;
import engine.GameObject;
import engine.ImageObject;
import engine.SafeList;

// Teil der Welt der sich horizontal nach links bewegt
public class Start extends ImageObject {

    boolean duplicated = false;

    SafeList<GameObject> obstacles = new SafeList<>();



    @Override
    protected void load() {
        super.load();
        // setzen des Bodens



        setSrc("img\\obj\\world\\dark-chunk\\dark-start.png");
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
            move(-2f / getFPS(), 0);
        }

        //löschen, falls außerhalb des bildschirms
        if (getGlobalPosition().x <= -getWidth()) {
            destroy();
        }

    }

}
