package development;

import engine.Collider;
import engine.Collision;
import engine.GameObject;

public class Coin extends engine.ImageObject {
    float timer  = 0f;
    boolean movingUp = true;

    @Override
    protected void load() {
        super.load();

        setSrc("img\\obj\\mua_coin.png");

        setSize(.5f,.5f);
        setGlobalPosition(getParent().getGlobalPosition().x +(float) Math.random() * (getParent().getWidth() - getWidth()), 4f * (float) Math.random());

        Collider collider = new Collider();
        collider.collide.subscribe(this::onCollide);
        addComponent(collider);
    }

    @Override
    protected void update() {
        super.update();
        if (movingUp) move(0,-.5f/getFPS());
        if (!movingUp) move(0,.5f/getFPS());
        timer += 1f / getFPS();
        if (timer > .5f) {
            timer = 0;
            movingUp = !movingUp;
        }
    }

    private void onCollide(GameObject other, Collision collision) {
        PlayMode.coins += 1;
        PlayMode.score += 100;
        System.out.println(PlayMode.coins);
        destroy();
    }
}
