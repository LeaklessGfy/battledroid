package fr.battledroid.core.adaptee;

import fr.battledroid.core.drawable.SlaveDrawable;
import fr.battledroid.core.utils.PointF;

public interface Asset extends SlaveDrawable {
    int getWidth();
    int getHeight();
    PointF getOffset();

    boolean isObstacle();
    AssetColor getColor();

    void tick();

    Object get();
}
