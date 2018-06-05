package fr.battledroid.core;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Color;

public abstract class AbstractAsset implements Asset {
    protected final Asset asset;

    public AbstractAsset(Asset asset) {
        this.asset = Utils.requireNonNull(asset);
    }

    @Override
    public int getWidth() {
        return asset.getWidth();
    }

    @Override
    public int getHeight() {
        return asset.getHeight();
    }

    @Override
    public int getAlphaWidth() {
        return asset.getAlphaWidth();
    }

    @Override
    public int getAlphaHeight() {
        return asset.getAlphaHeight();
    }

    @Override
    public int[] getPixels() {
        return asset.getPixels();
    }

    @Override
    public boolean isObstacle() {
        return asset.isObstacle();
    }

    @Override
    public Color getColor() {
        return asset.getColor();
    }

    @Override
    public Object get() {
        return asset.get();
    }
}
