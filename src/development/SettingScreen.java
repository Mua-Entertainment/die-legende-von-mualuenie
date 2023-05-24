package development;

import engine.Button;
import engine.GameObject;

public class SettingScreen extends GameObject
{
    @Override
    protected void load() {
        super.load();
        Button Back =new Button();
        addChildren(Back);
        Back.setSrc("pfeil.png");
    }

}
