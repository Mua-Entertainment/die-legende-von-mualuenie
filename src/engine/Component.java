package engine;

public abstract class Component {

    GameObject owner;

    abstract void update();
}