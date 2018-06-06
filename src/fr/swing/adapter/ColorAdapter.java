package fr.swing.adapter;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.Color;

import java.awt.*;
import java.util.Objects;

public final class ColorAdapter implements Color {
    private final java.awt.Color color;

    public ColorAdapter(java.awt.Color color) {
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Graphics g = canvas.get();
        g.setColor(color);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
