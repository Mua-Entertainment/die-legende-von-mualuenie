package development;

import engine.*;

import java.awt.event.KeyEvent;

public class Mualuenie extends ImageObject {

    private final float SPEED = 2f;
    private final float GRAVITY = 1f;
    private float airtime = 0;

    @Override
    protected void load() {
        super.load();

        setSrc("mua.png");
        setGlobalPosition(1, getCanvasSize().height() - 2);

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
    }

    @Override
    protected void update() {
        super.update();

        if (getInput().keyPressed(KeyEvent.VK_W)) move(0, -SPEED / getFPS());
        if (getInput().keyPressed(KeyEvent.VK_S)) move(0, SPEED / getFPS());
        if (getInput().keyPressed(KeyEvent.VK_A)) move(-SPEED / getFPS(), 0);
        if (getInput().keyPressed(KeyEvent.VK_D)) move(SPEED / getFPS(), 0);
        if (getInput().keyPressed(KeyEvent.VK_SPACE)) move(0, -SPEED / getFPS());
        move (0, GRAVITY * airtime / getFPS());
        airtime = airtime + 1f/getFPS();

    }

    private void onCollide(GameObject other, Collision collision) {
        System.out.println(other);
        airtime = 0;
    }
}