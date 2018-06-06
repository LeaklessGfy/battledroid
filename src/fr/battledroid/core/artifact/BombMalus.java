package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.Player;

public final class BombMalus extends AbstractArtifact {
    public BombMalus(Asset asset) {
        super(asset);
    }

    @Override
    public void onPick(Player player) {
        player.takeDamage(20);
    }
}
