package fr.swing.adapter;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.utils.Point;

import java.awt.*;
import java.util.Objects;

public final class CanvasAdapter implements Canvas<Graphics2D> {
    private Graphics2D g;

    public CanvasAdapter(Graphics2D g) {
        this.g = Objects.requireNonNull(g);
    }

    @Override
    public Canvas wrap(Graphics2D o) {
        return null;
    }

    @Override
    public int getWidth() {
        return g.getDeviceConfiguration().getBounds().width;
    }

    @Override
    public int getHeight() {
        return g.getDeviceConfiguration().getBounds().height;
    }

    @Override
    public Point getSize() {
        return new Point(getWidth(), getHeight());
    }

    @Override
    public Graphics2D get() {
        return null;
    }

    @Override
    public void drawRect(float x, float y, float width, float height, AssetColor color) {
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawCircle(float x, float y, float radius, AssetColor color) {
        g.drawOval((int) x, (int) y, (int) radius, (int) radius);
        g.setColor(java.awt.Color.RED);
        g.fillOval((int) x, (int) y, (int) radius, (int) radius);
    }

    @Override
    public void drawColor(AssetColor color) {

    }
}
