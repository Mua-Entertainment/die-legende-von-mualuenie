// Joha Zwin

package development;

import engine.Button;
import engine.GameObject;


// Einstellungsmenü
public class SettingScreen extends GameObject {
    private Button musicButton, sfxButton;

    @Override
    protected void load() {
        super.load();

        // Button mit dem man zum MainMenu zurück kommt
        createButton(this, "Zurück", this::returnToMainMenu, 0.5f, 0.5f);

        String text = Program.data.getMusicEnabled() ? "Musik aus" : "Musik ein";
        musicButton = createMenuButton(this, text, this::switchMusicSettings, 2f);

        text = Program.data.getSFXEnabled() ? "SFX aus" : "SFX ein";
        sfxButton = createMenuButton(this, text, this::switchSFXSettings, 3f);

        createMenuLabel(this, "Name: " + Program.data.getName(), 4f);
        createMenuButton(this, "Ändern", this::openNameInput, 4.5f);
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

    private void switchMusicSettings() {
        boolean enabled = Program.data.getMusicEnabled();
        Program.data.setMusicEnabled(!enabled);

        if (enabled) {
            Program.music.stop();
        } else {
            Program.music.play(true);
        }

        musicButton.label.setText(enabled ? "Musik ein" : "Musik aus");
    }

    private void switchSFXSettings() {
        boolean enabled = Program.data.getSFXEnabled();
        Program.data.setSFXEnabled(!enabled);

        sfxButton.label.setText(enabled ? "SFX ein" : "SFX aus");
    }
}