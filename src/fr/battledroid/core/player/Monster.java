package fr.battledroid.core.player;

import fr.battledroid.core.player.item.Item;

public final class Monster extends AbstractPlayer {
    private Monster(Builder builder, boolean cpu) {
        super(builder);
    }

    public static Monster create(Item weapon, Item shield, boolean cpu) {
        Builder builder = new Builder(null, weapon, shield)
                .setMaxHealth(50)
                .setMaxSpeed(5)
                .setSpeed(5);

        return new Monster(builder, cpu);
    }
}
