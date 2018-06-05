package fr.battledroid.core.player.item;

import fr.battledroid.core.player.Player;

public interface Item {
    void use(Player self, Player target, boolean isCritic);
}
