package fr.battledroid.core.adaptee;

public interface Asset {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();
    int[] getPixels();
    boolean isObstacle();
    Color getColor();
    Object get();
}
