package development.ui;

import development.data.DataFile;
import development.main.Program;
import engine.main.*;
import engine.main.Button;
import engine.main.Label;

import java.awt.*;

// Einstellungsmenü
public class SettingsMenu extends RectObject {
    private Label fpsLabel, musicLabel, sfxLabel;
    private Slider fpsSlider, musicSlider, sfxSlider;
    private Button fullscreenBtn;

    // GameObject, welches nach Verlassen dieser Anzeige angezeigt wird
    private final GameObject objToReturn;

    public SettingsMenu(GameObject objToReturn) {
        this.objToReturn = objToReturn;
    }

    @Override
    protected void load() {
        super.load();

        setSize(getCanvasSize());
        setColor(objToReturn instanceof PauseMenu ? new Color(0x7C000000, true) : new Color(0x567BB4));

        // zurück
        createButton(this, "Zurück", () -> replace(objToReturn), 0.5f, 0.5f);

        // Vollbildmodus ein/aus
        fullscreenBtn = createMenuButton(this, "Vollbild " + (hasFullscreen() ? "an" : "aus"), this::changeWindowState, .8f);

        Color labelColor = objToReturn instanceof PauseMenu ? Color.white : Color.black;

        musicLabel = createMenuLabel(this, "Musiklautstärke " + (int) (DataFile.getMusicVolume() * 100f), 1.5f);
        musicLabel.setColor(labelColor);

        // Slider zum Anpassen der Musiklautstärke
        musicSlider = createMenuSlider(this, 1.9f);
        musicSlider.button.release.subscribe(this::switchMusicSettings);
        musicSlider.setValue(DataFile.getMusicVolume());

        sfxLabel = createMenuLabel(this, "SFX-Lautstärke " + (int) (DataFile.getSFXVolume() * 100f), 2.2f);
        sfxLabel.setColor(labelColor);

        // Slider zum Anpassen der Musiklautstärke
        sfxSlider = createMenuSlider(this, 2.6f);
        sfxSlider.button.release.subscribe(this::switchSFXSettings);
        sfxSlider.setValue(DataFile.getSFXVolume());

        fpsLabel = createMenuLabel(this, DataFile.getMaxFPS() + " max. FPS", 2.9f);
        fpsLabel.setColor(labelColor);

        // Slider zum Anpassen der Maximalen FPS
        fpsSlider = createMenuSlider(this, 3.3f);
        fpsSlider.button.release.subscribe(this::switchMaxFPS);
        fpsSlider.setValue((DataFile.getMaxFPS() - 10) / 990f);

        // nur im Hauptmenü möglich
        if (objToReturn instanceof MainMenu) {
            // Zeigt den aktuellen Username an
            createMenuLabel(this, "Name: " + DataFile.getName(), 3.6f).setColor(labelColor);

            // Username ändern
            createMenuButton(this, "Ändern", () -> replace(new NameInput(new SettingsMenu(objToReturn))), 4f);
        }
    }

    @Override
    protected void update() {
        super.update();
        fpsLabel.setText(getSliderFPSValue() + " max. FPS");
    }

    // ändert max. FPS
    private void switchMaxFPS() {
        DataFile.setMaxFPS(getSliderFPSValue());
    }

    // berechnet FPS-Wert des Sliders
    private int getSliderFPSValue() {
        return 10 + (int) (fpsSlider.getValue() * 990f);
    }

    // ändert Musiklautstärke
    private void switchMusicSettings() {
        DataFile.setMusicVolume(musicSlider.getValue());
        Program.music.setVolume(musicSlider.getValue());
        musicLabel.setText("Musiklautstärke " + (int) (musicSlider.getValue() * 100f));
    }

    // ändert SFX-Lautstärke
    private void switchSFXSettings() {
        DataFile.setSFXVolume(sfxSlider.getValue());
        sfxLabel.setText("SFX-Lautstärke " + (int) (sfxSlider.getValue() * 100f));
    }

    @Override
    protected void changeWindowState() {
        super.changeWindowState();
        fullscreenBtn.label.setText("Vollbild " + (hasFullscreen() ? "an" : "aus"));
    }
}