package fr.battledroid.core.asset;

public interface Canvas {
    int getWidth();
    int getHeight();
    void drawImage(Object img, float x, float y);
    void drawRect(float x, float y, float width, float height, Color color);
    void drawCircle(float x, float y, float radius, Color color);
    void drawColor(Color color);
}
