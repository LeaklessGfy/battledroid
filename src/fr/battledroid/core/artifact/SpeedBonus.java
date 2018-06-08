package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.Player;

public final class SpeedBonus extends AbstractArtifact {
    public SpeedBonus(Asset asset) {
        super(asset);
    }

    @Override
    public void onCollide(Player player) {
        player.addSpeed(5);
    }
}
