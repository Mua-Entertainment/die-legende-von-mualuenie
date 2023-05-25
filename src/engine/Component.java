// Simo Münc

package engine;

// stellt eine Komponente eines GameObject dar, die dem Objekt Funktionen verleiht
public abstract class Component {

    // das GameObject dem die Component gehört
    GameObject owner;

    // wird jeden Frame aufgerufen
    abstract void update();
}