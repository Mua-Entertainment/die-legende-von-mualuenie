// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.*;

import java.awt.event.KeyEvent;

// GameObject in dem alle Objekte sich befinden
public class Environment extends GameObject {

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


        label.setGlobalPosition(7, 4);
        label.setText(String.valueOf(getPosition().y));
    }
}