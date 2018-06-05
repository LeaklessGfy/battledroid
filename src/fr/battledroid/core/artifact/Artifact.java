package fr.battledroid.core.artifact;

import fr.battledroid.core.asset.Collider;
import fr.battledroid.core.asset.Asset;
import fr.battledroid.core.player.Player;

public interface Artifact extends Asset, Collider {
    void onPick(Player player);
}
