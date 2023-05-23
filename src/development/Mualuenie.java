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


        //Übergang Springen -> Fallen
        if (state==State.JUMP && airtime > JUMPFORCE)
        {
            state=State.AIR;
        }

        //Sprung
        if (state==State.JUMP && airtime <= JUMPFORCE)
        {
            move(0, -SPEED / getFPS());
            System.out.println(airtime);
        }

        //Springen Initial
        if (getInput().keyPressed(KeyEvent.VK_SPACE) && state==State.GROUND) {
            state = State.JUMP;
            move(0, -SPEED / getFPS());
        }

        //Schwerkraft
        move (0, GRAVITY * airtime / getFPS());
        airtime = airtime + 1f/getFPS();

        // fürs testen (will remove later)
        if (getInput().keyPressed(KeyEvent.VK_W)) move(0, -SPEED / (airtime*getFPS()));
        if (getInput().keyPressed(KeyEvent.VK_S)) move(0, SPEED / getFPS());
        if (getInput().keyPressed(KeyEvent.VK_A)) move(-SPEED / getFPS(), 0);
        if (getInput().keyPressed(KeyEvent.VK_D)) move(SPEED / getFPS(), 0);
    }

    private void onCollide(GameObject other, Collision collision) {
        System.out.println(other);
        airtime = 0;
        state=State.GROUND;

    }
}