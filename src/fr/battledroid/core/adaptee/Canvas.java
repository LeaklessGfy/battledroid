package fr.battledroid.core.adaptee;

public interface Canvas {
    int getWidth();
    int getHeight();

    <T> T get();

    void drawRect(float x, float y, float width, float height, Color color);
    void drawCircle(float x, float y, float radius, Color color);
}
