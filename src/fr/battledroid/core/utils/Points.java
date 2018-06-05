package fr.battledroid.core.utils;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.Point;
import fr.battledroid.core.PointF;

public final class Points {
    public static int dist(PointF src, PointF dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static int dist(Point src, Point dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static PointF isoToScreen(Point iso, Asset img) {
        float x = (iso.x - iso.y) * (img.getWidth() / 2) - (img.getWidth() / 2);
        float y = (iso.x + iso.y) * ((img.getHeight() - img.getAlphaHeight()) / 4) - img.getAlphaHeight();
        return new PointF(x, y);
    }

    public static Point screenToIso(double x, double y, Settings s) {
        int w = s.tileWidth;
        int h = (s.tileHeight - s.tileAlphaHeight) / 2;

        double fx = (x / w) + (y / h);
        double fy = (y / h) - (x / w);

        return new Point((int) fx, (int) fy);
    }
}
