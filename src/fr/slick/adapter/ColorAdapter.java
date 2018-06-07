package fr.slick.adapter;

import fr.battledroid.core.adaptee.AssetColor;
import org.newdawn.slick.Color;

import java.util.Objects;

public final class ColorAdapter implements AssetColor<Color> {
    private final Color color;

    public ColorAdapter(org.newdawn.slick.Color color) {
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public Color get() {
        return color;
    }
}
