package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.Player;

public class HealthBonus extends AbstractArtifact {
    public HealthBonus(Asset asset) {
        super(asset);
    }

    @Override
    public void onPick(Player player) {
        player.addHealth(10);
    }
}
