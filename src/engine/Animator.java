package engine;

import java.util.List;

public class Animator extends Component {

    private final SafeList<AnimationFrame> frames = new SafeList<>();
    private float cooldown;
    private int frameIndex;

    @Override
    void update() {
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

    public void addFrames(AnimationFrame... frames) {
        this.frames.addAll(List.of(frames));
    }

    public void removeFrames(AnimationFrame... frames) {
        this.frames.removeAll(List.of(frames));
    }
}