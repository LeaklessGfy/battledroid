package fr.swing.adapter;

import fr.battledroid.core.Settings;
import fr.battledroid.core.asset.Image;

import java.util.Objects;

public final class ImageAdapter implements Image {
    private final java.awt.Image img;
    private final int width;
    private final int height;
    private final int alphaWidth;
    private final int alphaHeight;

    public ImageAdapter(java.awt.Image img, Settings settings) {
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
    public Object get() {
        return img;
    }
}
