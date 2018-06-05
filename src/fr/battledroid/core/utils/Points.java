package fr.battledroid.core.utils;

import fr.battledroid.core.asset.Image;
import fr.battledroid.core.asset.Point;
import fr.battledroid.core.asset.PointF;

public final class Points {
    public static int dist(PointF src, PointF dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static int dist(Point src, Point dst) {
        return (int) Math.sqrt(Math.pow(dst.x - src.x, 2) + Math.pow(dst.y - src.y, 2));
    }

    public static PointF isoToScreen(Point iso, Image img) {
        float x = (iso.x - iso.y) * (img.getWidth() / 2) - (img.getWidth() / 2);
        float y = (iso.x - iso.y) * ((img.getHeight() - img.getAlphaHeight()) / 4) - img.getAlphaHeight();
        return new PointF(x, y);
    }
}
