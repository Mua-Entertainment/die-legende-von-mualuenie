package development;

import engine.GameObject;

// GameObject in dem alle Objekte sich befinden
public class Environment extends GameObject {

    @Override
    protected void load() {
        super.load();

        // Anzeigen von Hauptmenü
        MainMenu mainMenu = new MainMenu();
        add(mainMenu);
    }
}