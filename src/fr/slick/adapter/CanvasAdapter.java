package fr.slick.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.utils.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;

public final class CanvasAdapter implements Canvas<Graphics> {
    private final int width;
    private final int height;

    private Graphics g;

    public CanvasAdapter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public CanvasAdapter wrap(Graphics g) {
        this.g = g;
        return this;
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
    public Point getSize() {
        return new Point(getWidth(), getHeight());
    }

    @Override
    public Graphics get() {
        return g;
    }

    @Override
    public void drawRect(float x, float y, float width, float height, AssetColor color) {
        Rectangle rect = new Rectangle(x, y, width, height);

        if (color != null) {
            g.setColor((Color) color.get());
            g.fill(rect);
        }
    }

    @Override
    public void drawCircle(float x, float y, float radius, AssetColor color) {
        Circle circle = new Circle(x, y, radius);

        if (color != null) {
            g.setColor((Color) color.get());
            g.fill(circle);
        }
    }

    @Override
    public void drawColor(AssetColor color) {
        g.setBackground((Color) color.get());
    }

    @Override
    public void drawAsset(Asset asset, float x, float y) {
        Image img = (Image) asset.get();
        img.draw(x, y);
    }

    @Override
    public void drawAsset(Asset asset, AssetColor color, float x, float y) {
        Image img = (Image) asset.get();
        img.draw(x, y, (Color) color.get());
    }

    @Override
    public void drawText(String text, float x, float y, AssetColor color) {
        if (color != null) {
            g.setColor((Color) color.get());
        }
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        TrueTypeFont font = new TrueTypeFont(awtFont, false);
        g.setFont(font);
        g.drawString(text, x, y);
    }
}
