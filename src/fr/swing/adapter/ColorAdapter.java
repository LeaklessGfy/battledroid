package fr.swing.adapter;

import fr.battledroid.core.adaptee.AssetColor;

import java.awt.*;
import java.util.Objects;

public final class ColorAdapter implements AssetColor {
    private final Color color;

    public ColorAdapter(Color color) {
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public Color get() {
        return color;
    }
}
