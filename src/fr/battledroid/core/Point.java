package fr.battledroid.core;

public class Point {
    public int x;
    public int y;

    public Point() {}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point transform(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
