package fr.battledroid.core.adaptee;

import fr.battledroid.core.drawable.SlaveDrawable;

public interface Asset extends SlaveDrawable {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();

    boolean isObstacle();
    AssetColor getColor();

    void tick();

    Object get();
}
