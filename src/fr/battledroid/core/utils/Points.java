package fr.battledroid.core.utils;

import fr.battledroid.core.Settings;
import fr.battledroid.core.map.tile.Tile;

public final class Points {
    public static int dist(PointF src, PointF dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static int dist(Point src, Point dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static PointF isoToScreen(int x, int y) {
        Settings s = Settings.instance();

        int w = (s.tileWidth / 2);
        int h = ((s.tileHeight - s.tileAlphaHeight) / 4);

        float dx = (x - y) * w - w;
        float dy = (x + y) * h - s.tileAlphaHeight;

        return new PointF(dx, dy);
    }

    public static PointF isoToScreen(Point point) {
        return isoToScreen(point.x, point.y);
    }

    public static Point screenToIso(double x, double y) {
        Settings s = Settings.instance();

        int w = s.tileWidth;
        int h = (s.tileHeight - s.tileAlphaHeight) / 2;

        double fx = (x / w) + (y / h);
        double fy = (y / h) - (x / w);

        return new Point((int) fx, (int) fy);
    }

    public static PointF movement(Point src, Point dst) {
        PointF s = Points.isoToScreen(src);
        PointF d = Points.isoToScreen(dst);
        return new PointF(d.x - s.x, d.y - s.y);
    }

    public static PointF step(PointF src, PointF dst, int speed) {
        float x = src.x + (dst.x / speed);
        float y = src.y + (dst.y / speed);
        return new PointF(x, y);
    }

    public static PointF center(Tile tile) {
        Settings s = Settings.instance();
        PointF center = new PointF();
        PointF current = isoToScreen(tile.iso());

        center.x = (s.screenWidth / 2) - (current.x + s.tileWidth / 2);
        center.y = (s.screenHeight / 2) - (current.y + s.tileHeight / 2);

        return center;
    }
}
