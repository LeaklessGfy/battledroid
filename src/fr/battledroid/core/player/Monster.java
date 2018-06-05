package fr.battledroid.core.player;

import fr.battledroid.core.player.item.Item;

public class Monster extends AbstractPlayer {
    private final boolean cpu;

    private Monster(Builder builder, boolean cpu) {
        super(builder);
        this.cpu = cpu;
    }

    public static Monster create(Item weapon, Item shield, boolean cpu) {
        Builder builder = new Builder(null, weapon, shield)
                .setMaxHealth(50)
                .setMaxSpeed(5)
                .setSpeed(5);

        return new Monster(builder, cpu);
    }

    @Override
    public boolean isCPU() {
        return cpu;
    }
}
