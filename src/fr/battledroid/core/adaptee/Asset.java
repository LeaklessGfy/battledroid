package fr.battledroid.core.adaptee;

public interface Asset {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();
    boolean isObstacle();
    AssetColor getColor();
    void draw(Canvas canvas, float x, float y);
    void tick();
}
