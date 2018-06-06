package fr.battledroid.core.utils;

import fr.battledroid.core.Settings;

public final class Points {
    public static int dist(PointF src, PointF dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static int dist(Point src, Point dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static PointF isoToScreen(Point iso) {
        Settings s = Settings.instance();

        int w = (s.tileWidth / 2);
        int h = ((s.tileHeight - s.tileAlphaHeight) / 4);

        float x = (iso.x - iso.y) * w - w;
        float y = (iso.x + iso.y) * h - s.tileAlphaHeight;

        return new PointF(x, y);
    }

    public static Point screenToIso(double x, double y) {
        Settings s = Settings.instance();

        int w = s.tileWidth;
        int h = (s.tileHeight - s.tileAlphaHeight) / 2;

        double fx = (x / w) + (y / h);
        double fy = (y / h) - (x / w);

        return new Point((int) fx, (int) fy);
    }
}
