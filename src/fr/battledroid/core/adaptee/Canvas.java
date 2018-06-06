package fr.battledroid.core.adaptee;

import fr.battledroid.core.utils.Point;

public interface Canvas {
    int getWidth();
    int getHeight();
    Point getSize();

    <T> T get();

    void drawRect(float x, float y, float width, float height, Color color);
    void drawCircle(float x, float y, float radius, Color color);
}
