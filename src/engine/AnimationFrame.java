// Simo Münc

package engine;

// Frame einer Animation
// delay: Dauer bis zum nächsten Frame
// action: Auszuführende Aktion
public record AnimationFrame(float delay, Runnable action) {
}