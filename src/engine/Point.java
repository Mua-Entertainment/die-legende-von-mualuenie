package engine;

public record Point(float x, float y) {
    public static Point ZERO = new Point(0, 0);
}