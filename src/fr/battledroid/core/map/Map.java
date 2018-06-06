package fr.battledroid.core.map;

import fr.battledroid.core.Drawable;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.utils.Point;

import java.util.List;

public interface Map extends Drawable {
    int size();
    boolean isBusy(int x, int y);
    boolean isBusy(Point point);

    int cost(int x, int y, int xD, int yD);
    int dist(Point src, Point dst);

    List<Tile> findPath(Point src, Point dst);
    List<Tile> findNearestPath(Point src, Point dst);
    boolean valid(Point point);
    Tile background(Point point);
    Tile overlay(Point point);
    Tile screenToTile(double x, double y);
    //TileHolder<Artifact> randomArtifacts(int nb, ArtifactFactory factory);

    void addParticle(Particle particle);
}
