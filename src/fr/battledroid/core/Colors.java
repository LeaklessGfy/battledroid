package fr.battledroid.core;

import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.utils.Utils;

public final class Colors {
    private static Colors instance;

    private final AssetColor background;
    private final AssetColor health;
    private final AssetColor defense;

    private Colors(AssetColor b, AssetColor h, AssetColor d) {
        this.background = Utils.requireNonNull(b);
        this.health = Utils.requireNonNull(h);
        this.defense = Utils.requireNonNull(d);
    }

    public static Colors instance() {
        if (instance == null) {
            throw new IllegalStateException("Colors has not been built");
        }
        return instance;
    }

    public static Colors init(AssetColor background, AssetColor health, AssetColor defense) {
        if (instance != null) {
            throw new IllegalStateException("Colors already built");
        }
        instance = new Colors(background, health, defense);
        return instance;
    }

    public AssetColor getBackground() {
        return background;
    }

    public AssetColor getHealth() {
        return health;
    }

    public AssetColor getDefense() {
        return defense;
    }
}
