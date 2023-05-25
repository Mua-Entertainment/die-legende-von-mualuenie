package development;

import engine.GameObject;
import engine.Slider;

public class Environment extends GameObject {

    @Override
    protected void load() {
        super.load();

        MainMenu mainMenu = new MainMenu();
        add(mainMenu);
    }
}