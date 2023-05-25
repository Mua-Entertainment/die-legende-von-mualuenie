package development;

import engine.GameObject;
import engine.Slider;

public class Environment extends GameObject {

    @Override
    protected void load() {
        super.load();

        MainMenu mainMenu = new MainMenu();
        add(mainMenu);

        Slider slider = new Slider();
        add(slider);
        slider.setSrc("img\\ui\\slider.png");
        slider.getButton().setSrc("img\\ui\\slider-button.png");
    }
}