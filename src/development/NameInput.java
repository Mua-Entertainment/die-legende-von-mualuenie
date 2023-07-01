package development;

import engine.GameObject;
import engine.Label;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class NameInput extends GameObject {

    private Label nameLabel;
    private final GameObject objToReturn;

    public NameInput(GameObject objToReturn) {
        this.objToReturn = objToReturn;
    }

    @Override
    protected void load() {
        super.load();

        setCanvasBackground(new Color(0x7996BE));

        createMenuLabel(this, "Gib deinen Namen ein.", 0.5f);

        nameLabel = createMenuLabel(this, DataFile.getName(), 2);
        nameLabel.setFont(nameLabel.getFont().deriveFont(30f));

        createMenuButton(this, "Weiter", this::exit, 4);
    }

    @Override
    protected void update() {
        super.update();

        if (getInput().anyKeyDown()) {
            for (int key : getInput().getPressedKeys()) {
                if (Character.isAlphabetic((char) key) || Character.isDigit((char) key)) {
                    char c = (char) key;

                    if (!getInput().getPressedKeys().contains(KeyEvent.VK_SHIFT)) {
                        c = Character.toLowerCase(c);
                    }

                    nameLabel.setText(nameLabel.getText() + c);
                } else if (key == KeyEvent.VK_BACK_SPACE && nameLabel.getText().length() > 0) {
                    nameLabel.setText(nameLabel.getText().substring(0, nameLabel.getText().length() - 1));
                }
            }
        }
    }

    private void exit() {
        DataFile.setName(nameLabel.getText());
        async(() -> Database.setName(nameLabel.getText()));
        add(objToReturn);
        destroy();
    }
}