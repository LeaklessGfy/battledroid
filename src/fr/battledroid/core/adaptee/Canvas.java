package fr.battledroid.core.adaptee;

import fr.battledroid.core.utils.Point;

public interface Canvas<T> {
    Canvas wrap(T t);
    int getWidth();
    int getHeight();
    Point getSize();
    T get();

    void drawRect(float x, float y, float width, float height, AssetColor color);
    void drawCircle(float x, float y, float radius, AssetColor color);
    void drawColor(AssetColor color);
    void drawAsset(Asset asset, float x, float y);
    void drawAsset(Asset asset, AssetColor color, float x, float y);
    void drawText(String text, float x, float y, AssetColor color);
}
