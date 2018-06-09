package fr.battledroid.core.map.tile.math;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public final class IsometricDaniloff implements TileMath {
    @Override
    public PointF isoToScreen(Point point, Asset asset) {
        if (asset == null) {
            return new PointF(0, 0);
        }

        int w = (asset.getWidth() / 2);
        int h = (int) (asset.getHeight() / 3.5f);

        float dx = (point.x - point.y) * w;
        float dy = (point.x + point.y) * h;

        return new PointF(dx, dy);
    }

    @Override
    public Point screenToIso(double x, double y) {
        return null;
    }
}
