package development;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import engine.SafeList;
import org.json.*;

public class DataFile {

    private final int SEED = 2;
    private final Path PATH = Path.of("player.dat");
    private JSONObject jo;

    public DataFile() {
        try {
            boolean loaded;
            String json = "{\"uid\":\"" + generateUID() + "\",\"highscore\":0,\"date\":0,\"skin\":\"DEFAULT\",\"music\":true,\"sfx\":true,\"coins\":0,\"skins\":[\"DEFAULT\"],\"name\":\"\",\"fps\":100}";

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
                } else if (!(jo.has("uid") && jo.has("highscore") && jo.has("date") && jo.has("skin") && jo.has("music") && jo.has("sfx") && jo.has("coins") && jo.has("skins") && jo.has("name") && jo.has("fps"))) {
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
    }

    // gibt die UUID des Nutzers zurück
    public String getUID() {
        try {
            return jo.getString("uid");
        } catch (IllegalArgumentException e1) {
            jo.put("uuid", generateUID());
            write();

            return jo.getString("uid");
        }
    }

    public int getHighscore() {
        return jo.getInt("highscore");
    }

    public void setHighscore(int value) {
        jo.put("highscore", value);
        write();
    }

    public long getDate() {
        return jo.getLong("date");
    }

    public void setDate(long date) {
        jo.put("date", date);
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

    public String getName() {
        return jo.getString("name");
    }

    public void setName(String name) {
        jo.put("name", name);
        write();
    }

    private String generateUID() {
        String result = String.valueOf(System.nanoTime());
        String ran = String.valueOf((int) (Math.random() * 100000));

        for (int i = 0; i < 5 - ran.length(); i++) {
            result += '0';
        }

        return result + ran;
    }

    public int getMaxFPS() {
        return jo.getInt("fps");
    }

    public void setMaxFPS(int fps) {
        jo.put("fps", fps);
        write();
    }
}