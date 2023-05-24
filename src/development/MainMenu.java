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
        Startbutton.label.setText("Start");
        Startbutton.setPosition(2,2);
        Startbutton.Click.subscribe(this::Spielstarten);

    }
    private void Spielstarten()
    {
        move(5, 2 );
    }
}
