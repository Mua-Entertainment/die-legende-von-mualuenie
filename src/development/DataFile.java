package development;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.json.*;

public class DataFile {

    private final Path PATH = Path.of("data.json");
    private JSONObject jo;

    public DataFile() {
        try {
            // Erstellt JSON-Datei, falls nicht vorhanden
            if (!Files.exists(PATH)) {
                Files.writeString(PATH, "{\"uuid\":\"\",\"highscore\":0}");
            }

            String json = Files.readString(PATH);
            jo = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gibt die UUID des Nutzers zurück
    public UUID getUUID() {
        try {
            return UUID.fromString(jo.get("uuid").toString());
        } catch (IllegalArgumentException e1) {
            jo.put("uuid", UUID.randomUUID());

            try {
                Files.writeString(PATH, jo.toString());
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            return UUID.fromString(jo.get("uuid").toString());
        }
    }

    public int getHighscore() {
        return (int) jo.get("highscore");
    }

    public void setHighscore(int value) {
        jo.put("highscore", value);

        try {
            Files.writeString(PATH, jo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}