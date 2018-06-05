package fr.battledroid.core.map;

import fr.battledroid.core.Settings;
import fr.battledroid.core.Utils;
import fr.battledroid.core.asset.Canvas;
import fr.battledroid.core.asset.Point;
import fr.battledroid.core.asset.PointF;
import fr.battledroid.core.map.path.AStarFinder;
import fr.battledroid.core.map.path.PathFinder;
import fr.battledroid.core.asset.Asset;
import fr.battledroid.core.utils.Points;

import java.util.List;

final class MapImpl implements Map {
    private final Asset[][] backgrounds;
    private final Asset[][] overlays;
    private final Settings settings;
    private final PathFinder pathFinder;

    MapImpl(Asset[][] backgrounds, Asset[][] overlays, Settings settings) {
        this.backgrounds = Utils.requireNonNull(backgrounds);
        this.overlays = Utils.requireNonNull(overlays);
        this.settings = Utils.requireNonNull(settings);
        this.pathFinder = new AStarFinder(this, settings.mapSize / 2, true);
    }

    @Override
    public int size() {
        return settings.mapSize;
    }

    @Override
    public Asset background(int x, int y) {
        return backgrounds[x][y];
    }

    @Override
    public Asset background(Point point) {
        return background(point.x, point.y);
    }

    @Override
    public Asset overlay(int x, int y) {
        return overlays[x][y];
    }

    @Override
    public Asset overlay(Point point) {
        return overlay(point.x, point.y);
    }

    @Override
    public Asset center() {
        return backgrounds[(settings.mapSize - 1) / 2][(settings.mapSize - 1) / 2];
    }

    @Override
    public boolean isBusy(int x, int y) {
        Asset b = backgrounds[x][y];
        Asset o = overlays[x][y];

        if (b == null || b.isObstacle()) {
            return true;
        }
        if (o != null && o.isObstacle()) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isBusy(Point point) {
        return isBusy(point.x, point.y);
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                if (backgrounds[x][y] != null) {
                    backgrounds[x][y].draw(canvas, offset);
                }
            }
        }
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                if (overlays[x][y] != null) {
                    overlays[x][y].draw(canvas, offset);
                }
            }
        }
    }

    @Override
    public void drawMiniMap(Canvas canvas) {
        float cellWidth = canvas.getWidth() / size();
        float cellHeight = canvas.getHeight() / size();

        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                backgrounds[x][y].drawMiniMap(canvas, cellWidth, cellHeight);
            }
        }
    }

    @Override
    public void tick() {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                if (overlays[x][y] != null) {
                    overlays[x][y].tick();
                }
            }
        }
    }

    @Override
    public int cost(int x, int y, int xD, int yD) {
        return 1;
    }

    @Override
    public int dist(Point src, Point dst) {
        return Points.dist(src, dst);
    }

    @Override
    public List<Point> findPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, false);
    }

    @Override
    public List<Point> findNearestPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, true);
    }
}
