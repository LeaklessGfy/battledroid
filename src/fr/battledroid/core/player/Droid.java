package fr.battledroid.core.player;

import fr.battledroid.core.asset.Image;
import fr.battledroid.core.player.item.Item;

public class Droid extends AbstractPlayer {
    private Droid(Builder builder) {
        super(builder);
    }

    public static Droid create(Image image, Item weapon, Item shield) {
        Builder builder = new Builder(image, weapon, shield);
        return new Droid(builder);
    }

    @Override
    public boolean isCPU() {
        return false;
    }
}
