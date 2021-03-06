package fr.battledroid.core.adaptee;

import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.HitBox;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Utils;

public class AssetWrapper implements Asset {
    private final Asset asset;

    public AssetWrapper(Asset asset) {
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
    public boolean isObstacle() {
        return asset.isObstacle();
    }

    @Override
    public AssetColor getColor() {
        return asset.getColor();
    }

    @Override
    public void tick() {
        asset.tick();
    }

    @Override
    public Object get() {
        return asset.get();
    }

    @Override
    public boolean shouldDraw(PointF screen, PointF offset, Point canvasSize) {
        return asset.shouldDraw(screen, offset, canvasSize);
    }

    @Override
    public void draw(Canvas canvas, PointF screen, PointF offset) {
        asset.draw(canvas, screen, offset);
    }
}
