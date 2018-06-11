package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.Weapon;

public final class Monster extends AbstractPlayer {
    private Monster(Builder builder) {
        super(builder);
    }

    public static Monster create(Asset asset, Weapon weapon, Item shield, boolean cpu) {
        Builder builder = new Builder(asset, weapon, shield)
                .setMaxHealth(50)
                .setMaxSpeed(5)
                .setSpeed(5)
                .setCPU(cpu);

        return new Monster(builder);
    }
}
