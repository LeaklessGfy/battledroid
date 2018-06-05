package fr.battledroid.core.artifact;

import fr.battledroid.core.Utils;
import fr.battledroid.core.player.Player;

public class SpeedBonus extends AbstractArtifact {
    public SpeedBonus() {
        super(DEFAULT_FIELD);
    }

    @Override
    public void onPick(Player player) {
        Utils.requireNonNull(player).addSpeed(5);
    }
}
