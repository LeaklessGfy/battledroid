package fr.battledroid.core.player.item;

import fr.battledroid.core.player.Player;

public class ShieldItem implements Item {
    private int defense = 10;

    @Override
    public void use(Player self, Player target, boolean isCritic) {
        self.addDefense(isCritic ? defense * 5 : defense);
    }

    @Override
    public String toString() {
        return "Shield";
    }
}
