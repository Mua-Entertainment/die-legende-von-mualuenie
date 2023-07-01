// Joha Zwin

package development;

import engine.Button;
import engine.GameObject;
import engine.Label;
import engine.Slider;


// Einstellungsmenü
public class SettingScreen extends GameObject {
    private Button musicButton, sfxButton;
    private Label fpsLabel;
    private Slider fpsSlider;

    @Override
    protected void load() {
        super.load();

        // Button mit dem man zum MainMenu zurück kommt
        createButton(this, "Zurück", this::returnToMainMenu, 0.5f, 0.5f);

        String text = DataFile.getMusicEnabled() ? "Musik aus" : "Musik ein";
        musicButton = createMenuButton(this, text, this::switchMusicSettings, 1f);

        text = DataFile.getSFXEnabled() ? "SFX aus" : "SFX ein";
        sfxButton = createMenuButton(this, text, this::switchSFXSettings, 1.75f);

        fpsLabel = createMenuLabel(this, DataFile.getMaxFPS() + " max. FPS", 2.5f);
        fpsSlider = createMenuSlider(this, 2.9f);
        fpsSlider.button.release.subscribe(this::switchMaxFPS);
        fpsSlider.setValue((DataFile.getMaxFPS() - 10) / 990f);

        var x = new Slider();
        add(x);
        x.setValue(.5f);
        System.out.println(x.getValue());

        createMenuLabel(this, "Name: " + DataFile.getName(), 3.5f);
        createMenuButton(this, "Ändern", this::openNameInput, 4f);
    }

    @Override
    protected void update() {
        super.update();
        fpsLabel.setText(getSliderFPSValue() + " max. FPS");
    }

    // zurück zum MainMenu
    private void returnToMainMenu() {
        add(new MainMenu());
        destroy();
    }

    private void openNameInput() {
        add(new NameInput(new SettingScreen()));
        destroy();
    }

    private void switchMaxFPS() {
        DataFile.setMaxFPS(getSliderFPSValue());
    }

    private int getSliderFPSValue() {
        return 10 + (int) (fpsSlider.getValue() * 990f);
    }

    private void switchMusicSettings() {
        boolean enabled = DataFile.getMusicEnabled();
        DataFile.setMusicEnabled(!enabled);

        if (enabled) {
            Program.music.stop();
        } else {
            Program.music.play(true);
        }

        musicButton.label.setText(enabled ? "Musik ein" : "Musik aus");
    }

    private void switchSFXSettings() {
        boolean enabled = DataFile.getSFXEnabled();
        DataFile.setSFXEnabled(!enabled);

        sfxButton.label.setText(enabled ? "SFX ein" : "SFX aus");
    }
}