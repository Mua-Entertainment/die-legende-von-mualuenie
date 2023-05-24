package development;

import engine.Button;
import engine.GameObject;

public class MainMenu extends GameObject
{
    @Override
    protected void load() {
        super.load();
        Button Startbutton = new Button();
        addChildren(Startbutton);
        Startbutton.setSrc("button.png");
        Startbutton.setSize(3, 1 );
        Startbutton.label.setText("         Start");
        Startbutton.setPosition(6,4);
        Startbutton.Click.subscribe(this::Spielstarten);

        Button Settingsbutton = new Button();
        addChildren(Settingsbutton);
        Settingsbutton.setSrc("zahnrader.png");
        Settingsbutton.setSize(1,1);
        Settingsbutton.setPosition(2,4);
        Settingsbutton.Click.subscribe(this::OpenSettings);

        Button Skinbutton = new Button();
        addChildren(Skinbutton);
        Skinbutton.setSrc("mua5.png");
        Skinbutton.setPosition(4,2);
        Skinbutton.Click.subscribe(this::SkinMenu);
    }

    @Override
    protected void update() {
        super.update();
    }

    private void Spielstarten()
    {
        move(5, 2);
    }
    private void OpenSettings()
    {

    }
    private void SkinMenu()
    {

    }
}
