package fr.swing.adapter;

import fr.battledroid.core.asset.Color;

import java.util.Objects;

public final class ColorAdapter implements Color {
    private final java.awt.Color color;

    public ColorAdapter(java.awt.Color color) {
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public int getRGB() {
        return color.getRGB();
    }

    @Override
    public Object get() {
        return color;
    }
}
