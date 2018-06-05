package fr.battledroid.core.artifact;

import fr.battledroid.core.Utils;
import fr.battledroid.core.player.Player;

public class HealthBonus extends AbstractArtifact {
    public HealthBonus() {
        super(DEFAULT_FIELD);
    }

    @Override
    public void onPick(Player player) {
        Utils.requireNonNull(player).addHealth(10);
    }
}
