package fr.battledroid.core.player.item;

import fr.battledroid.core.player.Player;

public final class WeaponItem implements Item {
    private int damage = 10;

    @Override
    public void use(Player self, Player target, boolean isCritic) {
        target.takeDamage(isCritic ? damage * 2 : damage);
    }

    @Override
    public String toString() {
        return "Weapon";
    }
}
