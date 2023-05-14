package development;

import engine.Collider;
import engine.GameObject;
import engine.ImageObject;
import engine.Label;

import java.awt.*;

public class Environment extends GameObject {

    private Mualuenie mua;

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0xB5DEFF));
        add(mua = new Mualuenie());

        ImageObject schneggn = new ImageObject();
        add(schneggn);
        schneggn.setSrc("schneggn.png");
        schneggn.setGlobalPosition(getCanvasSize().width() - 3, 2);

        Collider collider = new Collider();
        collider.setPadding(0, 0.5f, 0, 0);
        schneggn.addComponent(collider);

        Label label = new Label();
        add(label);
        label.setColor(Color.red);
        label.setText("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
}