package fr.swing.adapter;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.Color;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class CanvasAdapter implements Canvas {
    private final JComponent component;
    private Graphics g;

    public CanvasAdapter(JComponent component) {
        this.component = Objects.requireNonNull(component);
    }

    public CanvasAdapter init(Graphics g) {
        this.g = g;
        return this;
    }

    @Override
    public int getWidth() {
        return component.getWidth();
    }

    @Override
    public int getHeight() {
        return component.getHeight();
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
