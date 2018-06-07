package fr.battledroid.core.map;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.map.path.AStarFinder;
import fr.battledroid.core.map.path.PathFinder;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;
import fr.battledroid.core.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

final class MapImpl implements Map {
    private final Tile[][] tiles;
    private final ArrayList<Particle> particles;
    private final Settings settings;
    private final PathFinder pathFinder;

    MapImpl(Tile[][] tiles) {
        this.tiles = Utils.requireNonNull(tiles);
        this.settings = Settings.instance();
        this.particles = new ArrayList<>();
        this.pathFinder = new AStarFinder(this, settings.mapSize / 2, true);
    }

    @Override
    public int size() {
        return settings.mapSize;
    }

    @Override
    public boolean isBusy(int x, int y) {
        Tile b = tiles[x][y];
        return b.isBusy();
    }

    @Override
    public boolean isBusy(Point point) {
        return isBusy(point.x, point.y);
    }

    @Override
    public Tile tile(int x, int y) {
        return tiles[x][y];
    }

    @Override
    public Tile tile(Point point) {
        return tiles[point.x][point.y];
    }

    @Override
    public Tile screenToTile(double x, double y) {
        Point point = Points.screenToIso(x, y);
        return tiles[point.x][point.y];
    }

    @Override
    public List<Particle> particles() {
        return Collections.unmodifiableList(particles);
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                tiles[x][y].drawBackground(canvas, offset);
            }
        }
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                tiles[x][y].drawOverlay(canvas, offset);
            }
        }
        for (Particle particle : particles) {
            particle.draw(canvas, offset);
        }
    }

    @Override
    public void tick() {
        for (int y = 0; y < settings.mapSize; y++) {
            for (int x = 0; x < settings.mapSize; x++) {
                tiles[x][y].tick();
            }
        }
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle particle = it.next();
            particle.tick();
            if (particle.hasEnd()) {
                it.remove();
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
    public List<Tile> findPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, false)
                .stream()
                .map(p -> tiles[p.x][p.y])
                .collect(Collectors.toList());
    }

    @Override
    public List<Tile> findNearestPath(Point src, Point dst) {
        return pathFinder.findPath(src, dst, true)
                .stream()
                .map(p -> tiles[p.x][p.y])
                .collect(Collectors.toList());
    }

    @Override
    public boolean valid(Point point) {
        return point.x >= 0 && point.x < size() && point.y >= 0 && point.y < size();
    }

    @Override
    public void addParticle(Particle particle) {
        particles.add(particle);
    }
}
