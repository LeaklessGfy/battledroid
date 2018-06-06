package fr.battledroid.core.utils;

public final class Point {
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

    public Point set(Point point) {
        this.x = point.x;
        this.y = point.y;
        return this;
    }

    public Point offset(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point offset(Point point) {
        this.x += point.x;
        this.y += point.y;
        return this;
    }

    public Point clone() {
        return new Point(this);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
