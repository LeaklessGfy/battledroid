package fr.battledroid.core;

import fr.battledroid.core.adaptee.Asset;

public interface Position {
    Point iso();
    PointF screen();
    void postMove(Position position);
    boolean isBusy();
    void setAsset(Asset asset);
    void translate(Asset asset, PointF screen, PointF direction);
}
