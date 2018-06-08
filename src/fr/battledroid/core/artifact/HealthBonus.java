package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.Player;

public final class HealthBonus extends AbstractArtifact {
    public HealthBonus(Asset asset) {
        super(asset);
    }

    @Override
    public void onCollide(Player player) {
        player.addHealth(10);
    }
}
