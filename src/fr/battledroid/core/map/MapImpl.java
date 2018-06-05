package fr.battledroid.core.map;

import fr.battledroid.core.*;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.map.path.AStarFinder;
import fr.battledroid.core.map.path.PathFinder;
import fr.battledroid.core.utils.Points;

import java.util.List;
import java.util.stream.Collectors;

final class MapImpl implements Map {
    private final Tile[][] backgrounds;
    private final Tile[][] overlays;
    private final Settings settings;
    private final PathFinder pathFinder;

    MapImpl(Tile[][] backgrounds, Tile[][] overlays, Settings settings) {
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
    public boolean isBusy(int x, int y) {
        Tile b = backgrounds[x][y];
        Tile o = overlays[x][y];

        if (b == null || b.isBusy()) {
            return true;
        }
        if (o != null && o.isBusy()) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isBusy(Point point) {
        return isBusy(point.x, point.y);
    }

    @Override
    public boolean shouldDraw(PointF offset, Point canvasSize) {
        return true;
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                if (backgrounds[x][y] != null) {
                    backgrounds[x][y].drawMap(canvas, offset);
                }
            }
        }
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                if (overlays[x][y] != null) {
                    overlays[x][y].drawMap(canvas, offset);
                }
            }
        }
    }

    @Override
    public void drawMiniMap(Canvas canvas, PointF cellSize) {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                backgrounds[x][y].drawMiniMap(canvas, cellSize);
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
    public List<Position> findPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, false)
                .stream()
                .map(p -> overlays[p.x][p.y])
                .collect(Collectors.toList());
    }

    @Override
    public List<Position> findNearestPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, true)
                .stream()
                .map(p -> overlays[p.x][p.y])
                .collect(Collectors.toList());
    }

    @Override
    public boolean valid(Point point) {
        return point.x >= 0 && point.x < size() && point.y >= 0 && point.y < size();
    }

    @Override
    public Position tile(Point point) {
        return backgrounds[point.x][point.y];
    }

    @Override
    public Position overlay(Point point) {
        return overlays[point.x][point.y];
    }

    @Override
    public Position screenToTile(double x, double y) {
        Point point = Points.screenToIso(x, y, settings);
        return backgrounds[point.x][point.y];
    }
}
