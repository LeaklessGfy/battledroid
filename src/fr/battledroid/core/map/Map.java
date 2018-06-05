package fr.battledroid.core.map;

import fr.battledroid.core.asset.Asset;
import fr.battledroid.core.asset.Canvas;
import fr.battledroid.core.asset.Point;
import fr.battledroid.core.asset.PointF;

import java.util.List;

public interface Map {
    int size();
    Asset background(int x, int y);
    Asset background(Point point);
    Asset overlay(int x, int y);
    Asset overlay(Point point);
    Asset center();
    boolean isBusy(int x, int y);
    boolean isBusy(Point point);

    void draw(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas);
    void tick();

    int cost(int x, int y, int xD, int yD);
    int dist(Point src, Point dst);

    List<Point> findPath(Point src, Point dst);
    List<Point> findNearestPath(Point src, Point dst);

    //Tile screenToTile(double x, double y);
    //TileHolder<Artifact> randomArtifacts(int nb, ArtifactFactory factory);
}
