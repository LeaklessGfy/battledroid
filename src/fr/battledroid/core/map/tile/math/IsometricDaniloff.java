package fr.battledroid.core.map.tile.math;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public final class IsometricDaniloff implements TileMath {
    @Override
    public PointF isoToScreen(Tile tile, Asset asset) {
        if (asset == null) {
            return new PointF(0, 0);
        }

        int w = (asset.getWidth() / 2);
        int h = (int) (asset.getHeight() / 3.5f);

        float dx = (tile.x - tile.y) * w;
        float dy = (tile.x + tile.y) * h;

        return new PointF(dx, dy);
    }

    @Override
    public Point screenToIso(double x, double y) {
        return null;
    }
}
