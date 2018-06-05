package fr.battledroid.core.asset;

public interface Image {
    int getWidth();
    int getHeight();
    int getAlphaWidth();
    int getAlphaHeight();
    int[] getPixels();
    Object get();
}
