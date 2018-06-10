package fr.slick.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.adaptee.AssetInfo;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.nio.file.Path;
import java.util.Objects;

public final class AssetAdapter implements Asset {
    private final Image img;
    private final PointF offset;

    private AssetAdapter(Image img, PointF offset) {
        this.img = Objects.requireNonNull(img);
        this.offset = Objects.requireNonNull(offset);
    }

    public static AssetAdapter create(int scale, AssetInfo info) {
        Image scaled = load(info, scale);
        return new AssetAdapter(scaled, new PointF(info.getOffsetX() * scale, info.getOffsetY() * scale));
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public PointF getOffset() {
        return offset;
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

    private static Image load(AssetInfo info, int scale) {
        try {
            Image img = new Image(info.getPath().toString());
            if (info.getWidth() == -1 && info.getHeight() == -1) {
                return img.getScaledCopy(scale);
            }
            return img.getScaledCopy(info.getWidth() * scale, info.getHeight() * scale);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }
}
