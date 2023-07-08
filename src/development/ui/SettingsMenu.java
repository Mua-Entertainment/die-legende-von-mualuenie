package development.ui;

import development.data.DataFile;
import development.main.Program;
import engine.main.Button;
import engine.main.GameObject;
import engine.main.Label;
import engine.main.Slider;

// Einstellungsmenü
public class SettingsMenu extends GameObject {
    private Label fpsLabel, musicLabel, sfxLabel;
    private Slider fpsSlider, musicSlider, sfxSlider;
    private Button fullscreenBtn;

    @Override
    protected void load() {
        super.load();

        // zurück zum MainMenu
        createButton(this, "Zurück", () -> replace(new MainMenu()), 0.5f, 0.5f);

        // Vollbildmodus ein/aus
        fullscreenBtn = createMenuButton(this, "Vollbild " + (hasFullscreen() ? "an" : "aus"), this::changeWindowState, .8f);

        // Slider zum Anpassen der Musiklautstärke
        musicLabel = createMenuLabel(this, "Musiklautstärke " + (int) (DataFile.getMusicVolume() * 100f), 1.5f);
        musicSlider = createMenuSlider(this, 1.9f);
        musicSlider.button.release.subscribe(this::switchMusicSettings);
        musicSlider.setValue(DataFile.getMusicVolume());

        // Slider zum Anpassen der Musiklautstärke
        sfxLabel = createMenuLabel(this, "SFX-Lautstärke " + (int) (DataFile.getSFXVolume() * 100f), 2.2f);
        sfxSlider = createMenuSlider(this, 2.6f);
        sfxSlider.button.release.subscribe(this::switchSFXSettings);
        sfxSlider.setValue(DataFile.getSFXVolume());

        // Slider zum Anpassen der Maximalen FPS
        fpsLabel = createMenuLabel(this, DataFile.getMaxFPS() + " max. FPS", 2.9f);
        fpsSlider = createMenuSlider(this, 3.2f);
        fpsSlider.button.release.subscribe(this::switchMaxFPS);
        fpsSlider.setValue((DataFile.getMaxFPS() - 10) / 990f);

        // Zeigt den aktuellen Username an
        createMenuLabel(this, "Name: " + DataFile.getName(), 3.5f);

        // Username ändern
        createMenuButton(this, "Ändern", () -> replace(new NameInput(new SettingsMenu())), 4f);
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