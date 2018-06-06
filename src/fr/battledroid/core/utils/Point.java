package fr.battledroid.core.utils;

public class Point {
    public int x;
    public int y;

    public Point() {}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public void offset(Point point) {
        this.x += point.x;
        this.y += point.y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
