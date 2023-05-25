// Simo Münc

package engine;

import java.util.List;

// ermöglicht es Aktionen in bestimmten Zeitabständen auszuführen
public class Animator extends Component {

    // alle Frames die animiert werden
    private final SafeList<AnimationFrame> frames = new SafeList<>();

    // Cooldown für die übrige Zeit bis zum nächstem Frame
    private float cooldown;

    // der index für den aktuellen Frame
    private int frameIndex;

    @Override
    void update() {
        // spielt den aktuellen Frame ab
        if (cooldown > 0) {
            cooldown -= 1f / owner.getFPS();
        } else {
            frames.get(frameIndex).action().run();

            if (frameIndex < frames.size() - 1) {
                frameIndex++;
            } else {
                frameIndex = 0;
            }

            cooldown = frames.get(frameIndex).delay();
        }
    }

    // Frames hinzufügen
    public void addFrames(AnimationFrame... frames) {
        this.frames.addAll(List.of(frames));
    }

    // Frames entfernen
    public void removeFrames(AnimationFrame... frames) {
        this.frames.removeAll(List.of(frames));
    }
}