package fr.battledroid.core;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public interface Position {
    class Context {
        Position dst;
        PointF dir;
        int iteration;
        int max;
    }

    Point iso();
    PointF screen();
    void move(Position position);
    boolean isBusy();
    void setAsset(Asset asset);
    void translate(Asset asset, PointF screen, Context context);
}
