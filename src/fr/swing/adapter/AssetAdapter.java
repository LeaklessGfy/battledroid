package fr.swing.adapter;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Color;

import java.util.Objects;

public final class AssetAdapter implements Asset {
    private final java.awt.Image img;
    private final int width;
    private final int height;
    private final int alphaWidth;
    private final int alphaHeight;

    public AssetAdapter(java.awt.Image img, Settings settings) {
        this.img = Objects.requireNonNull(img).getScaledInstance(settings.tileWidth, settings.tileHeight, java.awt.Image.SCALE_DEFAULT);
        this.width = settings.tileWidth;
        this.height = settings.tileHeight;
        this.alphaWidth = settings.tileAlphaWidth;
        this.alphaHeight = settings.tileAlphaHeight;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getAlphaWidth() {
        return alphaWidth;
    }

    @Override
    public int getAlphaHeight() {
        return alphaHeight;
    }

    @Override
    public int[] getPixels() {
        return new int[0];
    }

    @Override
    public boolean isObstacle() {
        return false;
    }

    @Override
    public Color getColor() {
        /*
        int pixels[] = img;

        int red = 0;
        int green = 0;
        int blue = 0;
        int pixelCount = 0;

        for (int pixel : pixels) {
            if (Color.alpha(pixel) == 255) { //ignore transparent pixels
                red += (pixel >> 16) & 0xFF;
                green += (pixel >> 8) & 0xFF;
                blue += (pixel & 0xFF);
                pixelCount++;
            }
        }

        red /= pixelCount;
        green /= pixelCount;
        blue /= pixelCount;

        return new ColorAdapter((255 << 24) | (red << 16) | (green << 8) | blue);
        */
        return null;
    }

    @Override
    public Object get() {
        return img;
    }
}
