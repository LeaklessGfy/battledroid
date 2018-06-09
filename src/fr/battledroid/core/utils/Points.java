package fr.battledroid.core.utils;

import fr.battledroid.core.Settings;
import fr.battledroid.core.map.tile.Tile;
import org.lwjgl.Sys;

public final class Points {
    public static int dist(PointF src, PointF dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static int dist(Point src, Point dst) {
        return Math.abs(dst.x-src.x) + Math.abs(dst.y-src.y);
    }

    public static Point screenToIso(double x, double y) {
        Settings s = Settings.instance();

        int w = s.tileWidth;
        int h = (s.tileHeight - s.tileAlphaHeight) / 2;

        double fx = (x / w) + (y / h);
        double fy = (y / h) - (x / w);

        return new Point((int) fx, (int) fy);
    }

    public static PointF step(PointF dst, int speed) {
        return new PointF(dst.x / speed, dst.y / speed);
    }

    public static PointF center(Tile tile) {
        Settings s = Settings.instance();
        PointF center = new PointF();
        PointF current = tile.getScreenBackground();

        center.x = (s.screenWidth / 2) - (current.x + s.tileWidth / 2);
        center.y = (s.screenHeight / 2) - (current.y + s.tileHeight / 2);

        return center;
    }
}
