package engine;

// Größe eines Objektes
public record Size(float width, float height) {
    // keine Größe
    public static Size ZERO = new Size(0, 0);
    // eine Einheit in x- und y-Richtung
    public static Size ONE = new Size(1, 1);
}