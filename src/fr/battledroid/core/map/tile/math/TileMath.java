package fr.battledroid.core.map.tile.math;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public interface TileMath {
    PointF isoToScreen(Point point, Asset asset);
    Point screenToIso(double x, double y);
}