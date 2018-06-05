package fr.battledroid.core.map.path;

import java.util.List;

import fr.battledroid.core.Point;

public interface PathFinder {
    List<Point> findPath(Point src, Point dst, boolean allowNearest);
}
