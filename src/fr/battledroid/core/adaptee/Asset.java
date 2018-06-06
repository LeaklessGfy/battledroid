package fr.battledroid.core.adaptee;

public interface Asset {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();
    boolean isObstacle();
    Color getColor();
    void draw(Canvas canvas, float x, float y);
}
