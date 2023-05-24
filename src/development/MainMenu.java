package development;

import engine.Button;
import engine.GameObject;
import engine.Settings;

public class MainMenu extends GameObject
{
    @Override
    protected void load() {
        super.load();
        Button Startbutton = new Button();
        addChildren(Startbutton);
        Startbutton.setSrc("img\\ui\\button.png");
        Startbutton.setSize(3, 1 );
        Startbutton.label.setText("         Start");
        Startbutton.setPosition(6,4);
        Startbutton.Click.subscribe(this::Spielstarten);

        Button Settingsbutton = new Button();
        addChildren(Settingsbutton);
        Settingsbutton.setSrc("img\\ui\\zahnrader.png");
        Settingsbutton.setSize(1,1);
        Settingsbutton.setPosition(2,4);
        Settingsbutton.Click.subscribe(this::OpenSettings);

        Button Skinbutton = new Button();
        addChildren(Skinbutton);
        Skinbutton.setSrc("img\\obj\\mua\\run\\mua-run-6.png");
        Skinbutton.setPosition(4,4);
        Skinbutton.Click.subscribe(this::SkinMenu);
    }

    @Override
    protected void update() {
        super.update();
    }

    private void Spielstarten()
    {
        PlayMode playMode = new PlayMode();
        add(playMode);
        destroy();
    }
    private void OpenSettings()
    {
        hide();
    }
    private void SkinMenu()
    {

    }
}
