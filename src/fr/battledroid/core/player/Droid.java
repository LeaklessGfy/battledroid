package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.Weapon;

public final class Droid extends AbstractPlayer {
    private Droid(Builder builder) {
        super(builder);
    }

    public static Droid create(Asset image, Weapon weapon, Item shield) {
        Builder builder = new Builder(image, weapon, shield);
        return new Droid(builder);
    }
}
