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
                Files.writeString(PATH, "{\"uuid\":\"\",\"highscore\":0,\"skin\":\"DEFAULT\",\"music\":true,\"sfx\":true}");
            }

            String json = Files.readString(PATH);
            jo = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        try {
            Files.writeString(PATH, jo.toString());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    // gibt die UUID des Nutzers zurück
    public UUID getUUID() {
        try {
            return UUID.fromString(jo.get("uuid").toString());
        } catch (IllegalArgumentException e1) {
            jo.put("uuid", UUID.randomUUID());
            write();

            return UUID.fromString(jo.get("uuid").toString());
        }
    }

    public int getHighscore() {
        return jo.getInt("highscore");
    }

    public void setHighscore(int value) {
        jo.put("highscore", value);
        write();
    }

    public Skin getMuaSkin() {
        return Skin.valueOf(jo.get("skin").toString());
    }

    public void setMuaSkin(Skin skin) {
        jo.put("skin", skin);
        write();
    }

    public boolean getMusicEnabled() {
        return jo.getBoolean("music");
    }

    public void setMusicEnabled(boolean enabled) {
        jo.put("music", enabled);
        write();
    }

    public boolean getSFXEnabled() {
        return jo.getBoolean("sfx");
    }

    public void setSFXEnabled(boolean enabled) {
        jo.put("sfx", enabled);
        write();
    }
}