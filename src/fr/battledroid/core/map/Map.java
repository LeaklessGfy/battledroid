package fr.battledroid.core.map;

import fr.battledroid.core.Ticker;
import fr.battledroid.core.drawable.MasterDrawable;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.utils.Point;

import java.util.Collection;
import java.util.List;

public interface Map extends MasterDrawable, Ticker {
    int size();
    boolean isBusy(int x, int y);
    boolean isBusy(Point point);
    Tile tile(int x, int y);
    Tile tile(Point point);
    Tile screenToTile(double x, double y);
    Collection<Particle> particles();

    int cost(int x, int y, int xD, int yD);
    int dist(Point src, Point dst);

    List<Tile> findPath(Point src, Point dst);
    List<Tile> findNearestPath(Point src, Point dst);
    boolean valid(Point point);
    //TileHolder<Artifact> randomArtifacts(int nb, ArtifactFactory factory);

    void addParticle(Particle particle);
}
