package development;

import engine.Button;
import engine.GameObject;

import java.awt.*;

public class MainMenu extends GameObject
{
    @Override
    protected void load() {
        super.load();

        // Button, der Spiel startet
        Button Startbutton = new Button();
        addChildren(Startbutton);
        Startbutton.setSrc("img\\ui\\button.png");
        Startbutton.setSize(3, 1 );
        Startbutton.label.setText("         Start");
        Startbutton.setPosition(6,4);
        Startbutton.click.subscribe(this::Spielstarten);

        // Button, der Einstellungsmenü öffnet
        Button Settingsbutton = new Button();
        addChildren(Settingsbutton);
        Settingsbutton.setSrc("img\\ui\\zahnrader.png");
        Settingsbutton.setSize(1,1);
        Settingsbutton.setPosition(2,4);
        Settingsbutton.click.subscribe(this::OpenSettings);

        // Button, der Skin-Menü öffnet
        Button Skinbutton = new Button();
        addChildren(Skinbutton);
        Skinbutton.setSrc("img\\obj\\mua\\run\\mua-run-6.png");
        Skinbutton.setPosition(4,4);
        Skinbutton.click.subscribe(this::SkinMenu);

        setCanvasBackground(new Color(0x567BB4));
    }

    @Override
    protected void update() {
        super.update();
    }

    // startet Spiel
    private void Spielstarten()
    {
        destroy();
        PlayMode playMode = new PlayMode();
        add(playMode);
    }

    // öffnet Einstellungen
    private void OpenSettings()
    {
        SettingScreen settingScreen = new SettingScreen(this);
        add(settingScreen);
        hide();
    }

    // öffnet Skin-Menü
    private void SkinMenu()
    {

    }
}
