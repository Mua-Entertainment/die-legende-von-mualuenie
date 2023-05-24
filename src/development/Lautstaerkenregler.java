package development;

import engine.Button;
import engine.GameObject;

public class Lautstaerkenregler extends GameObject {
    @Override
    protected void load() {
        super.load();
        Button Startbutton = new Button();

        Startbutton.setSrc("img\\ui\\button.png");
        Startbutton.setSize(3, 1 );
        Startbutton.label.setText("         Start");
        Startbutton.setPosition(6,4);
        Startbutton.click.subscribe(this::setVolume);
        addChildren(Startbutton);
    }

    private void setVolume() {
        setLautstaerke(10);
    }

    private  SettingScreen settingScreen;
    public Lautstaerkenregler(SettingScreen settingScreen)
    {
        this.settingScreen = settingScreen;

    }

    private int lautstaerke;

    public Lautstaerkenregler() {
        lautstaerke = 0; // Standardmäßig auf 0 setzen
    }

    public void setLautstaerke(int neueLautstaerke) {
        lautstaerke = neueLautstaerke;
    }

    public int getLautstaerke() {
        return lautstaerke;
    }

}
