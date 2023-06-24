// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.*;

import java.awt.event.KeyEvent;

// GameObject in dem alle Objekte sich befinden
public class Environment extends GameObject {

    private final float ACCELERATION = 20f, JUMP_SPEED = 20f, GROUND_LEVEL = 5f;
    private float currentJumpSpeed = 0;
    private Label label;

    @Override
    protected void load() {
        super.load();

        // Anzeigen von Hauptmenü
        MainMenu mainMenu = new MainMenu();
        add(mainMenu);

        label = new Label();
        add(label);
        label.setPosition(7, 5);
    }


    @Override
    protected void update() {
        super.update();

        if (getPosition().y < GROUND_LEVEL || currentJumpSpeed > 0) {
            move(0, -currentJumpSpeed / getFPS());
            currentJumpSpeed -= ACCELERATION / getFPS();
        } else {
            setY(GROUND_LEVEL);
            currentJumpSpeed = getInput().keyDown(KeyEvent.VK_SPACE) ? JUMP_SPEED : 0;
        }

        label.setGlobalPosition(7, 4);
        label.setText(String.valueOf(getPosition().y));
    }
}