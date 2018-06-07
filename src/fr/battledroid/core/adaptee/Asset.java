package fr.battledroid.core.adaptee;

import fr.battledroid.core.drawable.SlaveDrawable;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.HitBox;
import fr.battledroid.core.utils.PointF;

public interface Asset extends SlaveDrawable {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();

    boolean isObstacle();
    AssetColor getColor();

    void tick();
    HitBox hitBox(PointF screen);

    Tile getCurrent();
    void setCurrent(Tile tile);

    Object get();
}
