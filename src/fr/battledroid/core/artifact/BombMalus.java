package fr.battledroid.core.artifact;

import fr.battledroid.core.player.Player;

/**
 * Bomb malus
 */
public class BombMalus extends AbstractArtifact {
    public BombMalus() {
        super(DEFAULT_FIELD);
    }

    @Override
    public void onPick(Player player) {
        player.takeDamage(20);
    }
}
