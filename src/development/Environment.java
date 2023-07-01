// Loui Gabl, Joha Zwin, Simo Münc

package development;

import engine.*;

// GameObject in dem alle Objekte sich befinden
public class Environment extends GameObject {

    @Override
    protected void load() {
        super.load();

        if (DataFile.getName().equals("")) {
            // Anzeigen von Namen-Eingabe
            add(new NameInput(new MainMenu()));
        } else {
            // Anzeigen von Hauptmenü
            add(new MainMenu());
        }
    }
}