package fr.battledroid.core.map;

import fr.battledroid.core.Drawable;
import fr.battledroid.core.Position;
import fr.battledroid.core.utils.Point;

import java.util.List;

public interface Map extends Drawable {
    int size();
    boolean isBusy(int x, int y);
    boolean isBusy(Point point);

    int cost(int x, int y, int xD, int yD);
    int dist(Point src, Point dst);

    List<Position> findPath(Point src, Point dst);
    List<Position> findNearestPath(Point src, Point dst);
    boolean valid(Point point);
    Position tile(Point point);
    Position overlay(Point point);

    Position screenToTile(double x, double y);
    //TileHolder<Artifact> randomArtifacts(int nb, ArtifactFactory factory);
}
