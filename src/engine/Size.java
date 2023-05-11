package engine;

public record Size(float width, float height) {
    public static Size ZERO = new Size(0, 0);
    public static Size ONE = new Size(1, 1);
}