package development;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import engine.SafeList;
import org.json.*;

public final class DataFile {

    private static final int SEED = 2;
    private static final Path PATH = Path.of("player.dat");
    private static JSONObject jo;

    public static void update() {
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
    private static String decode(String text, int seed) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            result.append((char) ((byte) c + seed));
        }

        return result.toString();
    }

    private static void write() {
        try {
            Files.writeString(PATH, decode(jo.toString(), SEED));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    // gibt die UUID des Nutzers zurück
    public static String getUID() {
        try {
            return jo.getString("uid");
        } catch (IllegalArgumentException e1) {
            jo.put("uuid", generateUID());
            write();

            return jo.getString("uid");
        }
    }

    public static int getHighscore() {
        return jo.getInt("highscore");
    }

    public static void setHighscore(int value) {
        jo.put("highscore", value);
        write();
    }

    public static long getDate() {
        return jo.getLong("date");
    }

    public static void setDate(long date) {
        jo.put("date", date);
        write();
    }

    public static Skin getMuaSkin() {
        return Skin.valueOf(jo.get("skin").toString());
    }

    public static void setMuaSkin(Skin skin) {
        jo.put("skin", skin);
        write();
    }

    public static boolean getMusicEnabled() {
        return jo.getBoolean("music");
    }

    public static void setMusicEnabled(boolean enabled) {
        jo.put("music", enabled);
        write();
    }

    public static boolean getSFXEnabled() {
        return jo.getBoolean("sfx");
    }

    public static void setSFXEnabled(boolean enabled) {
        jo.put("sfx", enabled);
        write();
    }

    public static int getCoins() {
        return jo.getInt("coins");
    }

    public static void addCoins(int coins) {
        jo.put("coins", getCoins() + coins);
        write();
    }

    public static SafeList<Skin> getUnlockedSkins() {
        SafeList<Skin> result = new SafeList<>();

        jo.getJSONArray("skins").forEach(s -> {
            result.add(Skin.valueOf(s.toString()));
        });

        return result;
    }

    public static void unlockSkin(Skin skin) {
        jo.getJSONArray("skins").put(skin.name());
    }

    public static String getName() {
        return jo.getString("name");
    }

    public static void setName(String name) {
        jo.put("name", name);
        write();
    }

    private static String generateUID() {
        String result = String.valueOf(System.nanoTime());
        String ran = String.valueOf((int) (Math.random() * 100000));

        for (int i = 0; i < 5 - ran.length(); i++) {
            result += '0';
        }

        return result + ran;
    }

    public static int getMaxFPS() {
        return jo.getInt("fps");
    }

    public static void setMaxFPS(int fps) {
        jo.put("fps", fps);
        write();
    }
}