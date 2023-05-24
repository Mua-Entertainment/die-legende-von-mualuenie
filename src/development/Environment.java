package development;

import engine.GameObject;

public class Environment extends GameObject {

    @Override
    protected void load() {
        super.load();

        MainMenu mainMenu = new MainMenu();
        add(mainMenu);
    }
}