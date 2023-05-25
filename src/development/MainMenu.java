package development;

import engine.Button;
import engine.GameObject;

import java.awt.*;

public class MainMenu extends GameObject
{
    @Override
    protected void load() {
        super.load();

        createButton(2, "Start", this::Spielstarten);
        createButton(2.7f, "Einstellungen", this::OpenSettings);
        createButton(3.4f, "Skins", this::SkinMenu);

        setCanvasBackground(new Color(0x567BB4));
    }

    @Override
    protected void update() {
        super.update();
    }

    private void createButton(float y, String text, Runnable action) {
        Button button = new Button();
        addChildren(button);

        button.setSize(3/2f, 1/2f);
        button.setSrc("img\\ui\\button.png");

        float x = (getCanvasSize().width() - button.getSize().width()) / 2f;
        button.setPosition(x, y);

        button.label.setText(text);
        button.label.setFont(getFont("font\\pixel.ttf"));
        button.click.subscribe(action);
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
        SkinMenu skinMenu = new SkinMenu(this);
        add(skinMenu);
        hide();
    }
}
