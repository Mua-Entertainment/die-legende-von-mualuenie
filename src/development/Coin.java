package development;

public class Coin extends engine.ImageObject {

    @Override
    protected void load() {
        super.load();

        setSrc("img\\obj\\mua_coin.png");

        setSize(.5f,.5f);
        setGlobalPosition(getParent().getGlobalPosition().x +(float) Math.random() * (getParent().getWidth() - getWidth()), 3f * (float) Math.random());

    }

    @Override
    protected void update() {
        super.update();
    }
}
