package fr.slick.adapter;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.utils.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

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
        g.drawRect(x, y, width, height);
    }

    @Override
    public void drawCircle(float x, float y, float radius, AssetColor color) {
        Circle circle = new Circle(x, y, radius);
        g.setColor(org.newdawn.slick.Color.red);
        g.fill(circle);
    }

    @Override
    public void drawColor(AssetColor color) {
        g.setBackground((Color) color.get());
    }
}
