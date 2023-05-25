package development;

import engine.GameObject;

public class SCHNECKE extends GameObject {

    private boolean duplicated;

    @Override
    protected void load() {
        super.load();
    }

    @Override
    protected void update() {
        super.update();

        if (getGlobalPosition().x < -getSize().width())
            destroy();

        if (getGlobalPosition().x <= getCanvasSize().width() && !duplicated) {
            var newChunk = new SCHNECKE();
            add(newChunk);
            newChunk.setGlobalPosition(getGlobalPosition().x + getSize().width(), getGlobalPosition().y);

            duplicated = true;
        }
    }
}