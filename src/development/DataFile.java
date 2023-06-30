package development;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import engine.SafeList;
import org.json.*;

public class DataFile {

    private final int SEED = 2;
    private final Path PATH = Path.of("player.dat");
    private JSONObject jo;

    public DataFile() {
        try {
            boolean loaded;
            String json = "{\"uuid\":\"\",\"highscore\":0,\"skin\":\"DEFAULT\",\"music\":true,\"sfx\":true,\"coins\":0,\"skins\":[]}";

            do {
                // Erstellt JSON-Datei, falls nicht vorhanden
                if (!Files.exists(PATH)) {
                    Files.writeString(PATH, decode(json, SEED));
                    loaded = false;
                } else if (jo == null) {
                    try {
                        jo = new JSONObject(decode(Files.readString(PATH), -SEED));
                    } catch (JSONException e) {
                        Files.writeString(PATH, decode(json, SEED));
                    }

                    loaded = false;
                } else if (!(jo.has("uuid") && jo.has("highscore") && jo.has("skin") && jo.has("music") && jo.has("sfx") && jo.has("coins") && jo.has("skins"))) {
                    Files.writeString(PATH, decode(json, SEED));
                    jo = null;
                    loaded = false;
                } else {
                    loaded = true;
                }
            } while (!loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // verschlüsselt Text
    private String decode(String text, int seed) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            result.append((char) ((byte) c + seed));
        }

        return result.toString();
    }

    private void write() {
        try {
            Files.writeString(PATH, decode(jo.toString(), SEED));
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        System.out.println(jo.toString());
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

    public int getCoins() {
        return jo.getInt("coins");
    }

    public void addCoins(int coins) {
        jo.put("coins", getCoins() + coins);
        write();
    }

    public SafeList<Skin> getUnlockedSkins() {
        SafeList<Skin> result = new SafeList<>();

        jo.getJSONArray("skins").forEach(s -> {
            result.add(Skin.valueOf(s.toString()));
        });

        return result;
    }

    public void unlockSkin(Skin skin) {
        jo.getJSONArray("skins").put(skin.name());
    }
}