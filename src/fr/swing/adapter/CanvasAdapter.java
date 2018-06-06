package fr.swing.adapter;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.Color;
import fr.battledroid.core.utils.Point;

import java.awt.*;
import java.util.Objects;

public final class CanvasAdapter implements Canvas {
    private Graphics2D g;

    public CanvasAdapter(Graphics2D g) {
        this.g = Objects.requireNonNull(g);
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
    public <T> T get() {
        return (T) g;
    }

    @Override
    public void drawRect(float x, float y, float width, float height, Color color) {
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawCircle(float x, float y, float radius, Color color) {
        g.drawOval((int) x, (int) y, (int) radius, (int) radius);
        g.setColor(java.awt.Color.RED);
        g.fillOval((int) x, (int) y, (int) radius, (int) radius);
    }
}
