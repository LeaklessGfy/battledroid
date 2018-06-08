package fr.slick.adapter;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import org.newdawn.slick.Image;

import java.util.Objects;

public final class AssetAdapter implements Asset {
    private final Image img;
    private final int width;
    private final int height;
    private final int alphaWidth;
    private final int alphaHeight;

    private AssetAdapter(Image img, int width, int height, int alphaWidth, int alphaHeight) {
        this.img = img;
        this.width = width;
        this.height = height;
        this.alphaWidth = alphaWidth;
        this.alphaHeight = alphaHeight;
    }

    public static AssetAdapter create(Image img) {
        Objects.requireNonNull(img);
        Settings settings = Settings.instance();
        Image scaled = img.getScaledCopy(settings.tileWidth, settings.tileHeight);
        int width = settings.tileWidth;
        int height = settings.tileHeight;
        int alphaWidth = settings.tileAlphaWidth;
        int alphaHeight = settings.tileAlphaHeight;

        return new AssetAdapter(scaled, width, height, alphaWidth, alphaHeight);
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
    public boolean isObstacle() {
        return false;
    }

    @Override
    public AssetColor getColor() {
        /*
        int pixels[] = img;

        int red = 0;
        int green = 0;
        int blue = 0;
        int pixelCount = 0;

        for (int pixel : pixels) {
            if (AssetColor.alpha(pixel) == 255) { //ignore transparent pixels
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
    public boolean shouldDraw(PointF screen, PointF offset, Point canvasSize) {
        double x = screen.x + offset.x;
        double y = screen.y + offset.y;

        return x >= 0 - getWidth() && x <= canvasSize.x && y >= 0 - getHeight() && y <= canvasSize.y;
    }

    @Override
    public void draw(Canvas canvas, PointF screen, PointF offset) {
        canvas.drawAsset(this, screen.x + offset.x, screen.y + offset.y);
    }

    @Override
    public void tick() {}

    @Override
    public Object get() {
        return img;
    }
}
