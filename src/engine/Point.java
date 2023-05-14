package engine;

public class Point {

    public static Point ZERO = new Point(0, 0);

    public float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}